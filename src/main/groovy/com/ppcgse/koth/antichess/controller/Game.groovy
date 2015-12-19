package com.ppcgse.koth.antichess.controller;

import static com.ppcgse.koth.antichess.controller.GameResult.*;

public class Game {
    private static final int MAX_TURNS_WITHOUT_CAPTURES = 100; //=50, counts for both teams
    private static final int MAX_MILLISECONDS = 2000;
    private Board board;
    private Player[] players = new Player[2];
    private int turnsWithoutCaptures = 0;
    private boolean finished = false;
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
        if (finished) throw new IllegalStateException("You can't rerun a game");

        int i = 0;
        while (!gameOver()) {
            makeTurn(players[i], players[(i + 1) % 2]);
            i = (i + 1) % 2;

            println "game over? ${gameOver()}\n\n${'='*30}\n"
        }
        def whiteHasPieces = players[0].getPieces(board).isEmpty()
        def blackHasPieces = players[1].getPieces(board).isEmpty()

        if (whiteHasPieces == false) {
                whiteGameRes = WIN
                blackGameRes = LOSE
        } else if (blackHasPieces == false) {
                whiteGameRes = LOSE
                blackGameRes = WIN
        } else {
                whiteGameRes = DRAW
                blackGameRes = DRAW
        }

        println "Game between $players is done"
        println "Result: White -> $whiteGameRes; Black -> $blackGameRes;"

        finished = true;
    }

    public GameResult getWhiteGameRes() {
        if (!finished) throw new IllegalStateException("You can't get the result of and unfinished game silly!");
        return whiteGameRes;
    }

    public GameResult getBlackGameRes() {
        if (!finished) throw new IllegalStateException("You can't get the result of and unfinished game silly!");
        return blackGameRes;
    }

    private void makeTurn(Player player, Player enemy) {
        try {
            long start = System.currentTimeMillis();
            def validMoves = genValidMoves(player, enemy)
            println "${player.team}s turn."
            println "validMoves: $validMoves"
            println "board:\n$board"
            println "captureless turns: $turnsWithoutCaptures"
            Move move = player.getMove((Board) board.clone(), enemy, validMoves);
            println "chosen move: $move"

//            if ((System.currentTimeMillis() - start) > MAX_MILLISECONDS)
//                player.disqualify();

            if (validMoves.contains(move)) {
                if (board.movePiece(player, move))
                    turnsWithoutCaptures = -1;
                turnsWithoutCaptures++;
            } else {
                player.disqualify();
                System.err.println("Player $player made an invalid move.")
                System.err.println("Valid moves $validMoves")
                System.err.println("Chosen move $move")
            }
        } catch (IllegalArgumentException e) {
            player.disqualify()
            System.err.println(player.getClass().getSimpleName() + " made and invalid move.")
        } catch (Exception e) {
            player.disqualify();
            System.err.println("Exception while moving " + player);
            e.printStackTrace()
        }
    }

    private Set<Move> genValidMoves(Player player, Player enemy) {
        def allMoves = player.getPieces(board).collect {[it, it.getValidDestinationSet(board)]}
        def attackMoves = allMoves
                .collect {pair ->
                    def piece = pair[0]
                    def dests = pair[1]
                    [piece, dests.findAll {board.getFieldAtLoc(it)?.piece?.team == enemy.team}]
                }.findAll {it[1]}

        println "Allmoves: $allMoves"
        println "Attackmoves: $attackMoves"

        if (attackMoves.isEmpty())
            return allMoves.collect {
                Piece piece = it[0]
                return it[1].collect {loc -> new Move(piece, loc as Location)}}.flatten() as Set<Move>
        else
            return attackMoves.collect {
                Piece piece = it[0]
                return it[1].collect {loc -> new Move(piece, loc as Location)}}.flatten() as Set<Move>
    }

    public boolean gameOver() {
        for (Player player : players) {
            if (player.isDisqualified() || player.getPieces(board).isEmpty() ||
                    turnsWithoutCaptures >= MAX_TURNS_WITHOUT_CAPTURES) {
                return true;
            }
        }
        return false;
    }
}