package com.ppcgse.koth.antichess.controller

import com.ppcgse.koth.antichess.pieces.*
import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * Created by Jarrett on 12/16/15.
 */
@EqualsAndHashCode
@ToString(includePackage = false)
@Immutable(knownImmutableClasses = [Knight, Bishop, King, Pawn, Queen, Rook, Piece])
class Field {
    Location loc
    Piece piece

    public boolean hasPiece() {return piece != null}
}
