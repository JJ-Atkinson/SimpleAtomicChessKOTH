package com.ppcgse.koth.antichess.controller;

public enum PieceType {
    PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING;

    public String getUtfChr(Color side) {
        def black = [ROOK:   '\u265C',
                     PAWN:   '\u265F',
                     KNIGHT: '\u265E',
                     KING:   '\u265A',
                     BISHOP: '\u265D',
                     QUEEN:  '\u265B']
        def white = [ROOK:   '\u2656',
                     PAWN:   '\u2659',
                     KNIGHT: '\u2658',
                     KING:   '\u2654',
                     BISHOP: '\u2657',
                     QUEEN:  '\u2655']

        (side == Color.WHITE ? white : black)."${this.toString()}"
    }

    public String getShortStr() {
        def mapping = [ROOK:   'R',
                         PAWN:   'P',
                         KNIGHT: 'K',
                         KING:   'I',
                         BISHOP: 'B',
                         QUEEN:  'Q']
        return mapping."${this.toString()}"
    }
}
