package entity.humanoid.action;

import controller.NPCController;
import entity.UFO;
import entity.humanoid.Humanoid;
import entity.humanoid.efect.Isolated;
import game.GameLoop;
import state.State;

public class TakeWithUFO extends Action{
    private int lifeSpanInUpdates;
    private Humanoid target;
    private UFO ufo;

    public TakeWithUFO(Humanoid target) {
        this.lifeSpanInUpdates= GameLoop.UPDATES_PER_SECOND*1;
        this.target = target;
        interruptable=false;
    }

    @Override
    public void update(State state, Humanoid humanoid) {
        lifeSpanInUpdates--;

        if(ufo == null){
            //daca nu avem bula , trebuie sa incepem sa suflam o bula
            takeWithUFO(state);
        }else{
            ufo.halt();//sta pe loc cat timp o "umflam"
        }

        if(isDone()){//rendam peste noi ce este in bula, si bula (zboara deasupra noapstra)
            target.setRenderOrder(6);
            ufo.setRenderOrder(6);
        }
    }

    private void takeWithUFO(State state) {
        //trebuie sa facem ca tinta sa pluteasca
        target.perform(new Levitate());
        target.addEffect(new Isolated());

        ufo =new UFO(new NPCController(),state.getSpriteLibrary());
        ufo.insert(target);
        state.spwan(ufo);
    }

    @Override
    public boolean isDone() {
        return lifeSpanInUpdates == 0;
    }

    @Override
    public String getAnimationName() {
        return "blow";
    }
}
