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
        put(Bishop.class,  {team, loc -> new Bishop(team, loc)} as BiFunction<Color, Location, Piece>);
        put(King.class,    {team, loc -> new King(team, loc)} as BiFunction<Color, Location, Piece>);
        put(Knight.class,  {team, loc -> new Knight(team, loc)} as BiFunction<Color, Location, Piece>);
        put(Pawn.class,    {team, loc -> new Pawn(team, loc)} as BiFunction<Color, Location, Piece>);
        put(Queen.class,   {team, loc -> new Queen(team, loc)} as BiFunction<Color, Location, Piece>);
        put(Rook.class,    {team, loc -> new Rook(team, loc)} as BiFunction<Color, Location, Piece> );
    }};

    public static Piece buildPiece(Class<? extends Piece> clazz, Color color, Location location) {
        return pieces.get(clazz).apply(color, location);
    }
}
