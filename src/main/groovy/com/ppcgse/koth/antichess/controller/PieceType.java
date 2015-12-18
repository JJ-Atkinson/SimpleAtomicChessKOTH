package com.ppcgse.koth.antichess.controller;

public enum PieceType {
    PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING;

    public String getInitial() {
        switch (this) {
            case ROOK:   return "R";
            case PAWN:   return "P";
            case KNIGHT: return "K";
            case KING:   return "&";
            case BISHOP: return "B";
            case QUEEN:  return "Q";
            default: return "I will never happen....";
        }
    }
}
