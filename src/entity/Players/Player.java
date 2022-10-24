package entity.Players;

import controller.EntityController;
import entity.GameObject;
import entity.NPCTypes.NPC;
import entity.SelectionCircle;
import entity.humanoid.Humanoid;
import entity.humanoid.action.TakeWithUFO;
import entity.humanoid.action.PlayerCough;
import entity.humanoid.efect.Isolated;
import game.Game;
import state.State;
import gfx.SpriteLibrary;

import java.util.Comparator;
import java.util.Optional;

public abstract class Player extends Humanoid {
    protected NPC target;
    private double targetRange;
    private SelectionCircle selectionCircle;

    public Player(EntityController controller, SpriteLibrary spriteLibrary, SelectionCircle selectionCircle) {
        super(controller, spriteLibrary);
        this.selectionCircle=selectionCircle;
        this.targetRange= 1 * Game.SPRITE_SIZE; //distanta de 1 tile
    }

    @Override
    public void update(State state){
        super.update(state);
        handleTarget(state);

        handleInput(state);
    }

    public abstract void handleInput(State state);

    private void handleTarget(State state) {
        Optional<NPC> closestNPC = findClosestNPC(state);

        if(closestNPC.isPresent()){
            NPC npc = closestNPC.get();
            if(!npc.equals(target)){
                selectionCircle.parent(npc);
                target=npc;
            }
        }else{
            selectionCircle.clearParent();
            target=null;
        }
    }

    private Optional<NPC> findClosestNPC(State state) {
        return state.getGameObjectsOfClass(NPC.class).stream()
                .filter(npc -> getPosition().distanceTo(npc.getPosition()) < targetRange)
                .filter(npc -> isFacing(npc.getPosition()))
                .filter(npc -> !npc.isAffectedBy(Isolated.class))//filtram npc urile care nu sunt afectate de untargetable
                .min(Comparator.comparingDouble(npc -> position.distanceTo(npc.getPosition())));//returneaza npc-ul cu cea mai mica val distanta
    }

    @Override
    protected void handleCollision(GameObject other) {
    }
}
