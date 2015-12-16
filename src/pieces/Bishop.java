package pieces;

import controller.*;

import java.util.Set;
import java.util.HashSet;

public class Bishop extends Piece {

    public Bishop(Color team, Location pos) {
        super(team, pos, PieceType.BISHOP);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        Set<Location> dests = new HashSet<>();
        Field[][] fields = board.getFields();

        //diagonal fields
        dests.addAll(super.getDests(fields, true, false, true, false));
        dests.addAll(super.getDests(fields, true, true, true, true));
        dests.addAll(super.getDests(fields, true, true, true, false));
        dests.addAll(super.getDests(fields, true, false, true, true));

        return dests;
    }

    public Piece copy() {
        return new Bishop(getTeam(), getPos().copy());
    }

}
