package game.level.blocks.collision;

@FunctionalInterface
public interface Collidable {
    public void hit(int blockX, int blockY);
}
