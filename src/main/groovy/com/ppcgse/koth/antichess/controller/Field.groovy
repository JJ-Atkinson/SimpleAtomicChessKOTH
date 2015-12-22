package com.ppcgse.koth.antichess.controller

import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * Created by Jarrett on 12/16/15.
 */
@EqualsAndHashCode
@ToString(includePackage = false)
@Immutable
class Field {
    Location loc
    Piece piece

    public boolean hasPiece() {return piece != null}
}
