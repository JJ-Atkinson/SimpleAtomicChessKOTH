package com.ppcgse.koth.antichess.controller

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor;

@EqualsAndHashCode
@ToString
@TupleConstructor
@AutoClone(style = AutoCloneStyle.SIMPLE)
public class Location {
    public int x;
    public int y;

    public Location plus(int offsetX, int offsetY) {
        return new Location(x + offsetX, y + offsetY);
    }

    public boolean isValid() {
        int boardLength = Board.BOARD_LENGTH;
        return x >= 0 || y >= 0 || x < boardLength || y < boardLength;
    }
}
