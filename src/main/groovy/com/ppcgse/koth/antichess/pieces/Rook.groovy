package com.ppcgse.koth.antichess.pieces

import com.ppcgse.koth.antichess.controller.*
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

@EqualsAndHashCode
@TupleConstructor
public class Rook extends Piece {

    Rook(Color team, Location loc) {
        super(team, PieceType.ROOK, loc)
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        genValidDests(board, [[1, 0], [0, 1], [-1, 0], [0, -1]])
    }
}
