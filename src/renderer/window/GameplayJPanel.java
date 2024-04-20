package renderer.window;

import game.ProgramManager;
import game.level.Block;
import game.level.Player;
import renderer.RenderInfo;
import renderer.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
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
    }

    private void updateBaseBlockSize() {
        baseBlockSize = this.getWidth() / 40.0f;
    }

    public void render(RenderInfo renderInfo) {
        this.renderInfo = renderInfo;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        currentTransform = (AffineTransform) renderInfo.getCamera().getTransform().clone();
        currentTransform.concatenate(defaultTransform);
        g2D.setTransform(currentTransform);

        // Rendering the player(s)
        for (Player player : renderInfo.getPlayers()) {
            if (player.getCurrentImage(baseBlockSize) == null) {
                throw new IllegalStateException("Player doesn't have an active image!");
            }
            renderTile(g2D, player.getCurrentImage(baseBlockSize), player.getPosition()[0], player.getPosition()[1]);
        }

        Block[][] blocks = renderInfo.getBlocks();
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (blocks[i][j] == null) continue;
                renderTile(g2D, blocks[i][j].getCurrentImage(baseBlockSize), i, j);
            }
        }

        if (ProgramManager.isDebug()) {
            g2D.setTransform(defaultTransform);
            g2D.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            g2D.setColor(Color.BLACK);
            g2D.drawString("FPS: " + renderInfo.getFrameRate(), 0, 20);
            float[] position = renderInfo.getPlayers().getFirst().getPosition();
            g2D.drawString("Player position: " + position[0] + ", " + position[1], 0, 40);
            float[] velocity = renderInfo.getPlayers().getFirst().getVelocity();
            g2D.drawString("Player velocity: " + velocity[0] + ", " + velocity[1], 0, 60);
        }
    }

    private void renderTile(Graphics2D graphics2D, BufferedImage image, float x, float y) {
        graphics2D.drawImage(image, (int) (x * baseBlockSize), (int) (y * baseBlockSize), null, null);
    }
}
