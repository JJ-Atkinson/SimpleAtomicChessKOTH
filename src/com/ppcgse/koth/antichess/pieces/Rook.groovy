package com.ppcgse.koth.antichess.pieces;

import com.ppcgse.koth.antichess.controller.*
import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor;

import java.util.Set;
import java.util.HashSet;

@ToString
@EqualsAndHashCode
@TupleConstructor
@AutoClone(style = AutoCloneStyle.SIMPLE)
public class Rook extends Piece {

    public Rook(Color team, Location pos) {
        super(team, pos, PieceType.ROOK);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        genValidDests(board, [[1, 0], [0, 1], [-1, 0], [0, -1]])
    }
}
