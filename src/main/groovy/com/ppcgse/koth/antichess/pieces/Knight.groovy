package com.ppcgse.koth.antichess.pieces

import com.ppcgse.koth.antichess.controller.*
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

@EqualsAndHashCode
@TupleConstructor
public class Knight extends Piece {

    public Knight(Color team, Location loc) {
        super(team, PieceType.KNIGHT, loc, true);
    }

    @Override
    public Set<Location> getValidLocations(Board board) {
        def dirVectors = [[2, 1],   [2, -1],  // left
                          [-1, 2],  [1, 2],   // up
                          [-2, -1], [-2, 1],  // right
                          [1, -2],  [-1, -2]] // down
        return dirVectors
                .collect {
                    loc.plus(it[0], it[1])
                }.findAll {isValidMove(board, it)}
    }
}
