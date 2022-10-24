package state.game.ui;

import core.Size;
import game.settings.GameSettings;
import input.Input;
import state.game.GameState;
import state.menu.MenuState;
import state.score.Score;
import state.score.ScoreState;
import ui.Alignment;
import ui.Spacing;
import ui.UIContainer;
import ui.VerticalContainer;
import ui.clickable.UIButton;

import java.awt.*;

public class UIPauseMenu extends VerticalContainer {

    private UIContainer headerContent;

    public UIPauseMenu(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize);
        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));

        headerContent = new VerticalContainer(windowSize);

        UIContainer buttonContainer = new VerticalContainer(windowSize);
        buttonContainer.setBackgroundColor(Color.DARK_GRAY);
        buttonContainer.setPadding(new Spacing(0,5,5,5));
        buttonContainer.addUIComponent(new UIButton("Finished", (state) -> state.setNextState(new ScoreState(windowSize, input,
                gameSettings, Score.createNew(((GameState)state).getScore())))));
        buttonContainer.addUIComponent(new UIButton("Resume", (state) -> ((GameState) state).togglePause(false)));
        buttonContainer.addUIComponent(new UIButton("Exit", (state) -> System.exit(0)));

        addUIComponent(headerContent);
        addUIComponent(buttonContainer);
    }

    public void setHeaderContent(UIContainer content) {
        headerContent.clear();
        headerContent.addUIComponent(content);
    }
}
