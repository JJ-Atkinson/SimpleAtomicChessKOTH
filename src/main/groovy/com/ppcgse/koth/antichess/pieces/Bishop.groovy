package com.ppcgse.koth.antichess.pieces

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import com.ppcgse.koth.antichess.controller.Board
import com.ppcgse.koth.antichess.controller.Color
import com.ppcgse.koth.antichess.controller.Location
import com.ppcgse.koth.antichess.controller.Piece
import com.ppcgse.koth.antichess.controller.PieceType;

@TupleConstructor
@EqualsAndHashCode
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
