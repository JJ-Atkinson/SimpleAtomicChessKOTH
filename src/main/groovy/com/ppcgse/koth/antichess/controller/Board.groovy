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
                            PieceFactory.buildPiece(Pawn.class, Color.WHITE, locations.bottom)))
            ret.put(locations.top,
                    new Field(locations.top,
                            PieceFactory.buildPiece(Pawn.class, Color.BLACK, locations.top)))
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

        def deadPieces = getExplosionLocations(dest)
                .collect { this[it].piece }
                .findAll { it?.canBeKilledByExplosion }
                .plus(piece)

        if (this[dest].piece == null) {
            // check for pawn upgrade
            if (piece.type == PieceType.PAWN && isHomeRow(dest)) {
                if (player.pieceUpgradeType == null)
                    throw new IllegalStateException("Unable to upgrade piece with Player#pieceUpgradeType undefined")
                def newPiece = PieceFactory.buildPiece(player.pieceUpgradeType.clazz, piece.team, dest)
                newFields[dest] = new Field(dest, newPiece);
            } else
                newFields[dest] = new Field(dest,
                        PieceFactory.buildPiece(piece.getClass() as Class<? extends Piece>,
                                piece.team, dest))
        } else {
            deadPieces.each {
                newFields[it.loc] = new Field(it.loc, null)
            }

        }

        //remove piece on old field
        newFields[oldLoc] = new Field(oldLoc, null);

        new Board(newFields)
    }


    static public Set<Location> getExplosionLocations(Location center) {
        return [-1..1, -1..1]
                .combinations()
                .collect { center.plus(it[0], it[1]) }
                .findAll { it.isValid() }
    }

    /**
     * Prevent killing your king (that would be bad)
     * @param move
     * @param board
     * @return
     */
    public boolean isValidMove(Move move) {
        def dest = move.destination
        def piece = move.piece
        def pieceTeam = piece.team
        def piecesDestroyed = getExplosionLocations(dest)
                .collect { this[it].piece }
                .findAll { it != null && it.canBeKilledByExplosion }
        if (this[dest].piece?.team == pieceTeam.opposite())
            return piecesDestroyed.find { it.type == PieceType.KING && it.team == pieceTeam } == null
        else
            return true
    }

    public boolean isInCheck(Player player, Player enemy) {
        return movesThatCauseCheck(player, enemy).size() > 0
    }

    public List<Move> movesThatCauseCheck(Player player, Player enemy) {
        def king = player.getPieces(this).find { it.type == PieceType.KING }
        def otherPlayerMoves =
                enemy.getPieces(this)
                        .collect {
                            it.getValidLocations(this).collect { loc -> new Move(it, loc)}
                        }
                        .flatten() as List<Move>

        return otherPlayerMoves.findAll { it.destination == king.loc }
    }

    public boolean isCheckmate(Player player, Player enemy) {
        def king = player.getPieces(this).find { it.type == PieceType.KING }
        def otherPlayerMoves = (enemy.getPieces(this)
                .collect { it.getValidLocations(this) }
                .flatten() as List<Location>)
        def kingMoves = king.getValidLocations(this)
                .findAll { isValidMove(new Move(king, it)) }
        return otherPlayerMoves
                .collect { kingMoves.contains(it) }
                .inject { acc, it -> acc && it }
    }

    public Set<Move> genValidMoves(Player player, Player enemy) {
        def allMoves = player.getPieces(this)
                .collect { it.getValidMoves(this) }
                .flatten() as List<Move>
        def otherPlayerDests = enemy.getPieces(this)
                .collect {it.getValidLocations(this)}
                .flatten() as List<Location>
        def king = player.getPieces(this).find {it.type == PieceType.KING}

        if (isInCheck(player, enemy))
            return king.getValidMoves(this)
                    .findAll {!otherPlayerDests.contains(it.destination)}
        else
            return allMoves.findAll {isValidMove(it)}
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

        (0..7).each { builder.append(it) }
        builder.append('\n')
        for (int j = BOARD_LENGTH - 1; j >= 0; j--) {
            for (int i = 0; i < BOARD_LENGTH; i++) {
                def color = this[i, j].piece?.team//?.opposite() //<- use if you have a dark console
                def out = USE_UTF8_TO_STRING ?
                        (this[i, j].piece?.type?.getUtfChr(color) ?: '\u2014') :
                        (this[i, j].piece?.type?.getShortStr() ?: '-')
                builder.append(color == Color.BLACK ? out : out.toLowerCase());
            }
            builder.append(" $j\n");
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
