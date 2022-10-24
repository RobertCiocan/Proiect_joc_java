package entity.Players;

import controller.EntityController;
import entity.SelectionCircle;
import entity.humanoid.action.PlayerCough;
import entity.humanoid.efect.Sick;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;
import state.game.GameState;

public class HumanPlayer extends Player {
    private static Player instance;

    private HumanPlayer(EntityController controller, SpriteLibrary spriteLibrary, SelectionCircle selectionCircle) {
        super(controller, spriteLibrary, selectionCircle);
        this.animationManager = new AnimationManager(spriteLibrary.getSpriteSet("gigel"));
    }

    public static Player getInstance(EntityController controller, SpriteLibrary spriteLibrary, SelectionCircle selectionCircle) {
        if(instance == null){
            instance = new HumanPlayer(controller,spriteLibrary,selectionCircle);
        }
        return instance;
    }

    public static void reset(){
        instance = null;
    }

    public void handleInput(State state) {
        if(entityController.isRequestingAction()){
            if(target != null){
                if(action.isEmpty()){
                    GameState gameState = (GameState) state;
                    if(!target.isAffectedBy(Sick.class)){
                        gameState.applyToScore(250);
                    }
                }
                perform(new PlayerCough(target));
            }
        }
    }
}
