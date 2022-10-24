package game;

public class Time {

    protected int currentUpdate;

    public Time() {
        this.currentUpdate = 0;
    }

    public int getUpdatesFromSeconds(double seconds) {
        return (int)Math.round(seconds * GameLoop.UPDATES_PER_SECOND);
    }

    public void update(){
        currentUpdate++;
    }

    public String getFormattedTime(){
        StringBuilder stringBuilder = new StringBuilder();
        int minutes = currentUpdate /GameLoop.UPDATES_PER_SECOND/60;
        int seconds= currentUpdate /GameLoop.UPDATES_PER_SECOND % 60;

        if(minutes<10){
            stringBuilder.append(0);
        }
        stringBuilder.append(minutes);
        stringBuilder.append(":");

        if(seconds < 10){
            stringBuilder.append(0);
        }
        stringBuilder.append(seconds);

        return stringBuilder.toString();
    }

    public boolean secondsDividableBy(double seconds) {
        if(currentUpdate % getUpdatesFromSeconds(seconds) == 0){
            return true;
        }
        return false;
    }

    public int asSeconds() {
        return currentUpdate / GameLoop.UPDATES_PER_SECOND;
    }
}
