package com.ppcgse.koth.antichess.controller

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor;

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

    // vert = vertical, neg = negative, hor = horizontal
    protected Set<Location> getDests(Field[][] fields, boolean vert, boolean negVert, boolean hor, boolean negHor) {
        Set<Location> dests = new HashSet<>();
        Location pos = getPos();
        Color enemy = getTeam().opposite();

        for (int offset = 1; offset < BOARD_LENGTH; offset++) {
            int dx = !hor ? 0 :
                    negHor ? -offset : offset;
            int dy = !vert ? 0 :
                    negVert ? -offset : offset;
            Location futurePos = pos.plus(dx, dy);
            if (!futurePos.isValid()) {
                break;
            }
            Field field = fields[futurePos.getX()][futurePos.getY()];
            if (!field.hasPiece()) {
                dests.add(futurePos);
            } else if (field.getPiece().getTeam() == enemy) {
                dests.add(futurePos);
                break;
            } else {
                break;
            }
        }
        return dests;
    }
}
