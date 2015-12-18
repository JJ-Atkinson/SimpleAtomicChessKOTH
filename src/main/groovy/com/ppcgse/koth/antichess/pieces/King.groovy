package com.ppcgse.koth.antichess.pieces

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import com.ppcgse.koth.antichess.controller.Location
import com.ppcgse.koth.antichess.controller.Piece
import com.ppcgse.koth.antichess.controller.PieceType
import com.ppcgse.koth.antichess.controller.Board
import com.ppcgse.koth.antichess.controller.Color

@TupleConstructor
@ToString
@AutoClone(style = AutoCloneStyle.SIMPLE)
public class King extends Piece {

    public King(Color team, Location pos) {
        super(team, pos, PieceType.KING);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        ([-1..1, -1..1]
                .combinations() as List<List<Integer>>)
                .collect {loc.plus(it[0], it[1])}
                .findAll (isValidMove.curry(board)) as Set<Location>
    }
}
