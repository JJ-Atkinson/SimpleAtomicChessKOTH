package com.ppcgse.koth.antichess.controller;

public enum PieceType {
    PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING;

    public String getInitial() {
        [ROOK:   'R',
         PAWN:   'P',
         KNIGHT: 'K',
         KING:   'I',
         BISHOP: 'B',
         QUEEN:  'Q']
                ."${this.toString()}"
    }
}
