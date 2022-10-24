package state.score;

import ExceptionHandle.AllExceptions;
import io.Persistable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreList implements Persistable {

    private List<Score> scoreList;

    public ScoreList() {
        this.scoreList = new ArrayList<>();
    }

    public void add(Score score){
        scoreList.add(score);
    }

    public List<Score> getTop10(){
        return scoreList.stream()
                .sorted(Comparator.comparing(Score::getScore).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Score score : scoreList){
            stringBuilder.append(score.serialize());
            stringBuilder.append(COLUMN_DELIMITER);
        }

        return stringBuilder.toString();
    }



    @Override
    public void applySerializedData(String serializedData) {
        String[] parts = serializedData.split(COLUMN_DELIMITER);
        scoreList.clear();

        //incepem de la 0 ->primul elem din parts e numele clasei -> nu e un scor
        for(int i=1; i<parts.length; i++){
            Score score = new Score();
            score.applySerializedData(parts[i]);
            scoreList.add(score);
        }
    }
}
