package com.ppcgse.koth.antichess.pieces

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import com.ppcgse.koth.antichess.controller.Location
import com.ppcgse.koth.antichess.controller.Board
import com.ppcgse.koth.antichess.controller.Color
import com.ppcgse.koth.antichess.controller.Piece
import com.ppcgse.koth.antichess.controller.PieceType;

@EqualsAndHashCode
@TupleConstructor
@AutoClone(style = AutoCloneStyle.SIMPLE)
public class Pawn extends Piece {

    public Pawn(Color team, Location pos) {
        super(team, pos, PieceType.PAWN);
    }

    @Override
    public Set<Location> getValidDestinationSet(Board board) {
        int direction = getTeam() == Color.WHITE ? 1 : -1;

        getAttackMoves(board, direction) + getForwardMoves(board, direction)
    }

    private Set<Location> getAttackMoves(Board board, int direction) {

        def left = loc.plus(-1, direction)
        def right = loc.plus(1, direction)

        [left, right].findAll {isValidMove(board, it) && board[it].piece?.team == team.opposite()}
    }

    private Set<Location> getForwardMoves(Board board, int direction) {
        def base = loc.plus(0, direction)
        def doubMove = loc.plus(0, direction*2)
        def isValMove = {Location loc -> isValidMove(board, loc) && board[loc].piece == null}

        if (isValMove(base)) {
            if (canDoubleMove() == false)
                return [base]
            else if (isValMove(doubMove))
                return [base, doubMove]
            else
                return [base]
        } else
            return []
    }

    private boolean canDoubleMove() {
        loc.y == 1 || loc.y == 6
    }
}
