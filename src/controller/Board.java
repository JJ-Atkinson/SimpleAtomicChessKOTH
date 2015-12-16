package controller;

import java.util.Arrays;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public class Board {
    private Field[][] fields;
    public static final int BOARD_LENGTH = 8;

    public Board() {
        fields = new Field[BOARD_LENGTH][BOARD_LENGTH];
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

    public Board copy() {
        Board copy = new Board();
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                copy.fields[i][j] = fields[i][j].copy();
            }
        }
        return copy;
    }

    void initialize() {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                Color color = (i + j) % 2 == 0 ? Color.BLACK : Color.WHITE;
                fields[i][j] = new Field(new Location(i, j), color);
            }
        }

        fields[0][0].setPiece(new Rook(Color.WHITE, new Location(0, 0)));
        fields[1][0].setPiece(new Knight(Color.WHITE, new Location(1, 0)));
        fields[2][0].setPiece(new Bishop(Color.WHITE, new Location(2, 0)));
        fields[3][0].setPiece(new Queen(Color.WHITE, new Location(3, 0)));
        fields[4][0].setPiece(new King(Color.WHITE, new Location(4, 0)));
        fields[5][0].setPiece(new Bishop(Color.WHITE, new Location(5, 0)));
        fields[6][0].setPiece(new Knight(Color.WHITE, new Location(6, 0)));
        fields[7][0].setPiece(new Rook(Color.WHITE, new Location(7, 0)));

        fields[0][7].setPiece(new Rook(Color.BLACK, new Location(0, 7)));
        fields[1][7].setPiece(new Knight(Color.BLACK, new Location(1, 7)));
        fields[2][7].setPiece(new Bishop(Color.BLACK, new Location(2, 7)));
        fields[3][7].setPiece(new Queen(Color.BLACK, new Location(3, 7)));
        fields[4][7].setPiece(new King(Color.BLACK, new Location(4, 7)));
        fields[5][7].setPiece(new Bishop(Color.BLACK, new Location(5, 7)));
        fields[6][7].setPiece(new Knight(Color.BLACK, new Location(6, 7)));
        fields[7][7].setPiece(new Rook(Color.BLACK, new Location(7, 7)));

        //pawns
        for (int i = 0; i < BOARD_LENGTH; i++) {
            fields[i][1].setPiece(new Pawn(Color.WHITE, new Location(i, 1)));
            fields[i][6].setPiece(new Pawn(Color.BLACK, new Location(i, 6)));
        }
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(fields);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Board other = (Board) obj;
        if (!Arrays.deepEquals(fields, other.fields))
            return false;
        return true;
    }
}
