package com.ppcgse.koth.antichess.controller

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

/**
 * Created by Jarrett on 12/16/15.
 */
@EqualsAndHashCode
@TupleConstructor
@ToString(includePackage = false)
@AutoClone(style = AutoCloneStyle.SIMPLE)
class Field {
    final Location pos
    Piece piece

    public boolean hasPiece() {return piece != null}
}
