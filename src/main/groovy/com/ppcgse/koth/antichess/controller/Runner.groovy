package com.ppcgse.koth.antichess.controller;

import java.util.HashMap;
import java.util.Map;

import com.ppcgse.koth.antichess.player.SimplePlayer;
import com.ppcgse.koth.antichess.player.TestPlayer;

public class Runner {
    private static final int GAMES_PER_PAIR = 10;
    private final Class[] classes = [SimplePlayer.class, TestPlayer.class];
    private final Map<Class<? extends Player>, Integer> scores = new HashMap<>();

    public static void main(String... args) {
        new Runner().runGames();
    }

    public Runner() {
        for (Class player : classes) {
            scores.put(player, 0);
        }
    }

    public void runGames() {

        for (int i = 0; i < classes.length - 1; i++) {
            for (int j = i + 1; j < classes.length; j++) {
                for (int k = 0; k < GAMES_PER_PAIR; k++) {
                    runGame(classes[i], classes[j], k >= GAMES_PER_PAIR / 2);
                }
            }
        }

        printScores();
    }

    private void runGame(Class class1, Class class2, boolean switchSides) {
        if (switchSides) { //switch sides
            Class tempClass = class2;
            class2 = class1;
            class1 = tempClass;
        }
        try {
            Player player1 = (Player) class1.newInstance();
            Player player2 = (Player) class2.newInstance();
            Game game = new Game(player1, player2);
            game.run();

            addResult(class1, game.getWhiteGameRes());
            addResult(class2, game.getBlackGameRes());
        } catch (Exception e) {
            System.out.println("Error in game between " + class1 + " and " + class2);
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
