package com.ppcgse.koth.antichess.controller

import com.ppcgse.koth.antichess.player.SacrificeBot
import com.ppcgse.koth.antichess.player.SimpleBot
import com.ppcgse.koth.antichess.player.TestBot

import java.util.function.Supplier;

/**
 * Created by Jarrett on 12/16/15.
 */
public class PlayerFactory {
    public static final HashMap<Class<? extends Player>, Supplier<Player>> players =
            new HashMap<Class<? extends Player>, Supplier<Player>>() {{
                put(TestBot.class, { new TestBot() })
                put(SimpleBot.class, { new SimpleBot() })
                put(SacrificeBot.class, { new SacrificeBot() } )
//                put(YourBot.class, { new YourBot() } )
            }};

    public static Player buildPlayer(Class<? extends Player> clazz) {
        return (players.get(clazz) as Supplier<Player>).get();
    }
}
