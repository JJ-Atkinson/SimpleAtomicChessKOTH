package com.ppcgse.koth.antichess.player

import com.ppcgse.koth.antichess.controller.*

import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Jarrett on 12/19/15.
 */
class SacrificeBot extends Player {

    {pieceUpgradeType = PieceUpgradeType.ROOK}

    @Override
    Move getMove(ReadOnlyBoard board, Player enemy, Set<Move> validMoves) {
        def realBoard = board.cloneBoard()
        def enemyPieces = enemy.getPieces(realBoard)
        def pawnMoves = getPawnsMoves(enemyPieces)
        def enemyPlayerValidMoves = (enemyPieces
                                        .collect { it.getValidDestinationSet(realBoard) }
                                        .flatten() as List<Location>)
        enemyPlayerValidMoves += pawnMoves

        def sacrificeMove = validMoves
                                .find {enemyPlayerValidMoves.contains(it.destination)}

        if (sacrificeMove)
            return sacrificeMove
        else
            return randomMove(validMoves)
    }

    def randomMove(Set<Move> validMoves) {
        return validMoves[ThreadLocalRandom.current().nextInt(validMoves.size())];
    }

    def getPawnsMoves(List<Piece> allPieces) {
        def direction = getTeam() == Color.BLACK ? 1 : -1;
        def pawns = allPieces.findAll {it.type == PieceType.PAWN}
        def pawnAttacks = (pawns.collect {
                                    [it.loc.plus(-1, direction), it.loc.plus(1, direction)]
                                }.flatten()
                                ).findAll {
                                    ((Location) it).isValid()
                                }
        return pawnAttacks as List<Location>
    }
}
