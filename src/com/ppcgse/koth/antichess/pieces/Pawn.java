package com.ppcgse.koth.antichess.pieces;

import com.ppcgse.koth.antichess.controller.*;

import java.util.Set;
import java.util.HashSet;

public class Pawn extends Piece {
    private boolean hasMoved = false;

    public Pawn(Color team, Location pos) {
        super(team, pos, PieceType.PAWN);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        Set<Location> dests = new HashSet<>();
        Field[][] fields = board.getFields();

        int offsetY = getTeam() == Color.WHITE ? 1 : -1;
        Location pos = getPos();

        Location oneStepPos = pos.add(0, offsetY);
        if (isEmpty(fields, oneStepPos)) {
            dests.add(oneStepPos);
            if (!hasMoved) {
                Location twoStepPos = oneStepPos.add(0, offsetY);
                if (isEmpty(fields, twoStepPos)) {
                    dests.add(twoStepPos);
                }
            }
        }
        Location attackPos = oneStepPos.add(1, 0);
        if (hasEnemy(fields, attackPos)) {
            dests.add(attackPos);
        }
        attackPos = oneStepPos.add(-1, 0);
        if (hasEnemy(fields, attackPos)) {
            dests.add(attackPos);
        }

        return dests;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved() {
        hasMoved = true;
    }

    private boolean isEmpty(Field[][] fields, Location pos) {
        if (!pos.isOutside()) {
            if (!fields[pos.getX()][pos.getY()].hasPiece()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasEnemy(Field[][] fields, Location pos) {
        if (!pos.isOutside()) {
            Field field = fields[pos.getX()][pos.getY()];
            if (field.hasPiece() && field.getPiece().getTeam() == getTeam().opposite()) {
                return true;
            }
        }
        return false;
    }

    public Piece copy() {
        Pawn copy = new Pawn(getTeam(), getPos().copy());
        if (hasMoved) {
            copy.setMoved();
        }
        return copy;
    }
}
