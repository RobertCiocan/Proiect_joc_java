package map;

import game.Game;
import gfx.SpriteLibrary;
import io.Persistable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Tile implements Persistable {

    private transient Image image;//intreaga imagine (tot setul)//specificam ca nu vom serializa obiectul cu "transient"
    private transient Image sprite;//doar un singur sprite
    // nu vrem sa salvam toata data pt o imagine
    //putem reincarca doar stiind numele tileului pe care ne aflam ->facem tileName
    private String tileName;//va fi serializat si salvat
    //cand reincarcam imginea -> stim indexul si numele -> putem reface harta
    private int tileIndex;//indexul fiecarui sprite din set
    private boolean walkable;

    public Tile(){
        walkable = true;
    }

    public Tile(SpriteLibrary spriteLibrary) {
        this(spriteLibrary,"grass",true);//default
    }
    public Tile(SpriteLibrary spriteLibrary, String name,boolean walkable) {
        this.image = spriteLibrary.getImage(name);
        this.tileName=name;
        this.walkable=walkable;
        generateSprite();
    }

    //constructor de copiere
    private Tile(Image image, int tileIndex, String tileName,boolean walkable){
        this.image=image;
        this.tileIndex=tileIndex;
        this.tileName=tileName;
        this.walkable=walkable;
        generateSprite();
    }

    private void generateSprite() {
        sprite = ((BufferedImage)image).getSubimage(
                (tileIndex / (image.getWidth(null) / Game.SPRITE_SIZE)) * Game.SPRITE_SIZE,
                (tileIndex % (image.getWidth(null) / Game.SPRITE_SIZE)) * Game.SPRITE_SIZE,
                Game.SPRITE_SIZE,
                Game.SPRITE_SIZE
        );
    }

    public static Tile copyOf(Tile tile) {
        return new Tile(tile.getImage(), tile.getTileIndex(), tile.tileName, tile.isWalkable());
    }

    public Image getImage() {
        return image;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
        generateSprite();
    }

    public Image getSprite() {
        return sprite;
    }

    public String getTileName() {
        return tileName;
    }

    public void reloadGraphics(SpriteLibrary spriteLibrary){
        image = spriteLibrary.getImage(tileName);
        generateSprite();
    }

    @Override
    public String serialize() {
        //cum serializam acest obiect: facem un string de unde ne vom lua numele tileului si indexul sau
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append(DELIMITER);
        stringBuilder.append(tileName);
        stringBuilder.append(DELIMITER);
        stringBuilder.append(tileIndex);
        stringBuilder.append(DELIMITER);
        stringBuilder.append(walkable);
        return stringBuilder.toString();
    }

    @Override
    public void applySerializedData(String serializedData) {
        //aplicam datele citite
        String[] tokens =serializedData.split(DELIMITER);//stim ca toate au ":" intre

        tileName = tokens[1];
        tileIndex = Integer.parseInt(tokens[2]);
        if(tokens.length > 3){
            walkable = Boolean.parseBoolean(tokens[3]);
        }
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable=walkable;
    }
}
