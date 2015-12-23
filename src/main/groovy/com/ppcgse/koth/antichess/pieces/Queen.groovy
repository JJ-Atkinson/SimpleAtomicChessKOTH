package com.ppcgse.koth.antichess.pieces

import com.ppcgse.koth.antichess.controller.*
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

@EqualsAndHashCode
@TupleConstructor
public class Queen extends Piece {

    public Queen(Color team, Location loc) {
        super(team, PieceType.QUEEN, loc, true);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        genValidDests(board, [-1..1, -1..1]
                                .combinations()
                                .findAll { it != [0, 0] }
                     )
    }
}
