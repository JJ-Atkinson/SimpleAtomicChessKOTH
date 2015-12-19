package com.ppcgse.koth.antichess.controller;

import java.util.HashMap;
import java.util.Map;

import com.ppcgse.koth.antichess.player.SimplePlayer;
import com.ppcgse.koth.antichess.player.TestPlayer;

public class Runner {
    private static final int GAMES_PER_PAIR = 10;
    private final Map<Class<? extends Player>, Integer> scores = new HashMap<>();

    public static void main(String... args) {
        new Runner().runGames();
    }

    public Runner() {
        for (Class player : PlayerFactory.players.keySet()) {
            scores.put(player, 0);
        }
    }

    public void runGames() {

        def classes = PlayerFactory.players.keySet()// as List<Class<? extends Player>>

        for (int i = 0; i < classes.size() - 1; i++) {
            for (int j = i + 1; j < classes.size(); j++) {
                for (int k = 0; k < GAMES_PER_PAIR; k++) {
                    runGame(PlayerFactory.buildPlayer(classes[i]),
                            PlayerFactory.buildPlayer(classes[j]),
                            k >= GAMES_PER_PAIR / 2);
                }
            }
        }

        printScores();
    }

    private void runGame(Player p1, Player p2, boolean switchSides) {
        if (switchSides) {
            def tmp = p1
            p1 = p2
            p2 = tmp
        }

        try {
            Game game = new Game(p1, p2);
            game.run();

            addResult(p1.getClass(), game.getWhiteGameRes());
            addResult(p2.getClass(), game.getBlackGameRes());
        } catch (Exception e) {
            System.err.println("Error in game between " + p1 + " and " + p2);
            e.printStackTrace()
        }
    }

    private void addResult(Class player, GameResult result) {
        scores.put(player, scores.get(player) + result.scoreChange);
    }

    private void printScores() {
        scores.entrySet()
                .stream()
                .sorted ({o1, o2 -> o1.getValue().compareTo(o2.getValue())} as Comparator)
                .forEachOrdered {
                        System.out.println(
                                it.getKey().getSimpleName() + " -> " +
                                        it.getValue())}
    }
}
