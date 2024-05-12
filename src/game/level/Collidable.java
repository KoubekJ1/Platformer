package game.level;

@FunctionalInterface
public interface Collidable {
    public void hit(int blockX, int blockY);
}
