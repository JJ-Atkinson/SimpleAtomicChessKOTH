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
        put(Bishop.class,  Bishop::new);
        put(King.class,    King::new);
        put(Knight.class,  Knight::new);
        put(Pawn.class,    Pawn::new);
        put(Queen.class,   Queen::new);
        put(Rook.class,    Rook::new);
    }};

    public static Piece buildPiece(Class<? extends Piece> clazz, Color color, Location location) {
        return pieces.get(clazz).apply(color, location);
    }
}
