package com.ppcgse.koth.antichess.controller

import com.ppcgse.koth.antichess.pieces.*
import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

@EqualsAndHashCode
@TupleConstructor
@AutoClone(style = AutoCloneStyle.CLONE)
public class Board {
    public Map<Location, Field> fields;
    public static final int BOARD_LENGTH = 8;
    public static final boolean USE_UTF8_TO_STRING = true

    public Board() {
        fields = [:]
        initialize()
    }

    void initialize() {
        for (int x = 0; x < BOARD_LENGTH; x++) {
            for (int y = 0; y < BOARD_LENGTH; y++) {
                def loc = new Location(x, y)
                fields[loc] = new Field(loc);
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
            fields[new Location(i, 0)].setPiece(PieceFactory.buildPiece(baseOrder[i], Color.WHITE, new Location(i, 0)));
            fields[new Location(i, 1)].setPiece(PieceFactory.buildPiece(Pawn.class, Color.WHITE, new Location(i, 1)));
            fields[new Location(i, 6)].setPiece(PieceFactory.buildPiece(Pawn.class, Color.BLACK, new Location(i, 6)));
            fields[new Location(i, 7)].setPiece(PieceFactory.buildPiece(baseOrder[i], Color.BLACK, new Location(i, 7)));
        }
    }

    //returns whether a piece got captured
    public boolean movePiece(Player player, Move move) {
        def piece = move.getPiece();
        def dest = move.getDestination();

        if (!dest.isValid())
            return false

        def capture = this[dest].hasPiece();
        // upgrade pawn
        if (piece.getType() == PieceType.PAWN && isHomeRow(dest)) {
            if (player.pieceUpgradeType == null)
                throw new IllegalStateException("Unable to upgrade piece with Player#pieceUpgradeType undefined")
            def newPiece = PieceFactory.buildPiece(player.pieceUpgradeType.clazz, piece.team, dest)
            this[dest].setPiece(newPiece);
        } else
            this[dest].setPiece(piece);

        //remove piece on old field
        this[piece.getLoc()].setPiece(null);
        //update position
        piece.setLoc(dest);

        return capture;
    }

    private static boolean isHomeRow(Location loc) {
        return (loc.y == 1 || loc.y == BOARD_LENGTH - 1)
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int j = BOARD_LENGTH - 1; j >= 0; j--) {
            for (int i = 0; i < BOARD_LENGTH; i++) {
                def color = this[i, j].piece?.team
                def out = USE_UTF8_TO_STRING ?
                        (this[i, j].piece?.type?.getUtfChr(color?.opposite()) ?: '\u2014') :
                        (this[i, j].piece?.type?.getShortStr() ?: '-')
                builder.append(color == Color.BLACK ? out : out.toLowerCase());
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public Field getFieldAtLoc(Location loc) {
        return fields[loc]
    }

    public Field getAt(Location loc) {
        return getFieldAtLoc(loc)
    }

    public Field getFieldAtLoc(int x, int y) {
        return getFieldAtLoc(new Location(x: x, y: y))
    }

    public Field getAt(int x, int y) {
        return getFieldAtLoc(x, y)
    }
}
