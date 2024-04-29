package util.asseteditor;

import game.level.Block;
import game.level.character.Powerup;
import game.level.character.enemy.Enemy;
import renderer.Animation;
import renderer.Sprite;

import java.util.HashMap;

public class Asset {
    private AssetType type;

    private Object asset;

    private String name;
    private String id;
    private Sprite sprite;
    private HashMap<String, Animation> animations;

    private Object getAsset() {
        return asset;
    }

    public void setAsset(Object asset) {
        this.asset = asset;
    }

    public AssetType getType() {
        return type;
    }

    public static Object getNewAsset(AssetType type) {
        return switch (type) {
            case BLOCK -> new Block();
            case ENEMY -> new Enemy();
            case POWERUP -> new Powerup();
            case SPRITE -> new Sprite();
            case ANIMATION -> new Animation();
        };
    }

    public Class getAssetClass() {
        return switch (type) {
            case BLOCK -> Block.class;
            case ENEMY -> Enemy.class;
            case POWERUP -> Powerup.class;
            case SPRITE -> Sprite.class;
            case ANIMATION -> Animation.class;
        };
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}
