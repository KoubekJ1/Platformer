package renderer.window;

import game.ProgramManager;
import game.level.blocks.Block;
import game.level.Level;
import game.level.dynamicobject.DynamicObject;
import game.level.dynamicobject.player.Player;
import game.level.dynamicobject.player.camera.Camera;
import renderer.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameplayJPanel extends JPanel {
    private Level level;

    private float baseBlockSize;

    private static AffineTransform defaultTransform;
    private static AffineTransform currentTransform;

    private Font font;

    public GameplayJPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public void initialize() {
        Renderer.setPanel(this);
        defaultTransform = ((Graphics2D) this.getGraphics()).getTransform();
        currentTransform = new AffineTransform();
        // region Code from Stack Overflow - Source: https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
        try {
            this.font = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/font.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            this.font = new Font("Segoe UI", Font.PLAIN, 20);
        }
    }

    private void updateBaseBlockSize() {
        baseBlockSize = Math.round(this.getHeight() / 20.0f);
    }

    public void render() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (ProgramManager.getLevel() == null) return;
        Graphics2D g2D = (Graphics2D) g;

        this.level = ProgramManager.getLevel();

        if (level == null) {
            return;
        }

        // Reset transform
        g2D.setTransform(defaultTransform);

        // Render background
        g2D.setColor(level.getBackground());
        g2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Setup
        updateBaseBlockSize();
        Camera camera = level.getPlayers().getFirst().getCamera();
        currentTransform.setTransform(camera.getTransform().getScaleX(), 0, 0, camera.getTransform().getScaleY(), camera.getTransform().getTranslateX() * baseBlockSize * camera.getTransform().getScaleX(), camera.getTransform().getTranslateY() * baseBlockSize * camera.getTransform().getScaleX());
        g2D.setTransform(currentTransform);

        // Rendering the player(s)
        for (DynamicObject dynamicObject : ProgramManager.getLevel().getDynamicObjects()) {
            if (!isTileVisible(dynamicObject.getPosX(), dynamicObject.getPosY(), baseBlockSize, currentTransform)) continue;
            renderTile(g2D, dynamicObject.getCurrentImage(baseBlockSize), dynamicObject.getPosX(), dynamicObject.getPosY());
        }

        for (int i = 0; i < level.getLevelSizeX(); i++) {
            for (int j = 0; j < level.getLevelSizeY(); j++) {
                Block block = level.getBlock(i, j);
                if (block == null) continue;
                if (!isTileVisible(i, j, baseBlockSize, currentTransform)) continue;
                renderTile(g2D, block.getCurrentImage(baseBlockSize), i, j);
            }
        }

        // HUD
        g2D.setTransform(defaultTransform);
        g2D.setColor(Color.WHITE);
        // region "borrowed" code - Source: https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
        /*try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets")));
        } catch (IOException | FontFormatException e) {
            //Handle exception
        }*/
        // endregion
        g2D.setFont(font.deriveFont(baseBlockSize));
        g2D.drawString("Score", baseBlockSize, baseBlockSize);
        g2D.drawString(String.valueOf(level.getScore()), baseBlockSize, 2 * baseBlockSize);

        if (ProgramManager.isDebug()) {
            g2D.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            g2D.setColor(Color.BLACK);
            g2D.drawString("FPS: " + level.getFps(), 0, 20);
            Player player = ProgramManager.getLevel().getPlayers().getFirst();
            g2D.drawString("Player position: " + player.getPosX() + ", " + player.getPosY(), 0, 40);
            float[] velocity = player.getVelocity();
            g2D.drawString("Player velocity: " + velocity[0] + ", " + velocity[1], 0, 60);
            AffineTransform playerCamera = camera.getTransform();
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
