package com.ppcgse.koth.antichess.controller;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private Color team;
    private boolean check = false;
    private boolean disqualified = false;

    public final List<Piece> getPieces(Board board) {
        List<Piece> pieces = new ArrayList<>();

        for (ArrayList<Field> rows : board.fields) {
            for (Field field : rows) {
                if (field.hasPiece()) {
                    Piece piece = field.getPiece();
                    if (piece.getTeam() == getTeam()) {
                        pieces.add(piece);
                    }
                }
            }
        }
        return pieces;
    }

    final void setTeam(Color team) {
        this.team = team;
    }

    public final Color getTeam() {
        return team;
    }

    final void setDisqualified() {
        disqualified = true;
    }

    public final boolean isDisqualified() {
        return disqualified;
    }

    public abstract Move getMove(Board board, Player enemy);

    public final String toString() {
        return "Player " + getClass() + " (" + getTeam() + ")";
    }
}
