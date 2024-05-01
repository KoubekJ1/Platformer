package game.level.character;

import game.ProgramManager;

public class Powerup extends DynamicObject {

    @Override
    protected void objectUpdate(float dt) {

    }

    @Override
    protected void damage() {
        kill();
    }

    @Override
    protected void kill() {
        ProgramManager.getLevel().removeObject(this);
    }
}
