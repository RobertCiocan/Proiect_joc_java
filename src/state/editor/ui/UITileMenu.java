package state.editor.ui;

import core.Size;
import game.Game;
import game.settings.GameSettings;
import gfx.SpriteLibrary;
import map.Tile;
import ui.*;
import ui.clickable.UICheckbox;
import ui.clickable.UIToolToggle;

import java.awt.*;

public class UITileMenu extends VerticalContainer {
    public UITileMenu(Size windowSize, SpriteLibrary spriteLibrary, GameSettings gameSettings) {
        super(windowSize);
        setBackgroundColor(Color.darkGray);
        setAlignment(new Alignment(Alignment.Position.START, Alignment.Position.END));

        UIContainer tileContainer = new HorizontalContainer(windowSize);
        tileContainer.addUIComponent(getTileSet(spriteLibrary,"grass",true));
        tileContainer.addUIComponent(getTileSet(spriteLibrary,"concrete",true));
        tileContainer.addUIComponent(getTileSet(spriteLibrary,"dirt",true));
        tileContainer.addUIComponent(getTileSet(spriteLibrary,"water",false));

        addUIComponent(new UICheckbox("Auto-Tile",gameSettings.getEditorSettings().getAutoTile()));
        addUIComponent(tileContainer);
    }

    private UIComponent getTileSet(SpriteLibrary spriteLibrary, String tileSet, boolean walkable) {
        //tile setul este o imagine de 5 pe 4 , vom crea un UITileToggle pt fiecare imagine din set, dar le punem pe toate intr-un container
        UIContainer main = new HorizontalContainer(new Size(0,0));
        main.setMargin(new Spacing(0));
        main.setPadding(new Spacing(0));
        Tile tile = new Tile(spriteLibrary,tileSet,walkable);

        int tilesX = tile.getImage().getWidth(null)/ Game.SPRITE_SIZE;
        int tilesY = tile.getImage().getHeight(null)/ Game.SPRITE_SIZE;

        for(int x=0; x<tilesX; x++){
            //facem un container sa punem toate Y
            UIContainer column = new VerticalContainer(new Size(0,0));
            column.setMargin(new Spacing(0));
            column.setPadding(new Spacing(0));
            for(int y=0; y<tilesY; y++){
                Tile indexedTile = Tile.copyOf(tile);
                indexedTile.setTileIndex(x * tilesX + y);//facem un vect dintr-o matrice
                UIToolToggle toggle=new UIToolToggle(indexedTile);
                //adaugam cate un elem la coloana
                column.addUIComponent(toggle);
            }
            //adaugam cate o coloana la main container
            main.addUIComponent(column);
        }
        return main;
    }


}
