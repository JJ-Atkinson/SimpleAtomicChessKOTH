package com.ppcgse.koth.antichess.player

import com.ppcgse.koth.antichess.controller.Location
import com.ppcgse.koth.antichess.controller.PieceUpgradeType
import com.ppcgse.koth.antichess.controller.Player
import com.ppcgse.koth.antichess.controller.Board
import com.ppcgse.koth.antichess.controller.Move
import com.ppcgse.koth.antichess.controller.ReadOnlyBoard
import groovy.transform.ToString

import java.util.concurrent.ThreadLocalRandom;

public class TestPlayer extends Player {

    {pieceUpgradeType = PieceUpgradeType.ROOK}

    @Override
    public Move getMove(ReadOnlyBoard board, Player enemy, Set<Move> moves) {
//        def target = new Location(1, 4)
//        return moves.find {it.destination == target}
//
        return moves[ThreadLocalRandom.current().nextInt(moves.size())];
    }

}
