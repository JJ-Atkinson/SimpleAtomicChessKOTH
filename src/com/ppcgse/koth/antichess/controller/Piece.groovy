package com.ppcgse.koth.antichess.controller

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

import java.util.stream.Stream;

import static com.ppcgse.koth.antichess.controller.Board.BOARD_LENGTH;

@ToString
@EqualsAndHashCode
@TupleConstructor
@AutoClone(style = AutoCloneStyle.SIMPLE)
public abstract class Piece {
    final Color team;
    Location pos;
    final PieceType type;

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
            def nx = pos.x + xVec
            def ny = pos.y + yVec

            def locations = []

            loop:
            while (new Location(x: nx, y: ny).isValid()) {
                def field = fields[nx][ny]
                def addLocation = {locations += new Location(x: nx, y: ny)}

                switch (field.piece) {
                    case null: addLocation();
                        break
                    case {Piece p -> p.team == this.team.opposite()}:
                        addLocation(); break loop;
                    case {Piece p -> p.team == this.team}:
                        break loop;
                }

                nx += xVec
                ny += yVec
            }
            locations
        }.flatten() as Set<Location>
    }
}
