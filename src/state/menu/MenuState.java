package state.menu;

import ExceptionHandle.AllExceptions;
import core.Size;
import game.settings.GameSettings;
import input.Input;
import io.MapIO;
import map.GameMap;
import state.State;
import state.menu.ui.UIMainMenu;
import ui.UIContainer;

public class MenuState extends State {
    public MenuState(Size windowSize, Input input, GameSettings gameSettings) throws AllExceptions {
        super(windowSize, input,gameSettings);
        //gameMap = new GameMap(new Size(16, 16), spriteLibrary); //daca nu incarcam
        gameMap= MapIO.load(spriteLibrary,"map.jm");
        gameSettings.getRenderSettings().getGrid().setValue(false);

        uiContainers.add(new UIMainMenu(windowSize));
    }

    public void enterMenu(UIContainer container){
        uiContainers.clear();

        uiContainers.add(container);
    }
}
