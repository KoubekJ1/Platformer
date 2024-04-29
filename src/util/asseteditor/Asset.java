package util.asseteditor;

import game.level.Block;
import game.level.character.Powerup;
import game.level.character.enemy.Enemy;
import renderer.Animation;
import renderer.Sprite;

public class Asset {
    private AssetType assetType;

    private Object asset;
    private AssetType type;

    private Object getAsset() {
        return asset;
    }

    public void setAsset(Object asset) {
        this.asset = asset;
    }

    public AssetType getAssetType() {
        return assetType;
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
}
