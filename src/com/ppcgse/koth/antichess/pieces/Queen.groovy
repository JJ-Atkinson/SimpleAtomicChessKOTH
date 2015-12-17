package com.ppcgse.koth.antichess.pieces;

import java.util.HashSet;
import java.util.Set;

import com.ppcgse.koth.antichess.controller.Board;
import com.ppcgse.koth.antichess.controller.Color;
import com.ppcgse.koth.antichess.controller.Field;
import com.ppcgse.koth.antichess.controller.Piece;
import com.ppcgse.koth.antichess.controller.PieceType;
import com.ppcgse.koth.antichess.controller.Location;

public class Queen extends Piece {

    public Queen(Color team, Location pos) {
        super(team, pos, PieceType.QUEEN);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        def dests = new HashSet<>();
        def fields = board.fields;

        //horizontal fields
        dests.addAll(super.getDests(fields, true, false, false, false));
        dests.addAll(super.getDests(fields, true, true, false, false));

        //vertical fields
        dests.addAll(super.getDests(fields, false, false, true, false));
        dests.addAll(super.getDests(fields, false, false, true, true));

        //diagonal fields
        dests.addAll(super.getDests(fields, true, false, true, false));
        dests.addAll(super.getDests(fields, true, true, true, true));
        dests.addAll(super.getDests(fields, true, true, true, false));
        dests.addAll(super.getDests(fields, true, false, true, true));

        return dests;
    }

    public Piece copy() {
        return new Queen(getTeam(), getPos().copy());
    }

}
