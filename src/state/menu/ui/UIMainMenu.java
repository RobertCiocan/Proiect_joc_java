package state.menu.ui;

import core.Size;
import game.settings.GameSettings;
import input.Input;
import state.editor.EditorState;
import state.game.GameState;
import state.menu.MenuState;
import ui.Alignment;
import ui.UIText;
import ui.VerticalContainer;
import ui.clickable.UIButton;

import java.awt.*;

public class UIMainMenu extends VerticalContainer {
    public UIMainMenu(Size windowSize) {
        super(windowSize);
        alignment=new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        setBackgroundColor(Color.darkGray);

        addUIComponent(new UIText("Aventura"));
        addUIComponent(new UIText("lui Gigel"));
        addUIComponent(new UIButton("Play",(state)-> ((MenuState)state).enterMenu(new UIChooseCharacterMenu(windowSize))));
        addUIComponent(new UIButton("EDITOR",(state) -> state.setNextState(new EditorState(windowSize, state.getInput(), state.getGameSettings()))));
        addUIComponent(new UIButton("OPTIONS",(state)-> ((MenuState)state).enterMenu(new UIOptionMenu(windowSize))));
        addUIComponent(new UIButton("EXIT",(state) -> System.exit(0)));
    }
}
