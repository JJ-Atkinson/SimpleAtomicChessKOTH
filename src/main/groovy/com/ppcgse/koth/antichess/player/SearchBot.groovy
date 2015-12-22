package com.ppcgse.koth.antichess.player

import com.ppcgse.koth.antichess.controller.*

/**
 * Created by ProgramFOX on 12/22/15.
 */

public class SearchBot extends Player {
    {
        pieceUpgradeType = PieceUpgradeType.KING
    }

    @Override
    Move getMove(Board board, Player opponent, Set<Move> validMoves) {
        return getMoveInternal(board, this, opponent, validMoves, 2)[0]
    }

    Tuple getMoveInternal(Board board, Player whoseTurn, Player opponent, Set<Move> validMoves, Integer depth) {
        def bestScore = null
        def currentlyChosenMove = null
        validMoves.each { m ->
            def opponentPiecesValueBefore = opponent.getPieces(board).sum { getPieceValue(it.getType()) }
            def newBoard = board.movePiece(whoseTurn, m)
            def opponentPiecesValueAfter = opponent.getPieces(newBoard).sum { getPieceValue(it.getType()) }
            if (opponentPiecesValueAfter == null) {
                opponentPiecesValueAfter = 0
            }
            def score = opponentPiecesValueAfter - opponentPiecesValueBefore
            if (whoseTurn.getTeam() == Color.BLACK) {
                score = -score
            }
            if (depth > 1) {
                def validMovesNow = genValidMoves(opponent, whoseTurn, newBoard)
                def goDeeper = true
                if (validMovesNow == null || validMovesNow.size() == 0) {
                    def toAdd = -999
                    if (whoseTurn.getTeam() == Color.BLACK) {
                        toAdd = -toAdd
                    }
                    score += toAdd
                    goDeeper = false
                }
                if (goDeeper) {
                    score += getMoveInternal(newBoard, opponent, whoseTurn, validMovesNow, depth - 1)[1]
                }
            }
            if (bestScore == null) {
                bestScore = score
                currentlyChosenMove = m
            }
            if ((whoseTurn.getTeam() == Color.WHITE && score > bestScore) || (whoseTurn.getTeam() == Color.BLACK && score < bestScore)) {
                bestScore = score
                currentlyChosenMove = m
            }
        }
        return new Tuple(currentlyChosenMove, bestScore)
    }

    Double getPieceValue(PieceType pieceType) {
        switch (pieceType) {
            case PieceType.KING:
                return 1
            case PieceType.PAWN:
                return 1.5
            case PieceType.KNIGHT:
                return 2.5
            case PieceType.BISHOP:
                return 3
            case PieceType.ROOK:
                return 5
            case PieceType.QUEEN:
                return 9
            default:
                return 0
        }
    }

    // Copied from Game.groovy and a bit modified.
    // I actually need this.
    Set<Move> genValidMoves(Player player, Player enemy, Board board) {
        def allMoves = player.getPieces(board).collect { [it, it.getValidDestinationSet(board)] }
        def attackMoves = allMoves
                .collect { pair ->
            def piece = pair[0]
            def dests = pair[1]
            [piece, dests.findAll { board.getFieldAtLoc(it as Location)?.piece?.team == enemy.team }]
        }.findAll { it[1] }

        if (attackMoves.isEmpty())
            return allMoves.collect {
                Piece piece = it[0] as Piece
                return it[1].collect { loc -> new Move(piece, loc as Location) }
            }.flatten() as Set<Move>
        else
            return attackMoves.collect {
                Piece piece = it[0] as Piece
                return it[1].collect { loc -> new Move(piece, loc as Location) }
            }.flatten() as Set<Move>
    }
}