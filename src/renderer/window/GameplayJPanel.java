package renderer.window;

import game.ProgramManager;
import game.level.Block;
import game.level.dynamicobject.DynamicObject;
import game.level.dynamicobject.player.Player;
import renderer.RenderInfo;
import renderer.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class GameplayJPanel extends JPanel {
    private RenderInfo renderInfo;
    private float baseBlockSize;

    private static AffineTransform defaultTransform;
    private static AffineTransform currentTransform;

    public GameplayJPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public void initialize() {
        Renderer.setPanel(this);
        defaultTransform = ((Graphics2D) this.getGraphics()).getTransform();
        currentTransform = new AffineTransform();
    }

    private void updateBaseBlockSize() {
        baseBlockSize = Math.round(this.getHeight() / 20.0f);
    }

    public void render(RenderInfo renderInfo) {
        this.renderInfo = renderInfo;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        if (renderInfo == null) {
            return;
        }

        // Reset transform
        g2D.setTransform(defaultTransform);

        // Render background
        g2D.setColor(renderInfo.getBackground());
        g2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Setup
        updateBaseBlockSize();
        currentTransform.setTransform(renderInfo.getCamera().getTransform().getScaleX(), 0, 0, renderInfo.getCamera().getTransform().getScaleY(), renderInfo.getCamera().getTransform().getTranslateX() * baseBlockSize * renderInfo.getCamera().getTransform().getScaleX(), renderInfo.getCamera().getTransform().getTranslateY() * baseBlockSize * renderInfo.getCamera().getTransform().getScaleX());
        g2D.setTransform(currentTransform);

        // Rendering the player(s)
        for (DynamicObject dynamicObject : renderInfo.getDynamicObjects()) {
            if (!isTileVisible(dynamicObject.getPosX(), dynamicObject.getPosY(), baseBlockSize, currentTransform)) continue;
            renderTile(g2D, dynamicObject.getCurrentImage(baseBlockSize), dynamicObject.getPosX(), dynamicObject.getPosY());
        }

        Block[][] blocks = renderInfo.getBlocks();
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (blocks[i][j] == null) continue;
                if (!isTileVisible(i, j, baseBlockSize, currentTransform)) continue;
                renderTile(g2D, blocks[i][j].getCurrentImage(baseBlockSize), i, j);
            }
        }

        if (ProgramManager.isDebug()) {
            g2D.setTransform(defaultTransform);
            g2D.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            g2D.setColor(Color.BLACK);
            g2D.drawString("FPS: " + renderInfo.getFrameRate(), 0, 20);
            Player player = ProgramManager.getLevel().getPlayers().getFirst();
            g2D.drawString("Player position: " + player.getPosX() + ", " + player.getPosY(), 0, 40);
            float[] velocity = player.getVelocity();
            g2D.drawString("Player velocity: " + velocity[0] + ", " + velocity[1], 0, 60);
            AffineTransform playerCamera = renderInfo.getCamera().getTransform();
            g2D.drawString("Camera position: " + playerCamera.getTranslateX() + ", " + playerCamera.getTranslateY(), 0, 80);
            g2D.drawString("Display scale: " + playerCamera.getScaleX(), 0, 100);
            AffineTransform finalTransform = currentTransform;
            g2D.drawString("Transform translate: " + finalTransform.getTranslateX() + ", " + finalTransform.getTranslateY(), 600, 20);
            g2D.drawString("Transform scale: " + finalTransform.getScaleX() + ", " + finalTransform.getScaleY(), 600, 40);
        }
    }

    private void renderTile(Graphics2D graphics2D, BufferedImage image, float x, float y) {
        graphics2D.drawImage(image, (int) (x * baseBlockSize), (int) (y * baseBlockSize), null, null);
    }

    private boolean isTileVisible(float x, float y, float blockSize, AffineTransform transform) {
        Point2D destinationPoint = new Point2D.Float();
        transform.transform(new Point2D.Float(x * blockSize, y * blockSize), destinationPoint);
        if ((destinationPoint.getX() + blockSize < 0 || destinationPoint.getX() > WindowManager.getResolution()[0] * (defaultTransform.getScaleX())) || (destinationPoint.getY() + blockSize < 0 || destinationPoint.getY() > WindowManager.getResolution()[1] * (defaultTransform.getScaleY()))) {
            return false;
        } else {
            return true;
        }
    }
}
