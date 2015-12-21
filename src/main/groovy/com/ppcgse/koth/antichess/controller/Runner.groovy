package com.ppcgse.koth.antichess.controller

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

        [classes, classes]
                .combinations()
                .findAll { it[0] != it[1] }
                .multiply(GAMES_PER_PAIR/2)
                .sort()
                .each {
                    runGame(PlayerFactory.buildPlayer(it[0]),
                            PlayerFactory.buildPlayer(it[1]));
                }

        printScores();
    }

    private void runGame(Player p1, Player p2) {
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
