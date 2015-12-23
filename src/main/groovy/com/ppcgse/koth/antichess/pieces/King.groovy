package com.ppcgse.koth.antichess.pieces

import com.ppcgse.koth.antichess.controller.*
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

@EqualsAndHashCode
@TupleConstructor
public class King extends Piece {

    public King(Color team, Location loc) {
        super(team, PieceType.KING, loc, true);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        ([-1..1, -1..1]
                .combinations() as List<List<Integer>>)
                .collect {loc.plus(it[0], it[1])}
                .findAll (isValidMove.curry(board)) as Set<Location>
    }
}
