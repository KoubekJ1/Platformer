package game.level.blocks.collision;

import game.ProgramManager;
import game.level.dynamicobject.DynamicObject;

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
}
