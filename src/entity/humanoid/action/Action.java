package entity.humanoid.action;

import entity.humanoid.Humanoid;
import state.State;

public abstract class Action {

    protected boolean interruptable;;

    public Action() {
        interruptable = true;
    }

    public abstract void update(State state, Humanoid humanoid);
    public abstract boolean isDone();
    public abstract String getAnimationName();

    public boolean isInterruptable() {
        return interruptable;
    }
}
