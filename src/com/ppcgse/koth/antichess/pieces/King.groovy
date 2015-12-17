package com.ppcgse.koth.antichess.pieces;

import com.ppcgse.koth.antichess.controller.*
import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.ToString
import groovy.transform.TupleConstructor;

import java.util.Set;
import java.util.HashSet;

@TupleConstructor
@ToString
@AutoClone(style = AutoCloneStyle.SIMPLE)
public class King extends Piece {

    public King(Color team, Location pos) {
        super(team, pos, PieceType.KING);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        def dests = new HashSet<>();
        def fields = board.fields;
        def pos = getPos();
        def enemy = getTeam().opposite();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                Location futurePos = pos.plus(x, y);
                if (!futurePos.isValid()) {
                    Field field = fields[futurePos.x][futurePos.y];
                    if (!field.hasPiece() || field.getPiece().getTeam() == enemy) {
                        dests.add(futurePos);
                    }
                }
            }
        }
        return dests;
    }
}
