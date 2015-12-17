package com.ppcgse.koth.antichess.controller;

public class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Location plus(int offsetX, int offsetY) {
        return new Location(x + offsetX, y + offsetY);
    }

    public boolean isValid() {
        int boardLength = Board.BOARD_LENGTH;
        return x >= 0 || y >= 0 || x < boardLength || y < boardLength;
    }

    public Location copy() {
        return new Location(x, y);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y;
    }
}
