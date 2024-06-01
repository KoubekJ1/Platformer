package game.level.dynamicobject;

import game.ProgramManager;
import renderer.Sprite;

/**
 * Finish objects are displayed as flags that the player must reach to win the game
 */
public class Finish extends DynamicObject {

    /**
     * Constructs a new Finish instance in the given coordinates
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Finish(int x, int y) {
        this.sprite = new Sprite("flag.png", 2, 2);
        this.setGravity(false);
        setPosX(x);
        setPosY(y);
    }

    @Override
    protected void objectUpdate(float dt) {

    }

    @Override
    protected void damage() {
        kill();
    }

    @Override
    public void kill() {
        ProgramManager.getLevel().removeObject(this);
    }

    @Override
    public String getObjectCategory() {
        return "finish";
    }

    @Override
    protected String getAssetDirectory() {
        return null;
    }

    @Override
    protected String getAssetExtension() {
        return null;
    }
}
