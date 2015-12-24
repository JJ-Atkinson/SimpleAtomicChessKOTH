package com.ppcgse.koth.antichess.controller

import static com.ppcgse.koth.antichess.controller.GameResult.*

public class Game {
    public static boolean DEBUG = false
    public static boolean SHOW_GAMES = true
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
        debugPrint "Game started. Players: $players"

        int i = 0;
        while (!gameOver()) {
            def hadMove = makeTurn(players[i], players[(i + 1) % 2]);
            i = (i + 1) % 2;

            debugPrint "Game over? ${gameOver() || !hadMove}\n\n${'=' * 30}\n"

            if (!hadMove)
                break
        }
        def whiteHasPieces = players.find { it.team == Color.WHITE }.getPieces(board).size() > 0
        def blackHasPieces = players.find { it.team == Color.BLACK }.getPieces(board).size() > 0

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
        if (DEBUG || SHOW_GAMES) {
            println "Game between $players is done"
            println "Result: White -> $whiteGameRes; Black -> $blackGameRes;"
        }

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

    /**
     * Return false if player has no moves and he wins
     *
     * (note to self: this is needed in case a pawn is stuck and he is the last piece)
     * @param player
     * @param enemy
     * @return
     */
    private boolean makeTurn(Player player, Player enemy) {
        try {
            long start = System.currentTimeMillis();
            def validMoves = genValidMoves(player, enemy)
            if (!validMoves) return false
            debugPrint "${player.team}s turn."
            debugPrint "validMoves: $validMoves"
            debugPrint "board:\n$board"
            debugPrint "captureless turns: $turnsWithoutCaptures"
            Move move = player.getMove(board, enemy, validMoves);
            debugPrint "chosen move: $move"

            if ((System.currentTimeMillis() - start) > MAX_MILLISECONDS && !DEBUG)
                player.disqualify();

            def newBoard = board.movePiece(player, move)

            if (validMoves.contains(move)) {
                if (wasCapture(board, newBoard))
                    turnsWithoutCaptures = -1;
                turnsWithoutCaptures++;
            } else {
                player.disqualify();
                System.err.println("Player $player made an invalid move.")
                System.err.println("Valid moves $validMoves")
                System.err.println("Chosen move $move")
            }

            board = newBoard
        } catch (IllegalArgumentException ignored) {
            player.disqualify()
            System.err.println(player.getClass().getSimpleName() + " made and invalid move.")
        } catch (Exception e) {
            player.disqualify();
            System.err.println("Exception while moving " + player);
            e.printStackTrace()
        }
        return true
    }


    private boolean wasCapture(Board oldBoard, Board newBoard) {
        def otherPieceCount = newBoard.fields.inject(0) {
            int acc, Location k, Field v -> v.hasPiece() ? acc + 1 : acc
        }
        def myPieceCount = oldBoard.fields.inject(0) {
            int acc, Location k, Field v -> v.hasPiece() ? acc + 1 : acc
        }

        return otherPieceCount != myPieceCount
    }


    private Set<Move> genValidMoves(Player player, Player enemy) {
        def allMoves = player.getPieces(board).collect { [it, it.getValidDestinationSet(board)] }
        def otherPlayerMoves = enemy.getPieces(board)
                                    .collect { [it, it.getValidDestinationSet(board)] }
        def otherPlayerDestSet = otherPlayerMoves
                                    .collect {it[1]}
                                    .flatten() as Set<Location>
        def king = player.getPieces(board).find {it.type == PieceType.KING}

        if (board.isInCheck(player, enemy))
            return king.getValidDestinationSet(board)
                            .findAll {!otherPlayerDestSet.contains(it)}
                            .collect {new Move(king, it)}
        else
            return allMoves.findAll {board.isValidMove(it)}
                            .collect {new Move(it[0], it[1])}
    }


    public boolean gameOver() {
        if (turnsWithoutCaptures >= MAX_TURNS_WITHOUT_CAPTURES) return true

        return (players.collect {
                                it.getPieces(board)
                                        .find {it.type == PieceType.KING}
                               }.findAll()
                               .size() == 2
               || board.isCheckmate(players[0], players[1])
               || board.isCheckmate(players[1], players[0]))
    }

    def debugPrint(String out) {
        if (DEBUG)
            println out
    }
}