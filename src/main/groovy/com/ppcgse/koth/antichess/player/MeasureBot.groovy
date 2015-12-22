package com.ppcgse.koth.antichess.player

import com.ppcgse.koth.antichess.controller.*

import java.util.concurrent.ThreadLocalRandom

/**
 * Created by ProgramFOX on 12/21/15.
 */

class MeasureBot extends Player {
    {
        pieceUpgradeType = PieceUpgradeType.KING
    }

    @Override
    Move getMove(Board board, Player opponent, Set<Move> validMoves) {
        def opponentPieces = opponent.getPieces(board)
        def mustCapture = opponentPieces.find { it.loc == validMoves[0].destination } != null
        def chosen = null
        if (mustCapture) {
            def piecesThatCanBeTaken = opponentPieces.findAll {
                validMoves.collect { it.getDestination() }.contains(it.loc)
            }
            def lowestAmount = getPieceValue(piecesThatCanBeTaken.sort { getPieceValue(it.getType()) }[0].getType())
            def piecesWithLowestValue = piecesThatCanBeTaken.findAll { getPieceValue(it.getType()) == lowestAmount }
            def chosenOnes = validMoves.findAll { m -> piecesWithLowestValue.find { it.loc == m.destination } != null }
            chosen = chosenOnes.sort { getPieceValue(it.piece.getType()) }.reverse()[0]
        } else {
            chosen = randomMove(validMoves);
        }
        return chosen
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

    def randomMove(Set<Move> validMoves) {
        return validMoves[ThreadLocalRandom.current().nextInt(validMoves.size())];
    }
}