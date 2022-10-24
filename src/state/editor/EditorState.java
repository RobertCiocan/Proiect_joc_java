package state.editor;

import core.Size;
import game.settings.GameSettings;
import input.Input;
import input.mouse.action.TilePlacer;
import map.GameMap;
import map.Tile;
import state.State;
import state.editor.ui.UIButtonMenu;
import state.editor.ui.UIRenderSettings;
import state.editor.ui.UITileMenu;

public class EditorState extends State {
    public EditorState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);
        gameMap = new GameMap(new Size(32, 32), spriteLibrary);
        mouseHandler.setPrimaryButtonAction(new TilePlacer(new Tile(spriteLibrary, "grass",true)));

        uiContainers.add(new UIButtonMenu(windowSize));
        uiContainers.add(new UIRenderSettings(windowSize,gameSettings.getRenderSettings(),gameMap));
        uiContainers.add(new UITileMenu(windowSize,spriteLibrary,gameSettings));
    }
}
