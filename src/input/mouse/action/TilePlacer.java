package input.mouse.action;

import core.Position;
import game.Game;
import map.GameMap;
import map.Tile;
import state.State;
import ui.clickable.UIImage;

import javax.swing.*;

public class TilePlacer extends MouseAction {
    private Tile tile;
    private UIImage preview;
    private int gridX,gridY;

    public TilePlacer(Tile tile) {
        this.tile = tile;
        preview=new UIImage(tile.getSprite());
    }

    @Override
    public void onClick(State state) {

    }

    @Override
    public void onDrag(State state) {
        if(state.getGameMap().gridWithinBounds(gridX,gridY)){
            state.getGameMap().setTile(gridX,gridY,Tile.copyOf(tile));
        }

        //daca avem autotile on
        if(state.getGameSettings().getEditorSettings().getAutoTile().getValue()){
            autoTileOn(state);
        }
    }

    private void autoTileOn(State state) {
        GameMap gameMap = state.getGameMap();
        //iteram fiecare tile din grid
        for(int x = gridX - 1; x < gridX +2; x++){
            for(int y=gridY -1; y<gridY+2; y++){
                if(gameMap.gridWithinBounds(x,y)){
                    Tile currentTile = gameMap.getTiles()[x][y];
                    //faceum un string de biti de 0 si 1 pt tileurile vecine (daca tile vecin nu este la fel (ac nume) -> 0, daca e ac tile->1)
                    int index = switch (getNeighborTiles(gameMap,x,y)){
                        case "001011111", "000011011", "001011011", "000011111" -> 0;
                        case "000111111", "100111111", "001111111", "101111111" -> 1;
                        case "000110110", "100110110", "000110111", "100110111", "101111110" -> 2;
                        case "011011011", "011011111", "111011011", "111011111" -> 5;
                        case "110110110", "110110111", "111110110", "111110111" -> 7;
                        case "011011000", "111011000", "011011001", "111011001", "011111101" -> 10;
                        case "111111000", "111111001", "111111100", "111111101" -> 11;
                        case "110110000", "111110000", "110110100", "111110100" -> 12;
                        case "111111110" -> 15;
                        case "111111011" -> 16;
                        case "110111011" -> 17;
                        case "110111111" -> 20;
                        case "011111111" -> 21;
                        case "011111110" -> 22;
                        default -> 6;
                    };

                    Tile indexedTile = Tile.copyOf(currentTile);
                    indexedTile.setTileIndex(index);
                    //updatam harta la poz x , y cu o copie a tileului
                    state.getGameMap().setTile(x,y,indexedTile);
                }
            }
        }
    }

    private String getNeighborTiles(GameMap gameMap, int gridX, int gridY) {
        StringBuilder stringBuilder=new StringBuilder();
        Tile currentTile = gameMap.getTiles()[gridX][gridY];

        //trecem prin toate tileurile vecine
        for(int x=gridX-1; x<gridX+2; x++){
            for(int y=gridY-1; y<gridY+2; y++){
                //verif ca tileul curent e in limite
                if(!gameMap.gridWithinBounds(x,y)){
                    stringBuilder.append(1);//daca e in afara limitelor, presupunem ca ce e in afara e ac tile ca cel curent (sa parca ca harta continua si in afara )
                    continue;
                }
                //daca e ac tile
                if(gameMap.getTiles()[x][y].getTileName().equals(currentTile.getTileName())){
                    stringBuilder.append(1);
                }else{
                    stringBuilder.append(0);
                }
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void update(State state) {
        Position position = Position.copyOf(state.getInput().getMousePosition());
        position.add(state.getCamera().getPosition());

        //folosim cele 2 var pt a sti pe ce patrat din grid ne aflam
        gridX = position.intX()/ Game.SPRITE_SIZE;
        gridY = position.intY()/ Game.SPRITE_SIZE;

        position.subtract(new Position(position.intX() % Game.SPRITE_SIZE, position.intY()% Game.SPRITE_SIZE));
        position.subtract(state.getCamera().getPosition());

        preview.setAbsolutePosition(position);
    }

    @Override
    public UIImage getSprite() {
        return preview;
    }

    @Override
    public void cleanUp() {

    }
}
