package com.ppcgse.koth.antichess.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ppcgse.koth.antichess.controller.Board;
import com.ppcgse.koth.antichess.controller.Move;
import com.ppcgse.koth.antichess.controller.Piece;
import com.ppcgse.koth.antichess.controller.Player;
import com.ppcgse.koth.antichess.controller.Location;

public class SimplePlayer extends Player {

    @Override
    public Move getMove(Board board, Player enemy, Set<Move> moves) {
        return new ArrayList<Move>(moves).get(0);
    }

}
