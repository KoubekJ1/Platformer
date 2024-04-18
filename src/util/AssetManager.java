package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public abstract class AssetManager {
    private static HashMap<String, BufferedImage> textures = new HashMap<>();
    private static BufferedImage defaultImage;

    static {
        try {
            defaultImage = ImageIO.read(new File("assets/textures/default.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage getTexture(String path) {
        File textureFile = new File(path);

        if (textures.containsKey(textureFile.getAbsolutePath())) {
            return textures.get(textureFile.getAbsolutePath());
        } else {
            try {
                textures.put(textureFile.getAbsolutePath(), ImageIO.read(textureFile));
            } catch (IOException e) {
                return defaultImage;
            }
        }
    }
}
