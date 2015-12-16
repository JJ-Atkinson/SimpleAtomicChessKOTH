package com.ppcgse.koth.antichess.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ppcgse.koth.antichess.controller.*;

public class TestPlayer extends Player {

    @Override
    public Move getMove(Board board, Player enemy) {
        List<Move> possibleMoves = new ArrayList<>();
        List<Piece> pieces = this.getPieces(board);

        for (Piece piece : pieces) {
            for (Location p : piece.getValidDestinationSet(board)) {
                possibleMoves.add(new Move(piece, p));
            }
        }

        int rnd = new Random().nextInt(possibleMoves.size());
        return possibleMoves.get(rnd);
    }

}
