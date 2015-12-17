package com.ppcgse.koth.antichess.controller;

import com.ppcgse.koth.antichess.pieces.*
import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

@EqualsAndHashCode
@TupleConstructor
@AutoClone(style = AutoCloneStyle.SIMPLE)
public class Board {
    public final ArrayList<ArrayList<Field>> fields;
    public static final int BOARD_LENGTH = 8;

    public Board() {
        fields = ([{ new ArrayList<Field>() }] * BOARD_LENGTH)*.call()
        initialize()
    }

    void initialize() {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                Color color = (i + j) % 2 == 0 ? Color.BLACK : Color.WHITE;
                fields[i][j] = new Field(new Location(i, j), color);
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

        for (int i = 0; i < BOARD_LENGTH; i++) {
            fields[i][0].setPiece(PieceFactory.buildPiece(baseOrder[i], Color.WHITE, new Location(x: 0, y: i)));
            fields[i][1].setPiece(PieceFactory.buildPiece(Pawn.class, Color.WHITE, new Location(x: 1, y: i)));
            fields[i][6].setPiece(PieceFactory.buildPiece(Pawn.class, Color.BLACK, new Location(x: 6, y: i)));
            fields[i][7].setPiece(PieceFactory.buildPiece(baseOrder[i], Color.BLACK, new Location(x: 7, y: i)));
        }
    }

    //returns whether a piece got captured
    public boolean movePiece(Move move) {
        def piece = move.getPiece();
        def dest = move.getDestination();

        if (!dest.isValid())
            return false

        def capture = fields[dest.x][dest.y].hasPiece();
        // upgrade pawn
        if (piece.getType() == PieceType.PAWN && isHomeRow(dest))
            fields[dest.x][dest.y].setPiece(new Queen(piece.getTeam(), dest));
        else
            fields[dest.x][dest.y].setPiece(piece);

        if (piece.getType() == PieceType.PAWN) {
            ((Pawn) piece).setMoved();
        }
        //remove piece on old field
        fields[piece.getLoc().x][piece.getLoc().y].setPiece(null);
        //update position
        piece.setLoc(dest);

        return capture;
    }

    private static boolean isHomeRow(Location loc) {
        return (loc.x == 0 || loc.y == BOARD_LENGTH - 1)
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

    public Field getFeildAtLoc(Location loc) {
        return fields[loc.x][loc.y]
    }
}
