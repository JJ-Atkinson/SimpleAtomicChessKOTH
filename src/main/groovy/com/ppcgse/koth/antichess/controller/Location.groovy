package com.ppcgse.koth.antichess.controller

import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString

@EqualsAndHashCode(includes = ['x', 'y'])
@ToString(includeFields = true, excludes = ['metaClass'], includePackage = false)
@Immutable
public class Location {
    int x;
    int y;

    public Location plus(int offsetX, int offsetY) {
        return new Location(x + offsetX, y + offsetY)
    }

    public boolean isValid() {
        int boardLength = Board.BOARD_LENGTH;
        return x >= 0 && y >= 0 && x < boardLength && y < boardLength
    }
}
