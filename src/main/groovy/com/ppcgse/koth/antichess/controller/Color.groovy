package com.ppcgse.koth.antichess.controller;

public enum Color {
    WHITE, BLACK;

    public Color opposite() {
        return this.next()
    }

    public String getInitial() {
        return this.name().charAt(0) + "";
    }
}
