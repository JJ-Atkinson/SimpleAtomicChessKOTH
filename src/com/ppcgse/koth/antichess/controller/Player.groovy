package com.ppcgse.koth.antichess.controller;

public abstract class Player {
    private Color team;
    private boolean disqualified = false;

    public final List<Piece> getPieces(Board board) {
        (board.fields
                .flatten() as List<Field>)
                .findAll {Field f -> !f.hasPiece()?: f.piece.team == team}
                .collect {Field f -> f.piece};
    }

    final void setTeam(Color team) {
        this.team = team;
    }

    public final Color getTeam() {
        return team;
    }

    final void disqualify() {
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
