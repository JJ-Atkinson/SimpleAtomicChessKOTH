package com.ppcgse.koth.antichess

import com.ppcgse.koth.antichess.controller.Game
import com.ppcgse.koth.antichess.player.MyBot
import com.ppcgse.koth.antichess.player.OnePlayBot

/**
 * Created by Jarrett on 12/15/15.
 */
public class Main {
    public static void main(String[] args) {
//        println 'Running'
//        new Runner().runGames();

        new Game(new OnePlayBot(), new MyBot()).run()
    }
}
