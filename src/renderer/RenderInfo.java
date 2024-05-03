package renderer;

import game.level.Block;
import game.level.dynamicobject.DynamicObject;
import game.level.dynamicobject.player.camera.Camera;

import java.awt.*;
import java.util.LinkedList;

public class RenderInfo {
    private Color background;
    private Camera camera;
    private Block[][] blocks;
    private LinkedList<DynamicObject> dynamicObjects;

    private float frameRate = 0;

    public RenderInfo(Color background, Camera camera, Block[][] blocks, LinkedList<DynamicObject> dynamicObjects) {
        this.background = background;
        this.camera = camera;
        this.blocks = blocks;
        this.dynamicObjects = dynamicObjects;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[][] blocks) {
        this.blocks = blocks;
    }

    public LinkedList<DynamicObject> getDynamicObjects() {
        return dynamicObjects;
    }

    public void setDynamicObjects(LinkedList<DynamicObject> dynamicObjects) {
        this.dynamicObjects = dynamicObjects;
    }

    public float getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(float frameRate) {
        this.frameRate = frameRate;
    }
}
