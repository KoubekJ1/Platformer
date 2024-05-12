package game.level.blocks.collision;

import game.ProgramManager;
import game.level.blocks.Block;
import game.level.dynamicobject.DynamicObject;
import renderer.Animation;
import renderer.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

public class LuckyBlock implements Collidable {

    private DynamicObject content;

    public LuckyBlock(DynamicObject content) {
        this.content = content;
    }

    @Override
    public void hit(int blockX, int blockY) {
        content.setPosX(blockX);
        content.setPosX(blockY - 1);
        ProgramManager.getLevel().addObject(content);
    }

    public Block getLuckyBlock(DynamicObject content) {
        HashMap<String, Animation> animations = new HashMap<>();

        ArrayList<String> animationImages = new ArrayList<>();
        animationImages.add("blocks/luckyblock/1.png");
        animationImages.add("blocks/luckyblock/2.png");
        animationImages.add("blocks/luckyblock/3.png");

        animations.put("static", new Animation(animationImages, 50, true));

        Sprite sprite = new Sprite(animations, 1, 1);
        Block luckyBlock = new Block(sprite, true, new LuckyBlock(content));
        return luckyBlock;
    }
}
