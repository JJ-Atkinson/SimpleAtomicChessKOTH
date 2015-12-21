package com.ppcgse.koth.antichess.controller

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * Created by Jarrett on 12/16/15.
 */
@EqualsAndHashCode
@ToString(includePackage = false)
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class Field {
    final Location loc
    Piece piece

    public Field(Field other) {
        this.loc = other.loc
        piece = other.piece?.clone()
    }

    public Field(Location loc) {
        this.loc = loc
        this.piece = null
    }

    public boolean hasPiece() {return piece != null}
}
