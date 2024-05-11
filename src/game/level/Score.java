package game.level;

import game.level.dynamicobject.player.Player;

import java.util.HashMap;

public class Score {
    private static final int[] MULTIPLIER_PROGRESSION = new int[]{1, 2, 4, 5, 8, 10, 20, 40, 50, 80};

    private long score;

    private HashMap<Player, Integer> multiplier;

    public Score() {
        this.score = 0;
        this.multiplier = new HashMap<>();
    }

    public void increaseMultiplier(Player player) {
        if (!multiplier.containsKey(player)) {
            multiplier.put(player, 0);
            return;
        }
        int nextMultiplier = multiplier.get(player) + 1;
        if (nextMultiplier >= MULTIPLIER_PROGRESSION.length) return;
        multiplier.put(player, nextMultiplier);
    }

    public void resetMultiplier(Player player) {
        multiplier.put(player, 0);
    }

    public int getMultiplier(Player player) {
        if (!multiplier.containsKey(player)) {
            multiplier.put(player, 0);
        }
        return MULTIPLIER_PROGRESSION[multiplier.get(player)];
    }

    public void addScore(int score) {
        this.score += score;
    }

    public long getScore() {
        return score;
    }
}
