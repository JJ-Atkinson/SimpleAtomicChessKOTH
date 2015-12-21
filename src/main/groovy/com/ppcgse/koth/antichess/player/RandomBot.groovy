package com.ppcgse.koth.antichess.player

import com.ppcgse.koth.antichess.controller.Move
import com.ppcgse.koth.antichess.controller.PieceUpgradeType
import com.ppcgse.koth.antichess.controller.Player
import com.ppcgse.koth.antichess.controller.ReadOnlyBoard

import java.util.concurrent.ThreadLocalRandom

public class RandomBot extends Player {

    {
        pieceUpgradeType = PieceUpgradeType.ROOK
    }

    @Override
    public Move getMove(ReadOnlyBoard board, Player enemy, Set<Move> moves) {
        return moves[ThreadLocalRandom.current().nextInt(moves.size())];
    }

}
