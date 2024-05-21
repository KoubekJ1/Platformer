package game.level.dynamicobject;

import game.ProgramManager;
import renderer.Sprite;

public class Finish extends DynamicObject {

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
        return null;
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
