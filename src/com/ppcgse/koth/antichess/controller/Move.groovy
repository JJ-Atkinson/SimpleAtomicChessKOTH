package com.ppcgse.koth.antichess.controller

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * Created by Jarrett on 12/16/15.
 */
@ToString
@EqualsAndHashCode
@AutoClone(style = AutoCloneStyle.SIMPLE)
class Move {
    Piece piece
    Location destination

    public boolean isValid(Board board, Player player) {
        if (piece == null || destination == null || player.getTeam() != piece.getTeam()) {
            return false;
        }

        if (!pieceExists(board, piece)) {
            return false;
        }

        if (piece.getValidDestinationSet(board).contains(destination)) {
            return true;
        }
        return false;
    }

    private boolean pieceExists(Board board, Piece piece) {
        Location pos = piece.getPos();
        if (pos != null && !piece.getPos().isOutside()) {
            Field field = board.getFields()[pos.getX()][pos.getY()];
            if (field.hasPiece() && field.getPiece().equals(piece)) {
                return true;
            }
        }
        return false;
    }
}
