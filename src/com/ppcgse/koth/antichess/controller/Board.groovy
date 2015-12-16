package com.ppcgse.koth.antichess.controller;

import com.ppcgse.koth.antichess.pieces.Bishop;
import com.ppcgse.koth.antichess.pieces.King;
import com.ppcgse.koth.antichess.pieces.Knight;
import com.ppcgse.koth.antichess.pieces.Pawn;
import com.ppcgse.koth.antichess.pieces.Queen;
import com.ppcgse.koth.antichess.pieces.Rook

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
public class Board {
    private ArrayList<ArrayList<Field>> fields;
    public static final int BOARD_LENGTH = 8;

    public Board() {
        fields = ([{new ArrayList<Field>()}] * BOARD_LENGTH)*.call()
        initialize()
    }

    void initialize() {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                Color color = (i + j) % 2 == 0 ? Color.BLACK : Color.WHITE;
                fields[i][j] = new Field(pos: new Location(i, j), color: color);
            }
        }

        def baseOrder =
                [Rook.class,
                 Knight.class,
                 Bishop.class,
                 Queen.class,
                 King.class,
                 Bishop.class,
                 Knight.class,
                 Rook.class];

        //pawns
        for (int i = 0; i < BOARD_LENGTH; i++) {
            fields[i][0].setPiece(PieceFactory.buildPiece(baseOrder[i], Color.WHITE, new Location(0, i)));
            fields[i][1].setPiece(PieceFactory.buildPiece(Pawn.class, Color.WHITE, new Location(1, i)));
            fields[i][6].setPiece(PieceFactory.buildPiece(Pawn.class, Color.BLACK, new Location(6, i)));
            fields[i][7].setPiece(PieceFactory.buildPiece(baseOrder[i], Color.BLACK, new Location(7, i)));
        }
    }

    public Field[][] getFields() {
        return fields;
    }

    //returns whether a piece got captured
    public boolean movePiece(Move move) {
        boolean capture = false;
        Piece piece = move.getPiece();
        Location dest = move.getDestination();
        if (!dest.isOutside()) {
            capture = fields[dest.getX()][dest.getY()].hasPiece();
            // upgrade pawn
            if (piece.getType() == PieceType.PAWN && (dest.getY() == 0 || dest.getY() == BOARD_LENGTH - 1)) {
                fields[dest.getX()][dest.getY()].setPiece(new Queen(piece.getTeam(), dest));
            } else {
                fields[dest.getX()][dest.getY()].setPiece(piece);
            }
            if (piece.getType() == PieceType.PAWN) {
                ((Pawn) piece).setMoved();
            }
            //remove piece on old field
            fields[piece.getPos().getX()][piece.getPos().getY()].setPiece(null);
            //update position
            piece.setPos(dest);
        }
        return capture;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int j = BOARD_LENGTH - 1; j >= 0; j--) {
            for (int i = 0; i < BOARD_LENGTH; i++) {
                builder.append(fields[i][j]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
