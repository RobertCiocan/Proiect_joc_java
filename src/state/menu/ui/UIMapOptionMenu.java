package state.menu.ui;

import core.Size;
import entity.Players.PlayerType;
import state.game.GameState;
import state.game.GameStateExtraterestru;
import state.game.GameStateHuman;
import state.menu.MenuState;
import ui.Alignment;
import ui.UIText;
import ui.VerticalContainer;
import ui.clickable.UIButton;

import java.awt.*;

public class UIMapOptionMenu extends VerticalContainer {
    public UIMapOptionMenu(Size windowSize, PlayerType type) {
        super(windowSize);
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        setBackgroundColor(Color.darkGray);


        addUIComponent(new UIText("Alegeti"));
        addUIComponent(new UIText("nivelul"));
        addUIComponent(new UIButton("Level 1",state -> {
            state.loadGameMap("map.jm");
            if(type == PlayerType.OM){
                state.setNextState(new GameStateHuman(windowSize, state.getInput(), state.getGameSettings(),"map.jm"));
            }else{
                state.setNextState(new GameStateExtraterestru(windowSize, state.getInput(), state.getGameSettings(),"map.jm"));
            }
        }));
        addUIComponent(new UIButton("Level 2",state -> {
            state.loadGameMap("map2.jm");
            if(type == PlayerType.OM){
                state.setNextState(new GameStateHuman(windowSize, state.getInput(), state.getGameSettings(),"map2.jm"));
            }else{
                state.setNextState(new GameStateExtraterestru(windowSize, state.getInput(), state.getGameSettings(),"map2.jm"));
            }
        }));

        addUIComponent(new UIButton("BACK",(state)-> ((MenuState)state).enterMenu(new UIChooseCharacterMenu(windowSize))));
    }
}
