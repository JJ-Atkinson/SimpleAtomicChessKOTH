package com.ppcgse.koth.antichess.pieces;

import com.ppcgse.koth.antichess.controller.*
import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor;

@TupleConstructor
@EqualsAndHashCode
@ToString
@AutoClone(style = AutoCloneStyle.SIMPLE)
public class Bishop extends Piece {
    public Bishop(Color team, Location pos) {
        super(team, pos, PieceType.BISHOP);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        return genValidDests(board, [[1, 1], [1, -1], [-1, -1], [-1, 1]]);
    }
}
