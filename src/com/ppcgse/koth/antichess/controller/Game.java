package com.ppcgse.koth.antichess.controller;

import static com.ppcgse.koth.antichess.controller.GameResult.*;

public class Game {
    private static final int MAX_TURNS_WITHOUT_CAPTURES = 100; //=50, counts for both teams
    private static final int MAX_MILLISECONDS = 2000;
    private Board board;
    private Player[] players = new Player[2];
    private int turnsWithoutCaptures = 0;
    private boolean finised = false;
    private GameResult whiteGameRes = DRAW;
    private GameResult blackGameRes = DRAW;


    public Game(Player player1, Player player2) {
        board = new Board();
        board.initialize();
        players[0] = player1;
        players[0].setTeam(Color.WHITE);
        players[1] = player2;
        players[1].setTeam(Color.BLACK);
    }

    int run() {
        int i = 0;
        while (!gameOver()) {
            makeTurn(players[i], players[(i + 1) % 2]);
            i = (i + 1) % 2;
        }
        if (loses(players[0]) && !loses(players[1])) {
            return Runner.LOSE_POINTS;
        } else if (loses(players[1]) && !loses(players[0])) {
            return Runner.WIN_POINTS;
        } else {
            return Runner.DRAW_POINTS;
        }

        finised = true;
    }

    public GameResult getWhiteGameRes() {
        if (!finised) throw new IllegalStateException("You can't get the result of and unfinished game silly!");
        return whiteGameRes;
    }

    public GameResult getBlackGameRes() {
        if (!finised) throw new IllegalStateException("You can't get the result of and unfinished game silly!");
        return blackGameRes;
    }

    private void makeTurn(Player player, Player enemy) {
        try {
            long start = System.currentTimeMillis();

            Move move = player.getMove(board.clone(), enemy);
            if ((System.currentTimeMillis() - start) > MAX_MILLISECONDS) {
                player.setDisqualified();
            }
            if (move.isValid(board, player)) {
                if (board.movePiece(move) || move.getPiece().getType() == PieceType.PAWN) {
                    turnsWithoutCaptures = 0;
                } else {
                    turnsWithoutCaptures++;
                }
            } else {
                player.setDisqualified(); //invalid move
            }
        } catch (Exception e) {
            player.setDisqualified();
            System.out.println("Exception while moving " + player);
        }

    }

    public boolean gameOver() {
        for (Player player : players) {
            if (player.isDisqualified() || board.getKing(player) == null
                    || turnsWithoutCaptures >= MAX_TURNS_WITHOUT_CAPTURES || draw) {
                return true;
            }
        }
        return false;
    }
}
