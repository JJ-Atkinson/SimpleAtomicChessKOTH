package com.ppcgse.koth.antichess.controller

import com.ppcgse.koth.antichess.player.SimplePlayer
import com.ppcgse.koth.antichess.player.TestPlayer

import java.util.function.Supplier;

/**
 * Created by Jarrett on 12/16/15.
 */
public class PlayerFactory {
    public static final HashMap<Class<? extends Player>, Supplier<Player>> players =
            new HashMap<Class<? extends Player>, Supplier<Player>>() {{
                put(TestPlayer.class,   { new TestPlayer() })
                put(SimplePlayer.class, { new SimplePlayer() } )
//                put(YourBot.class, { new YourBot() } )
            }};

    public static Player buildPlayer(Class<? extends Player> clazz) {
        return (players.get(clazz) as Supplier<Player>).get();
    }
}
