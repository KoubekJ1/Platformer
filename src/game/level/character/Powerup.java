package game.level.character;

import game.ProgramManager;

public class Powerup extends DynamicObject {

    private static final float POWERUP_SPEED = 1;

    private boolean direction;

    public Powerup() {
        this.direction = true;
    }

    @Override
    protected void objectUpdate(float dt) {
        if (rightBlockCollisionCheck()) {
            switchDirection();
        }
        if (leftBlockCollisionCheck()) {
            switchDirection();
        }

        velocityX = POWERUP_SPEED * getDirection() * dt;
    }

    private int getDirection() {
        if (direction) {
            return 1;
        } else {
            return -1;
        }
    }

    private void switchDirection() {
        direction = !direction;
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
