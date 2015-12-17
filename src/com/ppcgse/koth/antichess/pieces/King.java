package com.ppcgse.koth.antichess.pieces;

import com.ppcgse.koth.antichess.controller.*;

import java.util.Set;
import java.util.HashSet;

public class King extends Piece {

    public King(Color team, Location pos) {
        super(team, pos, PieceType.KING);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        Set<Location> dests = new HashSet<>();
        Field[][] fields = board.getFields();
        Location pos = getPos();
        Color enemy = getTeam().opposite();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                Location futurePos = pos.plus(x, y);
                if (!futurePos.isValid()) {
                    Field field = fields[futurePos.getX()][futurePos.getY()];
                    if (!field.hasPiece() || field.getPiece().getTeam() == enemy) {
                        dests.add(futurePos);
                    }
                }
            }
        }
        return dests;
    }

    public Piece copy() {
        return new King(getTeam(), getPos().copy());
    }

}
