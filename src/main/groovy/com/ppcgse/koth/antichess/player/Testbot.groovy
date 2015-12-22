package com.ppcgse.koth.antichess.player

import com.ppcgse.koth.antichess.controller.*

public class Testbot extends Player {

    {
        pieceUpgradeType = PieceUpgradeType.ROOK
    }

    @Override
    public Move getMove(Board board, Player enemy, Set<Move> moves) {
        def pawn = getPieces(board.cloneBoard()).find { it.type == PieceType.PAWN }
        return new Move(pawn, pawn.loc);
    }

}
