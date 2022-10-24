package entity.humanoid.efect;

import entity.humanoid.Humanoid;
import state.State;

public abstract class Effect {

    private int lifeSpanInUpdates; //cate updateuri, nu secunde

    public Effect(int lifeSpanInUpdates) {
        this.lifeSpanInUpdates = lifeSpanInUpdates;
    }

    public void update(State state, Humanoid humanoid){
        lifeSpanInUpdates--;
    }

    public boolean shouldDelete(){
        return lifeSpanInUpdates <= 0;
    }
}
