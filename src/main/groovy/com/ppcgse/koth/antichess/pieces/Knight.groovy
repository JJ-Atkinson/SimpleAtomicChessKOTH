package com.ppcgse.koth.antichess.pieces

import com.ppcgse.koth.antichess.controller.Board
import com.ppcgse.koth.antichess.controller.Color
import com.ppcgse.koth.antichess.controller.Location
import com.ppcgse.koth.antichess.controller.Piece
import com.ppcgse.koth.antichess.controller.PieceType

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
