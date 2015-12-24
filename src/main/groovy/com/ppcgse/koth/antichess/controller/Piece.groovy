package com.ppcgse.koth.antichess.controller

import groovy.transform.EqualsAndHashCode
import groovy.transform.Memoized
import groovy.transform.ToString

@ToString(includeFields = true, excludes = ['metaClass', 'isValidMove'], includePackage = false)
@EqualsAndHashCode
public abstract class Piece {
    final Color team;
    final PieceType type;
    final Location loc;
    final boolean canBeKilledByExplosion;

    Piece(Color team, PieceType type, Location loc, boolean canBeKilledByExplosion) {
        this.team = team
        this.type = type
        this.loc = loc
        this.canBeKilledByExplosion = canBeKilledByExplosion
    }


    @Memoized
    protected final def isValidMove(Board board, Location test) {
        if (!test.isValid() || test == loc)
            return false
        def field = board[test]
        return field.piece?.team != this.team
    }

    /**
     * This returns all valid locations for the board. This does NOT take into account
     * invalid moves (e.g. killing that pawn next to the his king). It does however
     * take into account that it can't jump over its own pieces...
     * @param board
     * @param directionVectors
     * @return
     */
    public abstract Set<Location> getValidLocations(Board board);

    public Set<Move> getValidMoves(Board board) {
        return getValidLocations(board).collect {new Move(this, it)}
    }

    @Memoized
    protected def genValidDests(Board board, List<List<Integer>> directionVectors) {
        directionVectors.collect {
            def xVec = it[0]
            def yVec = it[1]
            def nx = loc.x + xVec
            def ny = loc.y + yVec

            def locations = []

            while (new Location(x: nx, y: ny).isValid()) {
                def field = board[nx, ny]
                def addLocation = { locations += new Location(x: nx, y: ny) }

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
