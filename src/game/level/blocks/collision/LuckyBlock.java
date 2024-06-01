package game.level.blocks.collision;

import game.ProgramManager;
import game.level.blocks.Block;
import game.level.dynamicobject.DynamicObject;
import renderer.Animation;
import renderer.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A lucky block is a block that contains a DynamicObject instance inside
 * The object is released when the lucky block is hit
 */
public class LuckyBlock implements Collidable {

    private DynamicObject content;

    /**
     * Creates a lucky block containing the given object
     * @param content the object
     */
    public LuckyBlock(DynamicObject content) {
        this.content = content;
    }

    @Override
    public void hit(int blockX, int blockY) {
        content.setPosX(blockX);
        content.setPosY(blockY - 1);
        ProgramManager.getLevel().addObject(content);
        content = null;
        ProgramManager.getLevel().addBlock(new Block("blocks/luckyblock/empty.png", true), blockX, blockY);
    }

    /**
     * Returns a lucky block containing the given object
     * @param content the object
     * @return a lucky block
     */
    public static Block getLuckyBlock(DynamicObject content) {
        HashMap<String, Animation> animations = new HashMap<>();

        ArrayList<String> animationImages = new ArrayList<>();
        animationImages.add("blocks/luckyblock/1.png");
        animationImages.add("blocks/luckyblock/2.png");
        animationImages.add("blocks/luckyblock/3.png");

        animations.put("static", new Animation(animationImages, new int[]{0, 1, 2, 1, 0},200, true));

        Sprite sprite = new Sprite(animations, 1, 1);
        Block luckyBlock = new Block(sprite, true, new LuckyBlock(content));
        return luckyBlock;
    }
}
