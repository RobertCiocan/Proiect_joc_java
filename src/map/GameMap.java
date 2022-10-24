package map;

import core.CollisionBox;
import core.Position;
import core.Size;
import display.Camera;
import game.Game;
import gfx.SpriteLibrary;
import io.Persistable;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//serializable -> doar ca un tag
public class GameMap implements Persistable {//pt ca java sa stie ca obiectul se poate serializa si sa se salveze

    private static final int SAFETY_SPACE = 2; //cate tileuri rendam in plus din harta, pt a nu avea bare negre

    private Tile[][] tiles;

    public GameMap(){}

    public GameMap(Size size, SpriteLibrary spriteLibrary) {
        tiles = new Tile[size.getWidth()][size.getHeight()];
        initializeTiles(spriteLibrary);
    }

    private void initializeTiles(SpriteLibrary spriteLibrary) {
        for(Tile[] row: tiles) {
            Arrays.fill(row, new Tile(spriteLibrary));
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getWidth() {
        return tiles.length * Game.SPRITE_SIZE;
    }

    public int getHeight() {
        return tiles[0].length * Game.SPRITE_SIZE;
    }

    //nu vom lua orice pozitie (daca de ex initializam un npc la o pozitie random sa nu il puna pe un tile pe care nu poate sa mearga)
    public Position getRandomPosition() {
        //luam un tile random (oricare)
        double x = Math.random() * tiles.length * Game.SPRITE_SIZE;
        double y = Math.random() * tiles[0].length * Game.SPRITE_SIZE;
        int gridX = (int)x / Game.SPRITE_SIZE;
        int gridY = (int)y/Game.SPRITE_SIZE;

        //ca o fct recursiva : cauta o pozitie pana gaseste una care sa fie "buna"
        if(!(getTile(gridX,gridY).isWalkable())){
            return getRandomPosition();
        }

        return new Position(x, y);
    }

    public Position getViewableStartingGridPosition(Camera camera) {
        return new Position(
                Math.max(0 ,camera.getPosition().getX() / Game.SPRITE_SIZE - SAFETY_SPACE),  //daca scaderea da rezultat negativ (ex -> x=1, ar trebui sa cautam in array elem -1 (indexOutOfBounds))
                Math.max(0,camera.getPosition().getY() / Game.SPRITE_SIZE - SAFETY_SPACE)
        );
    }

    public Position getViewableEndingGridPosition(Camera camera) {
        return new Position(
                Math.min(tiles.length,camera.getPosition().getX() / Game.SPRITE_SIZE + camera.getSize().getWidth()+SAFETY_SPACE),
                Math.min(tiles[0].length,camera.getPosition().getY() / Game.SPRITE_SIZE + camera.getSize().getHeight() + SAFETY_SPACE)
        );
    }

    public boolean gridWithinBounds(int gridX, int gridY) {
        return gridX >= 0 && gridX < tiles.length && gridY >=0 && gridY < tiles[0].length;
    }

    public void setTile(int gridX, int gridY, Tile tile) {
        tiles[gridX][gridY]=tile;
    }

    public void reloadGraphics(SpriteLibrary spriteLibrary){
        //trecem prin fiecare tile si ii zicem sa isi dea reload la grafici
        for(Tile[] row : tiles){
            for(Tile tile : row){
                tile.reloadGraphics(spriteLibrary);
            }
        }
    }

    public List<CollisionBox> getCollidingUnwalkableTileBoxes(CollisionBox collisionBox) {
        int gridX = (int) (collisionBox.getBounds().getX() / Game.SPRITE_SIZE);
        int gridY = (int) (collisionBox.getBounds().getY() / Game.SPRITE_SIZE);

        List<CollisionBox> collidingUnwalkableTileBoxes = new ArrayList<>();

        for(int x = gridX - 1; x < gridX + 2; x++) {
            for(int y = gridY - 1; y < gridY + 2; y++) {
                if(gridWithinBounds(x, y) && !getTile(x, y).isWalkable()) {
                    CollisionBox gridCollisionBox = getGridCollisionBox(x, y);
                    if(collisionBox.collidesWith(gridCollisionBox)) {
                        collidingUnwalkableTileBoxes.add(gridCollisionBox);
                    }
                }
            }
        }

        return collidingUnwalkableTileBoxes;
    }

    private CollisionBox getGridCollisionBox(int x, int y) {
        return new CollisionBox(new Rectangle(x * Game.SPRITE_SIZE, y * Game.SPRITE_SIZE, Game.SPRITE_SIZE, Game.SPRITE_SIZE));
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    @Override
    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());//mereu stim obiectul unde ne uitam
        stringBuilder.append(DELIMITER);
        stringBuilder.append(tiles.length); //latimea
        stringBuilder.append(DELIMITER);
        stringBuilder.append(tiles[0].length); //lungimea
        stringBuilder.append(DELIMITER);

        stringBuilder.append(SECTION_DELIMITER); //citim arrayul de tileuri
        for (Tile[] tile : tiles) {
            for (int y = 0; y < tiles[0].length; y++) {
                stringBuilder.append(tile[y].serialize());
                stringBuilder.append(LIST_DELIMITER);
            }
            stringBuilder.append(COLUMN_DELIMITER);
        }

        return stringBuilder.toString();
    }

    @Override
    public void applySerializedData(String serializedData) {
        String[] tokens = serializedData.split(DELIMITER);
        tiles = new Tile[Integer.parseInt(tokens[1])][Integer.parseInt(tokens[2])]; //spunem marimea hartii (in 0 e numele ob), 1,2 latime/lungime

        String tileSection = serializedData.split(SECTION_DELIMITER)[1];//avem sectiunea cu toate tileurile
        String[] columns = tileSection.split(COLUMN_DELIMITER); //luam toate coloanele din fisier

        for(int x = 0; x < tiles.length; x++) {
            String[] Tiles = columns[x].split(LIST_DELIMITER);//gasim randul coloanei
            for(int y = 0; y < tiles[0].length; y++) {
                Tile tile = new Tile();
                tile.applySerializedData(Tiles[y]);

                tiles[x][y] = tile;
            }
        }
    }
}
