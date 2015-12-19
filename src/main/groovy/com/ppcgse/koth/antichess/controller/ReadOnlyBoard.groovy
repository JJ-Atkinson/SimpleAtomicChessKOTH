package com.ppcgse.koth.antichess.controller

import groovy.transform.Immutable


/**
 * Created by Jarrett on 12/19/15.
 */

@Immutable
class ReadOnlyBoard {
    private Board backing;

    public Field getFieldAtLoc(Location loc) {
        return backing[loc].clone()
    }

    public Field getAt(Location loc) {
        return getFieldAtLoc(loc)
    }

    public Field getFieldAtLoc(int x, int y) {
        return getFieldAtLoc(new Location(x: x, y: y))
    }

    public Field getAt(int x, int y) {
        return getFieldAtLoc(x, y)
    }
}
