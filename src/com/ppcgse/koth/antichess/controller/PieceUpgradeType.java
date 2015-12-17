package com.ppcgse.koth.antichess.controller;

import com.ppcgse.koth.antichess.pieces.*;

/**
 * Created by Jarrett on 12/16/15.
 */
public enum PieceUpgradeType {
    ROOK(Rook.class), KNIGHT(Knight.class), QUEEN(Queen.class), BISHOP(Bishop.class), KING(King.class);

    public final Class<? extends Piece> clazz;

    PieceUpgradeType(Class<? extends Piece> clazz) {
        this.clazz = clazz;
    }
}
