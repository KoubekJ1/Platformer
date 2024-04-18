package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public abstract class AssetManager {
    private static HashMap<String, BufferedImage> textures = new HashMap<>();
    private static BufferedImage defaultImage;

    private static final String TEXTURES_PATH = "assets/textures/";

    static {
        try {
            defaultImage = ImageIO.read(new File(TEXTURES_PATH + "missing.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage getTexture(String path) {
        File textureFile = new File(TEXTURES_PATH + path);

        if (textures.containsKey(textureFile.getAbsolutePath())) {
            return textures.get(textureFile.getAbsolutePath());
        } else {
            try {
                textures.put(textureFile.getAbsolutePath(), ImageIO.read(textureFile));
                return textures.get(textureFile.getAbsolutePath());
            } catch (IOException e) {
                return defaultImage;
            }
        }
    }
}
