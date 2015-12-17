package com.ppcgse.koth.antichess.pieces;

import com.ppcgse.koth.antichess.controller.*

public class Knight extends Piece {

    public Knight(Color team, Location pos) {
        super(team, pos, PieceType.KNIGHT);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        Set<Location> dests = new HashSet<>();
        ArrayList<ArrayList<Field>> fields = board.fields;
        Location pos = getPos();
        Color enemy = getTeam().opposite();

        def futurePositions = genValidMoves(pos)

        for (Location futurePos : futurePositions) {
            if (!futurePos.isValid()) {
                Field field = fields[futurePos.getX()][futurePos.getY()];
                if (!field.hasPiece() || field.getPiece().getTeam() == enemy) {
                    dests.add(futurePos);
                }
            }
        }
        return dests;
    }

    def genValidMoves(Location baseLocation) {
        def dirVectors = [[2, 1], [2, -1], [-2, 1], [-2, -1], [1, 2], [-1, 1], [1, -2], [-1, -2]]
        return dirVectors.collect {baseLocation.plus(it[0], it[1])} as Set<Location>
    }
}
