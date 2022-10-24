package state.editor.ui;

import core.Size;
import game.settings.RenderSettings;
import map.GameMap;
import ui.Alignment;
import ui.VerticalContainer;
import ui.clickable.UICheckbox;
import ui.clickable.UIMinimap;

public class UIRenderSettings extends VerticalContainer {

    public UIRenderSettings(Size windowSize, RenderSettings settings, GameMap gameMap) {
        super(windowSize);
        setAlignment(new Alignment(Alignment.Position.START, Alignment.Position.START));

        addUIComponent(new UIMinimap(gameMap));
        addUIComponent(new UICheckbox("Grid",settings.getGrid()));
        addUIComponent(new UICheckbox("Col. Box",settings.getCollisionBox()));
    }
}
