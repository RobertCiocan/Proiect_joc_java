package state.score.ui;

import core.Size;
import state.menu.MenuState;
import state.score.Score;
import state.score.ScoreList;
import ui.*;
import ui.clickable.UIButton;

public class UIHighScore extends VerticalContainer {
    public UIHighScore(Size windowSize, ScoreList scoreList) {
        super(windowSize);
        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        addUIComponent(new UIText("High score"));

        UIContainer scoreContainer = new VerticalContainer(windowSize);
        for(Score score: scoreList.getTop10()){
            scoreContainer.addUIComponent(createScoreRow(score));
        }

        addUIComponent(scoreContainer);
        addUIComponent(new UIButton("Menu", state -> state.setNextState(new MenuState(windowSize,state.getInput(),state.getGameSettings()))));
    }

    private UIComponent createScoreRow(Score score) {
        UIContainer scoreRow = new HorizontalContainer(windowSize);
        scoreRow.addUIComponent(new UIText(score.getFormattedTimeStamp()));
        scoreRow.addUIComponent(new UIText("|"+String.valueOf(score.getScore())));
        return scoreRow;
    }
}
