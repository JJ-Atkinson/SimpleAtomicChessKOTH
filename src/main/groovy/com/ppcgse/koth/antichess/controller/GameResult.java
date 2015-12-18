package com.ppcgse.koth.antichess.controller;

/**
 * Created by Jarrett on 12/16/15.
 */
public enum GameResult {
    DRAW(1), WIN(3), LOSE(0);

    public final int scoreChange;

    GameResult(int scoreChange) {
        this.scoreChange = scoreChange;
    }

}
