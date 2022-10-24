package state.editor.ui;

import ExceptionHandle.AllExceptions;
import core.Size;
import io.MapIO;
import state.menu.MenuState;
import ui.Alignment;
import ui.HorizontalContainer;
import ui.UIContainer;
import ui.VerticalContainer;
import ui.clickable.UIButton;

public class UIButtonMenu extends HorizontalContainer {
    public UIButtonMenu(Size windowSize) {
        super(windowSize);
        alignment=new Alignment(Alignment.Position.END, Alignment.Position.START);

        UIContainer container = new VerticalContainer(windowSize);
        container.addUIComponent(new UIButton("BACK",state -> state.setNextState(new MenuState(state.getCamera().getSize(), state.getInput(), state.getGameSettings()))));
        container.addUIComponent(new UIButton("SAVE 1",state -> {
            try {
                MapIO.save(state.getGameMap(),"map.jm");
            } catch (AllExceptions e) {
                throw new RuntimeException(e);
            }
        }));
        container.addUIComponent(new UIButton("SAVE 2",state -> {
            try {
                MapIO.save(state.getGameMap(),"map2.jm");
            } catch (AllExceptions e) {
                throw new RuntimeException(e);
            }
        }));
        container.addUIComponent(new UIButton("LOAD 1",state -> {
            try {
                state.loadGameMap("map.jm");
            } catch (AllExceptions e) {
                throw new RuntimeException(e);
            }
        }));
        container.addUIComponent(new UIButton("LOAD 2",state -> {
            try {
                state.loadGameMap("map2.jm");
            } catch (AllExceptions e) {
                throw new RuntimeException(e);
            }
        }));

        addUIComponent(container);
    }
}
