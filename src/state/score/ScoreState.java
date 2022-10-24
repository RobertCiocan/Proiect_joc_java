package state.score;

import ExceptionHandle.AllExceptions;
import core.Size;
import game.settings.GameSettings;
import input.Input;
import database.LoadAndSaveScores;
import state.State;
import state.score.ui.UIHighScore;

import java.io.File;

//va incarca si salva scorurile
public class ScoreState extends State {

    private ScoreList scoreList;

    public ScoreState(Size windowSize, Input input, GameSettings gameSettings, Score score) {
        super(windowSize, input, gameSettings);
        try {
            loadGameMap("map.jm");
        } catch (AllExceptions e) {
            throw new RuntimeException(e);
        }
        loadScores();
        scoreList.add(score);
        try {
            saveScores();
        } catch (AllExceptions e) {
            throw new RuntimeException(e);
        }

        uiContainers.add(new UIHighScore(windowSize,scoreList));
    }

    private void loadScores(){
        if(new File("E:\\Cursuri\\An2\\Semestru2\\PAOO\\ProiectPAOO\\scoruri.db").exists()){
            try {
                scoreList = LoadAndSaveScores.loaad();
            } catch (AllExceptions e) {
                throw new RuntimeException(e);
            }
        }else{
            scoreList = new ScoreList();//daca nu exsita facem una noua
        }
    }

    private void saveScores() throws AllExceptions {
        try{
            LoadAndSaveScores.save(scoreList);
        } catch (AllExceptions e) {
            throw new AllExceptions("Nu am putut crea un fisier nou",e);
        }
    }
}
