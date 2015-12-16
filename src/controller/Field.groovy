package controller

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * Created by Jarrett on 12/16/15.
 */
@ToString
@EqualsAndHashCode
@AutoClone(style = AutoCloneStyle.SIMPLE)
class Field {
    final Location pos
    final Color color
    Piece piece
}
