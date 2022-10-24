package gfx;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {

    private Map<String, SpriteSet> spriteSets;
    private Map<String, Image> images;

    public SpriteLibrary() {
        spriteSets = new HashMap<>();
        images = new HashMap<>();
        loadSpritesFromDisk();
    }

    private void loadSpritesFromDisk() {
        loadSpriteSet("/sprites/units");
        loadImages("/sprites/tiles");
        loadImages("/sprites/effects");
    }

    private void loadImages(String path) {
        String[] imagesInFolder = getImagesInFolder(path);

        for(String filename: imagesInFolder) {
            images.put(
                    filename.substring(0, filename.length() - 4),
                    ImageUtils.loadImage(path + "/" + filename));
        }
    }

    private void loadSpriteSet(String path) {
        String[] folderNames = getFolderNames(path);

        for(String folderName: folderNames) {
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = path + "/" + folderName;
            String[] sheetsInFolder = getImagesInFolder(pathToFolder);

            for(String sheetName: sheetsInFolder) {
                spriteSet.addSheet(
                        sheetName.substring(0, sheetName.length() - 4),
                        ImageUtils.loadImage(pathToFolder + "/" + sheetName));
            }

            spriteSets.put(folderName, spriteSet);
        }
    }

    private String[] getImagesInFolder(String basePath) {
        URL resource = SpriteLibrary.class.getResource(basePath);
        File file;
        try
        {
            file = new File(resource.toURI());
        } catch (java.net.URISyntaxException e) {
            return new String[0];
        }
        return file.list((current, name) -> new File(current, name).isFile());
    }

    private String[] getFolderNames(String basePath) {
        URL resource = SpriteLibrary.class.getResource(basePath);
        File file;
        try
        {
            file = new File(resource.toURI());
        } catch (java.net.URISyntaxException e) {
            return new String[0];
        }
        return file.list((current, name) -> new File(current, name).isDirectory());
    }

    public SpriteSet getSpriteSet(String name) {
        return spriteSets.get(name);
    }

    public Image getImage(String name) {
        return images.get(name);
    }
}
