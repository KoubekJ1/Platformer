package game.level;

import game.level.dynamicobject.DynamicObject;

import java.util.HashMap;

public class Score {
    public static final int ENEMY_DAMAGE_SCORE = 100;
    public static final int KOOPA_DAMAGE_SCORE = 100;

    private static final int[] MULTIPLIER_PROGRESSION = new int[]{1, 2, 4, 5, 8, 10, 20, 40, 50, 80};

    private long score;

    private HashMap<DynamicObject, Integer> multiplier;

    public Score() {
        this.score = 0;
        this.multiplier = new HashMap<>();
    }

    public void increaseMultiplier(DynamicObject dynamicObject) {
        if (!multiplier.containsKey(dynamicObject)) {
            multiplier.put(dynamicObject, 0);
        }
        int nextMultiplier = multiplier.get(dynamicObject) + 1;
        if (nextMultiplier >= MULTIPLIER_PROGRESSION.length) return;
        multiplier.put(dynamicObject, nextMultiplier);
    }

    public void resetMultiplier(DynamicObject dynamicObject) {
        multiplier.put(dynamicObject, 0);
    }

    public int getMultiplier(DynamicObject dynamicObject) {
        if (!multiplier.containsKey(dynamicObject)) {
            multiplier.put(dynamicObject, 0);
        }
        return MULTIPLIER_PROGRESSION[multiplier.get(dynamicObject)];
    }

    public void addScore(int score) {
        this.score += score;
    }

    public long getScore() {
        return score;
    }

    @Override
    public String toString() {
        return String.valueOf(score);
    }
}
