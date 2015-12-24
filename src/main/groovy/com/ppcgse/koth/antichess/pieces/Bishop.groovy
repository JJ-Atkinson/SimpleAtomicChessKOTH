package com.ppcgse.koth.antichess.pieces

import com.ppcgse.koth.antichess.controller.*
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

@TupleConstructor
@EqualsAndHashCode
public class Bishop extends Piece {

    Bishop(Color team, Location loc) {
        super(team, PieceType.BISHOP, loc, true)
    }

    @Override
    public Set<Location> getValidLocations(Board board) {
        return genValidDests(board, [[1, 1], [1, -1], [-1, -1], [-1, 1]]);
    }
}
