package com.ppcgse.koth.antichess.player;

import com.ppcgse.koth.antichess.controller.*

import java.util.concurrent.ThreadLocalRandom;

public class TestPlayer extends Player {

    @Override
    public Move getMove(Board board, Player enemy, Set<Move> moves) {
        return moves[ThreadLocalRandom.current().nextInt(moves.size())];
    }

}
