package com.ppcgse.koth.antichess.player;

import java.util.List;

import com.ppcgse.koth.antichess.controller.Board;
import com.ppcgse.koth.antichess.controller.Move;
import com.ppcgse.koth.antichess.controller.Piece;
import com.ppcgse.koth.antichess.controller.Player;
import com.ppcgse.koth.antichess.controller.Location;

public class SimplePlayer extends Player {

    @Override
    public Move getMove(Board board, Player enemy) {
        //get all com.ppcgse.koth.antichess.pieces of this com.ppcgse.koth.antichess.player
        List<Piece> pieces = this.getPieces(board);
        for (Piece piece : pieces) {
            Location[] destinations = piece.getValidDestinations(board);
            if (destinations.length > 0) {
                return new Move(piece, destinations[0]);
            }
        }

        //should never happen, because the game is over then
        return null;
    }

}
