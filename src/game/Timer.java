package game;

public class Timer extends Time{

    //vrem sa avem un timp care descreste -> cand ajunge la 0 am pierdut
    private Runnable callBack;

    public Timer(double seconds, Runnable callBack){//runnable -> ce instanta de joc se va intampla cand timpul ajunge la 0
        currentUpdate = getUpdatesFromSeconds(seconds);
        this.callBack=callBack;
    }

    @Override
    public void update(){
        //daca nu ajungem la 0 -> scadem din sec
        if(currentUpdate > 0){
            currentUpdate--;
            //cand ajungem la 0 -> intram in noua stare
            if(currentUpdate == 0){
                callBack.run();
            }
        }
    }

}
