package com.ppcgse.koth.antichess.controller

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
public abstract class Player {
    private Color team;
    private boolean disqualified = false;
    public PieceUpgradeType pieceUpgradeType;

    public final List<Piece> getPieces(Board board) {
        (board.fields
                .values() as List<Field>)
                .collect {Field f -> f.piece}
                .findAll {Piece p -> p?.team == team}
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

    public abstract Move getMove(Board board, Player enemy, Set<Move> validMoves);

    @Override
    String toString() {
        return getClass().getSimpleName() + '(' + team + ')'
    }
}
