package com.ppcgse.koth.antichess.util

/**
 * Created by Jarrett on 12/16/15.
 */
class ListUtil {

    /**
     * I just had to do it ;)
     *
     * Usage:
     *  location = getNamedArrayLocation(array, x, y)
     *  location.get()
     *  location.set(5)
     *
     * Note that this is vulnerable to frame shift.
     *
     * @param array
     * @param location
     * @return
     */
    def <T> Map getNamedArrayLocation(ArrayList<ArrayList<T>> array, int x, int y) {
        return [get: {
            array[x][y]
        },      set: {T newItem ->
            array[x][y] = newItem
        }]
    }
}
