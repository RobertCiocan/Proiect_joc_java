package entity.humanoid.efect;

import entity.humanoid.Humanoid;
import entity.humanoid.action.Cough;
import game.GameLoop;
import state.State;

public class Sick extends Effect{

    private static final double COUGH_RATE = 1.0 / (GameLoop.UPDATES_PER_SECOND * 10); // aproximativ odata la 10 secunde

    public Sick() {
        super(Integer.MAX_VALUE);//entitatea va fi "bolnava" pt tot jocul
    }

    @Override
    public void update(State state, Humanoid humanoid){
        super.update(state, humanoid);

        if(Math.random()  < COUGH_RATE) { // math.random() -> da un nr intre 0 si 1
            humanoid.perform(new Cough());
        }
    }

}
