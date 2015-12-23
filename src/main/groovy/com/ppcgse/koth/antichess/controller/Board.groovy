package com.ppcgse.koth.antichess.controller

import com.ppcgse.koth.antichess.pieces.*
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

@EqualsAndHashCode
@TupleConstructor
public class Board {
    public static final int BOARD_LENGTH = 8;
    public static boolean USE_UTF8_TO_STRING = false

    private final Map<Location, Field> fields

    public Board() {
        this(genFieldMap())
    }

    public Board(Map<Location, Field> fields) {
        this.fields = fields
    }

    private static Map<Location, Field> genFieldMap() {
        def ret = new HashMap<>()
        for (int x = 0; x < BOARD_LENGTH; x++) {
            for (int y = 0; y < BOARD_LENGTH; y++) {
                def loc = new Location(x, y)
                ret.put(loc, new Field(loc, null));
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
            def locations = [top       : new Location(i, 6),
                             veryTop   : new Location(i, 7),
                             bottom    : new Location(i, 1),
                             veryBottom: new Location(i, 0)]

            ret.put(locations.veryBottom,
                    new Field(locations.veryBottom,
                            PieceFactory.buildPiece(baseOrder[i], Color.WHITE, locations.veryBottom)))
            ret.put(locations.bottom,
                    new Field(locations.bottom,
                            PieceFactory.buildPiece(Pawn.class,   Color.WHITE, locations.bottom)))
            ret.put(locations.top,
                    new Field(locations.top,
                            PieceFactory.buildPiece(Pawn.class,   Color.BLACK, locations.top)))
            ret.put(locations.veryTop,
                    new Field(locations.veryTop,
                            PieceFactory.buildPiece(baseOrder[i], Color.BLACK, locations.veryTop)))
        }
        ret
    }

    //returns whether a piece got captured
    public Board movePiece(Player player, Move move) {
        def newFields = fields.clone() as Map<Location, Field>
        def piece = move.getPiece();
        def dest = move.getDestination();
        def oldLoc = piece.loc

        if (!dest.isValid())
            return this

        // upgrade pawn
        if (piece.getType() == PieceType.PAWN && isHomeRow(dest)) {
            if (player.pieceUpgradeType == null)
                throw new IllegalStateException("Unable to upgrade piece with Player#pieceUpgradeType undefined")
            def newPiece = PieceFactory.buildPiece(player.pieceUpgradeType.clazz, piece.team, dest)
            newFields[dest] = new Field(dest, newPiece);
        } else
            newFields[dest] = new Field(dest,
                    PieceFactory.buildPiece(piece.getClass() as Class<? extends Piece>,
                            piece.team, dest))

        //remove piece on old field
        newFields[oldLoc] = new Field(oldLoc, null);

        new Board(newFields)
    }


    static public Set<Location> getExplosionLocations(Location center) {
        return [-1..1, -1..1]
                .combinations()
                .collect {new Location(it[0], int[1])}
                .findAll {it.isValid()}
    }

    /**
     * Prevent killing your king
     * @param move
     * @param board
     * @return
     */
    static public boolean isValidMove(Move move, Board board) {
        def dest = move.destination
        def piece = move.piece
        def pieceTeam = piece.team
        def piecesDestroyed = getExplosionLocations(dest)
                                .collect {board[it].piece}
                                .findAll {it != null && it.canBeKilledByExplosion}
        return piecesDestroyed.find {it.type == PieceType.KING && it.team != pieceTeam}
    }

    static public boolean isInCheck(Player player, Board board) {

    }

    static public Set<Move> movesThatCauseCheck(Player player, Player enemy, Board board) {

    }

    static public Set<Move> isCheckmate(Player player, Player enemy, Board board) {

    }


    private static boolean isHomeRow(Location loc) {
        return (loc.y == 1 || loc.y == BOARD_LENGTH - 1)
    }

    public Map<Location, Field> getFields() {
        fields.clone() as Map<Location, Field>
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int j = BOARD_LENGTH - 1; j >= 0; j--) {
            for (int i = 0; i < BOARD_LENGTH; i++) {
                def color = this[i, j].piece?.team//?.opposite() //<- use if you have a dark console
                def out = USE_UTF8_TO_STRING ?
                        (this[i, j].piece?.type?.getUtfChr(color) ?: '\u2014') :
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
