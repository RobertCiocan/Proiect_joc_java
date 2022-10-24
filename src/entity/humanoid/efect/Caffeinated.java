package entity.humanoid.efect;

import entity.humanoid.Humanoid;
import game.GameLoop;
import state.State;

public class Caffeinated extends Effect{    //creste viteza
    private double speedMulitplier;

    public Caffeinated() {
        super(GameLoop.UPDATES_PER_SECOND * 500); //presupunem ca dureaza 5 sec ( 5* updates/sec = 5 sec)
        speedMulitplier = 2.5;      //pt -1 -> inverseaza toate inputurile
    }

    @Override
    public void update(State state, Humanoid humanoid){
        super.update(state, humanoid);

        humanoid.multiplySpeed(speedMulitplier);
    }
}
