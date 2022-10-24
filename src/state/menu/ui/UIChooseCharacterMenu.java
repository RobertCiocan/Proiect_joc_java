package state.menu.ui;

import core.Size;
import entity.Players.PlayerType;
import game.Game;
import gfx.SpriteLibrary;
import map.Tile;
import state.State;
import state.game.GameState;
import state.menu.MenuState;
import ui.*;
import ui.clickable.UIButton;
import ui.clickable.UIImage;
import ui.clickable.UIToolToggle;

import java.awt.*;

public class UIChooseCharacterMenu extends VerticalContainer {
    public UIChooseCharacterMenu(Size windowSize) {
        super(windowSize);
        setBackgroundColor(Color.darkGray);
        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        setPadding(new Spacing(5));

        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        addUIComponent(new UIText("Alegeti-va"));
        addUIComponent(new UIText("caracterul"));

        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
       addUIComponent(new UIButton("Om", state -> {
            ((MenuState)state).enterMenu(new UIMapOptionMenu(windowSize,PlayerType.OM));
        }));
       addUIComponent(new UIButton("Darjakr'Ul",state -> {
            ((MenuState)state).enterMenu(new UIMapOptionMenu(windowSize,PlayerType.EXTRATERESTRU));
        }));

       addUIComponent(new UIButton("BACK",(state)-> ((MenuState)state).enterMenu(new UIMainMenu(windowSize))));
    }

}
