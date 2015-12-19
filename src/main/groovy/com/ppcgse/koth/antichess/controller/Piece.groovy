package com.ppcgse.koth.antichess.controller

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

@ToString(includeFields = true, excludes = ['metaClass', 'isValidMove'], includePackage = false)
@EqualsAndHashCode
@AutoClone(style = AutoCloneStyle.SIMPLE)
public abstract class Piece {
    final Color team;
    final PieceType type;
    Location loc;

    Piece(Color team, Location loc, PieceType type) {
        this.loc = loc
        this.team = team
        this.type = type
    }

    protected def isValidMove = { Board board, Location test ->
        if (!test.isValid() || test == loc)
            return false
        def field = board[test.x, test.y]
        return field.piece?.team != this.team
    }

    public abstract Set<Location> getValidDestinationSet(Board board);

    protected Set<Location> genValidDests(Board board, List<List<Integer>> directionVectors) {
        directionVectors.collect {
            def xVec = it[0]
            def yVec = it[1]
            def nx = loc.x + xVec
            def ny = loc.y + yVec

            def locations = []

//            println this
//            println board
//            println directionVectors

            while (new Location(x: nx, y: ny).isValid()) {
                def field = board[nx, ny]
                def addLocation = {locations += new Location(x: nx, y: ny)}

                def fieldTeam = field.piece?.team
                if (fieldTeam == null) {
                    addLocation()
                } else if (fieldTeam == this.team) {
                    break
                } else {
                    addLocation()
                    break
                }

                nx += xVec
                ny += yVec
            }
            locations
        }.flatten() as Set<Location>
    }
}
