package com.ppcgse.koth.antichess.player

import com.ppcgse.koth.antichess.controller.PieceUpgradeType;

import com.ppcgse.koth.antichess.controller.Board;
import com.ppcgse.koth.antichess.controller.Player;
import com.ppcgse.koth.antichess.controller.Move;

public class SimplePlayer extends Player {

    {pieceUpgradeType = PieceUpgradeType.ROOK}

    @Override
    public Move getMove(Board board, Player enemy, Set<Move> moves) {
        return new ArrayList<Move>(moves).get(0);
    }

}
