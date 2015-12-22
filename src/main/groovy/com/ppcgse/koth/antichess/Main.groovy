package com.ppcgse.koth.antichess

import com.ppcgse.koth.antichess.controller.Runner

/**
 * Created by Jarrett on 12/15/15.
 */
public class Main {
    public static void main(String[] args) {
        println 'Running'
        new Runner().runGames();
//
//        new Game(new OnePlayBot(), new MyBot()).run()
    }
}
