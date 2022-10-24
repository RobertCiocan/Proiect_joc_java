package entity.Players;

import controller.EntityController;
import entity.SelectionCircle;
import entity.humanoid.action.PlayerCough;
import entity.humanoid.action.TakeWithUFO;
import entity.humanoid.efect.Sick;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;
import state.game.GameState;

public class ExtraterestrialPlayer extends Player {
    private static Player instance;

    private ExtraterestrialPlayer(EntityController controller, SpriteLibrary spriteLibrary, SelectionCircle selectionCircle) {
        super(controller, spriteLibrary, selectionCircle);
        this.animationManager = new AnimationManager(spriteLibrary.getSpriteSet("alienMain"));
    }

    public static Player getInstance(EntityController controller, SpriteLibrary spriteLibrary, SelectionCircle selectionCircle) {
        if(instance == null){
            instance = new ExtraterestrialPlayer(controller,spriteLibrary,selectionCircle);
        }
        return instance;
    }

    public static void reset(){
        instance = null;
    }

    public void handleInput(State state) {
        if(entityController.isRequestingAction()){
            if(target != null){
                //verif daca avem o actiune (daca avem -> nu adaugam la scor pt a nu putea spama un buton si sa primim puncte)
                if(action.isEmpty()){
                    GameState gameState = (GameState) state;
                    //adaugam la scor daca extraterestrul pe care il luam cu UFO-ul este bolnav ->daca nu avem penalizare (daca luam un extraterestru care este sanataos)
                    if(target.isAffectedBy(Sick.class)){
                        gameState.applyToScore(250);
                    }else{
                        gameState.applyToScore(-250);
                    }
                }

                perform(new TakeWithUFO(target));
            }
        }
    }
}
