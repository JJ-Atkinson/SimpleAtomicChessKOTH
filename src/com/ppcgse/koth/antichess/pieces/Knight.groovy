package com.ppcgse.koth.antichess.pieces;

import com.ppcgse.koth.antichess.controller.*

public class Knight extends Piece {

    public Knight(Color team, Location pos) {
        super(team, pos, PieceType.KNIGHT);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        def dirVectors = [[2, 1], [2, -1], [-2, 1], [-2, -1], [1, 2], [-1, 1], [1, -2], [-1, -2]]
        return dirVectors
                .collect {
                    loc.plus(it[0], it[1])
                }.findAll(isValidMove.curry(board))
    }
}
