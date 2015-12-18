package com.ppcgse.koth.antichess.controller;

public enum Color {
    WHITE, BLACK;

    public Color opposite() {
        return this == WHITE ? BLACK : WHITE;
    }

    public String getInitial() {
        return this.toString().charAt(0) + "";
    }
}
