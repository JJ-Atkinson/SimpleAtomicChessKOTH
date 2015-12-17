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
        players[0] = player1;
        players[0].setTeam(Color.WHITE);
        players[1] = player2;
        players[1].setTeam(Color.BLACK);
    }

    public void run() {
        if (finised) throw new IllegalStateException("You can't rerun a game");

        int i = 0;
        while (!gameOver()) {
            makeTurn(players[i], players[(i + 1) % 2]);
            i = (i + 1) % 2;
        }
        def white = players[0].getPieces(board).isEmpty() ? 1 : 0
        def black = players[1].getPieces(board).isEmpty() ? 2 : 0
        switch (white + black) {
            case 0:
                whiteGameRes = DRAW
                blackGameRes = DRAW
                break
            case 1:
                whiteGameRes = WIN
                blackGameRes = LOSE
                break
            case 2:
                whiteGameRes = LOSE
                blackGameRes = WIN
                break
            case 3:
                throw new IllegalStateException("Wha?? both players have no pieces!!")
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
            def validMoves = genValidMoves(player, enemy)

            Move move = player.getMove((Board) board.clone(), enemy, validMoves);

            if ((System.currentTimeMillis() - start) > MAX_MILLISECONDS)
                player.disqualify();

            if (validMoves.contains(move)) {
                if (board.movePiece(move))
                    turnsWithoutCaptures = -1;
                turnsWithoutCaptures++;
            } else {
                player.disqualify();
            }
        } catch (IllegalArgumentException e) {
            player.disqualify()
            System.err.println(player.getClass().getSimpleName() + " made and invalid move.")
        } catch (Exception e) {
            player.disqualify();
            System.err.println("Exception while moving " + player);
        }
    }

    private Set<Move> genValidMoves(Player player, Player enemy) {
        def allMoves = player.getPieces(board).collect {[it, it.getValidDestinationSet(board)]}
        def attackMoves = allMoves
                .collect {
                    [it[0], it[1].findAll {board.getFieldAtLoc(it[0])?.piece?.team == enemy.team}]
                }.findAll {it[1]}

        if (attackMoves.isEmpty())
            return allMoves.collect {
                Piece piece = it[0]
                return it[1].collect {loc -> new Move(piece, loc)}}.flatten() as Set<Move>
        else
            return attackMoves.collect {
                Piece piece = it[0]
                return it[1].collect {loc -> new Move(piece, loc)}}.flatten() as Set<Move>
    }

    public boolean gameOver() {
        for (Player player : players) {
            if (player.isDisqualified() ||
                player.getPieces(board).isEmpty() ||
                turnsWithoutCaptures >= MAX_TURNS_WITHOUT_CAPTURES) {
                return true;
            }
        }
        return false;
    }
}
