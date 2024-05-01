package game.level.character;

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
        
    }
}
