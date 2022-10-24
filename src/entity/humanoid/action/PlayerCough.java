package entity.humanoid.action;

import core.CollisionBox;
import core.Position;
import core.Size;
import entity.NPCTypes.NPC;
import entity.humanoid.Humanoid;
import entity.humanoid.efect.Sick;
import game.Game;
import game.GameLoop;
import state.State;

public class PlayerCough extends Action{
    private int lifeSpanInSeconds;
    private Humanoid target;

    public PlayerCough(Humanoid target) {
        lifeSpanInSeconds= GameLoop.UPDATES_PER_SECOND * 1;
        this.target = target;
        interruptable= false;
    }

    @Override
    public void update(State state, Humanoid performer) {
        lifeSpanInSeconds -- ;

        if(!target.isAffectedBy(Sick.class)){
            target.addEffect(new Sick());
        }
    }

    @Override
    public boolean isDone() {
        return lifeSpanInSeconds == 0;
    }

    @Override
    public String getAnimationName() {
        return "cough";
    }
}
