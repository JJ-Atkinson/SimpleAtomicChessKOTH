package com.ppcgse.koth.antichess.controller;

import com.ppcgse.koth.antichess.pieces.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by Jarrett on 12/16/15.
 */
public class PieceFactory {
    public static final Map<Class<? extends Piece>, BiFunction<Color, Location, Piece>> pieces =
            new HashMap<Class<? extends Piece>, BiFunction<Color, Location, Piece>>() {{
        put(Bishop.class,  {team, loc -> new Bishop(team, loc)});
        put(King.class,    {team, loc -> new King(team, loc)});
        put(Knight.class,  {team, loc -> new Knight(team, loc)});
        put(Pawn.class,    {team, loc -> new Pawn(team, loc)});
        put(Queen.class,   {team, loc -> new Queen(team, loc)});
        put(Rook.class,    {team, loc -> new Rook(team, loc)});
    }};

    public static Piece buildPiece(Class<? extends Piece> clazz, Color color, Location location) {
        return pieces.get(clazz).apply(color, location);
    }
}
