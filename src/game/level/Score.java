package game.level;

import game.level.dynamicobject.DynamicObject;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Score instances manage the score gained by the player and handle the score multiplier functionality
 */
public class Score implements Serializable {
    public static final int ENEMY_DAMAGE_SCORE = 100;
    public static final int KOOPA_DAMAGE_SCORE = 200;

    private static final int[] MULTIPLIER_PROGRESSION = new int[]{1, 2, 4, 5, 8, 10, 20, 40, 50, 80};

    private long score;

    private HashMap<DynamicObject, Integer> multiplier;

    /**
     * Creates a new Score instance
     */
    public Score() {
        this.score = 0;
        this.multiplier = new HashMap<>();
    }

    /**
     * Increases the object's score multiplier
     * @param dynamicObject the object
     */
    public void increaseMultiplier(DynamicObject dynamicObject) {
        if (!multiplier.containsKey(dynamicObject)) {
            multiplier.put(dynamicObject, 0);
        }
        int nextMultiplier = multiplier.get(dynamicObject) + 1;
        if (nextMultiplier >= MULTIPLIER_PROGRESSION.length) return;
        multiplier.put(dynamicObject, nextMultiplier);
    }

    /**
     * Resets the object's multiplier
     * @param dynamicObject the object
     */
    public void resetMultiplier(DynamicObject dynamicObject) {
        multiplier.put(dynamicObject, 0);
    }

    /**
     * Returns the multiplier of the given object
     * @param dynamicObject the object
     * @return the multiplier
     */
    public int getMultiplier(DynamicObject dynamicObject) {
        if (!multiplier.containsKey(dynamicObject)) {
            multiplier.put(dynamicObject, 0);
        }
        return MULTIPLIER_PROGRESSION[multiplier.get(dynamicObject)];
    }

    /**
     * Increases the score by the given amount
     * @param score the amount
     */
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
