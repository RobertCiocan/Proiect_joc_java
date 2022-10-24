package state.game.ui;

import core.Size;
import game.Time;
import state.State;
import state.game.GameState;
import ui.Alignment;
import ui.HorizontalContainer;
import ui.UIText;

public class UIGameTime extends HorizontalContainer {

    private UIText gameTime;

    public UIGameTime(Size windowSize) {
        super(windowSize);
        this.alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.START);
        this.gameTime = new UIText("");
        addUIComponent(gameTime);
    }

    @Override
    public void update(State state) {
        super.update(state);
        Time gameTime = ((GameState) state).getGameTimer();
        this.gameTime.setText(gameTime.getFormattedTime());
    }
}
