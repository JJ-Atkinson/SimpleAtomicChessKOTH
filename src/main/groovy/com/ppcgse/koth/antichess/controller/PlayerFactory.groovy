package com.ppcgse.koth.antichess.controller

import com.ppcgse.koth.antichess.player.*

import java.util.function.Supplier

/**
 * Created by Jarrett on 12/16/15.
 */
public class PlayerFactory {
    public static final HashMap<Class<? extends Player>, Supplier<Player>> players =
            new HashMap<Class<? extends Player>, Supplier<Player>>() {{
                put(RandomBot.class, { new RandomBot() })
                put(OnePlayBot.class, { new OnePlayBot() })
//                put(YourBot.class, { new YourBot() } )
            }};

    public static Player buildPlayer(Class<? extends Player> clazz) {
        return (players.get(clazz) as Supplier<Player>).get();
    }
}
