package com.ppcgse.koth.antichess.controller

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

@ToString
@EqualsAndHashCode
@AutoClone(style = AutoCloneStyle.SIMPLE)
public abstract class Piece {
    final Color team;
    final PieceType type;
    Location loc;

    Piece(Location loc, Color team, PieceType type) {
        this.loc = loc
        this.team = team
        this.type = type
    }

    protected def isValidMove = { Board board, Location test ->
        if (!test.isValid() || test == loc)
            return false
        def field = board.fields[test.x][test.y]
        return field.piece?.team != this.team
    }

    public abstract Set<Location> getValidDestinationSet(Board board);

    public Location[] getValidDestinations(Board board) {
        Set<Location> dests = getValidDestinationSet(board);
        return dests.toArray(new Location[dests.size()]);
    }

    protected Set<Location> genValidDests(Board board, List<List<Integer>> directionVectors) {
        def fields = board.fields
        directionVectors.collect {
            def xVec = it[0]
            def yVec = it[1]
            def nx = loc.x + xVec
            def ny = loc.y + yVec

            def locations = []

//            loop:
            while (new Location(x: nx, y: ny).isValid()) {
                def field = fields[nx][ny]
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
