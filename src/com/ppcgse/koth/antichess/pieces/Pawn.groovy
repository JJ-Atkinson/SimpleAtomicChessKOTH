package com.ppcgse.koth.antichess.pieces;

import com.ppcgse.koth.antichess.controller.*
import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor;

@EqualsAndHashCode
@ToString
@TupleConstructor
@AutoClone(style = AutoCloneStyle.SIMPLE)
public class Pawn extends Piece {

    public Pawn(Color team, Location pos) {
        super(team, pos, PieceType.PAWN);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        int direction = getTeam() == Color.WHITE ? 1 : -1;

        ([loc.plus(1, direction),
          loc.plus(-1, direction),
          loc.plus(0, direction)]
          + (canDoubleMove() ? [loc.plus(0, direction*2)] : []))
                .findAll (isValidMove.curry(board))
                .findAll {board.getFeildAtLoc(it).piece?.team != team} as Set<Location>
    }

    private boolean canDoubleMove() {
        loc.y == 1 || loc.y == 6
    }
}
