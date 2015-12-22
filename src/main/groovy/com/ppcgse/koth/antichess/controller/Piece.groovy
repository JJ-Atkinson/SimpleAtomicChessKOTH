package com.ppcgse.koth.antichess.controller

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeFields = true, excludes = ['metaClass', 'isValidMove'], includePackage = false)
@EqualsAndHashCode
public abstract class Piece {
    final Color team;

    Piece(Color team, PieceType type, Location loc) {
        this.team = team
        this.type = type
        this.loc = loc
    }


    final PieceType type;
    final Location loc;

    protected def isValidMove = { Board board, Location test ->
        if (!test.isValid() || test == loc)
            return false
        def field = board[test]
        return field.piece?.team != this.team
    }

    public abstract Set<Location> getValidDestinationSet(Board board);

    protected def genValidDests = { Board board, List<List<Integer>> directionVectors ->
        directionVectors.collect {
            def xVec = it[0]
            def yVec = it[1]
            def nx = loc.x + xVec
            def ny = loc.y + yVec

            def locations = []

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
    }.memoize()
}
