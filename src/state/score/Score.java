package state.score;

import io.Persistable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Score implements Persistable{ //sa putem salva , serializa clasa

    private LocalDateTime timeStamp; //cand am facut scorul
    private int score;

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public int getScore() {
        return score;
    }

    public String getFormattedTimeStamp(){
        return timeStamp.format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm"));//Exemplu: 22-5-22 11:33
    }

    public static Score createNew(int score){
        Score newScore = new Score();
        newScore.score = score;
        newScore.timeStamp = LocalDateTime.now();
        return newScore;
    }

    @Override
    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName());
        //nu putem da : ca delimitator -> e foloist de timp
        stringBuilder.append("}");
        stringBuilder.append(timeStamp.toString());
        stringBuilder.append("}");
        stringBuilder.append(score);

        return stringBuilder.toString();
    }

    @Override
    public void applySerializedData(String serializedData) {
        String[] parts = serializedData.split("}");
        timeStamp = LocalDateTime.parse(parts[1]);
        score = Integer.parseInt(parts[2]);
    }
}
