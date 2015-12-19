package com.ppcgse.koth.antichess.controller

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor
import com.ppcgse.koth.antichess.pieces.King
import com.ppcgse.koth.antichess.pieces.Knight
import com.ppcgse.koth.antichess.pieces.Pawn
import com.ppcgse.koth.antichess.pieces.Queen
import com.ppcgse.koth.antichess.pieces.Rook
import com.ppcgse.koth.antichess.pieces.Bishop

@EqualsAndHashCode
@TupleConstructor
@AutoClone(style = AutoCloneStyle.SIMPLE)
public class Board {
    public final Map<Location, Field> fields;
    public static final int BOARD_LENGTH = 8;

    public Board() {
        fields = [:]
        initialize()
    }

    void initialize() {
        for (int x = 0; x < BOARD_LENGTH; x++) {
            for (int y = 0; y < BOARD_LENGTH; y++) {
                def loc = new Location(x: x, y: y)
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
            fields[new Location(x: i, y: 0)].setPiece(PieceFactory.buildPiece(baseOrder[i], Color.WHITE, new Location(x: 0, y: i)));
            fields[new Location(x: i, y: 1)].setPiece(PieceFactory.buildPiece(Pawn.class, Color.WHITE, new Location(x: 1, y: i)));
            fields[new Location(x: i, y: 6)].setPiece(PieceFactory.buildPiece(Pawn.class, Color.BLACK, new Location(x: 6, y: i)));
            fields[new Location(x: i, y: 0)].setPiece(PieceFactory.buildPiece(baseOrder[i], Color.BLACK, new Location(x: 7, y: i)));
        }
    }

    //returns whether a piece got captured
    public boolean movePiece(Player player, Move move) {
        def piece = move.getPiece();
        def dest = move.getDestination();

        if (!dest.isValid())
            return false

        def capture = this[dest.x, dest.y].hasPiece();
        // upgrade pawn
        if (piece.getType() == PieceType.PAWN && isHomeRow(dest)) {
            if (player.pieceUpgradeType == null)
                throw new IllegalStateException("Unable to upgrade piece with Player#pieceUpgradeType undefined")
            def newPiece = PieceFactory.buildPiece(player.pieceUpgradeType.clazz, piece.team, dest)
            this[dest.x, dest.y].setPiece(newPiece);
        } else
            this[dest.x, dest.y].setPiece(piece);

        //remove piece on old field
        this[piece.getLoc().x, piece.getLoc().y].setPiece(null);
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
                def color = this[i, j].piece?.team
                def out = this[i, j].piece?.type?.getInitial() ?: "-"
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
