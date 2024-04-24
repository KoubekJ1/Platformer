package game.level.character;

import renderer.Sprite;

public class Character {
    public static final int GRAVITY_ACCELERATION = 1;
    public static final String CHARACTER_TEXTURES_PATH = "assets/textures/characters/";

    protected Sprite sprite;

    protected int posX;
    protected int posY;
    protected int velocityX;
    protected int velocityY;
}
