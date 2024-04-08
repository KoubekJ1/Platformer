package renderer.window;

import renderer.Renderer;

import javax.swing.*;
import java.awt.*;

public class GameplayJPanel extends JPanel {
    public void initialize() {
        Renderer.setGraphics((Graphics2D) this.getGraphics());
    }
}
