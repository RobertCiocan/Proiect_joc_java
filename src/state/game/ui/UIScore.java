package state.game.ui;

import core.Size;
import state.State;
import state.game.GameState;
import ui.Alignment;
import ui.UIText;
import ui.VerticalContainer;

public class UIScore extends VerticalContainer {
    private UIText score;

    public UIScore(Size windowSize) {
        super(windowSize);
        setAlignment(new Alignment(Alignment.Position.END, Alignment.Position.START));
        score = new UIText("0");

        addUIComponent(new UIText("Score"));
        addUIComponent(score);
    }

    @Override
    public  void update(State state){
        super.update(state);
        GameState gameState = (GameState)state;
        //luam scorul din GameState ul actual si il punem la score
        score.setText(String.valueOf(gameState.getScore()));
    }
}
