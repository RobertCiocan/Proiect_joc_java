package entity.NPCTypes;

import ai.AIManager;
import controller.EntityController;
import entity.GameObject;
import entity.Players.Player;
import entity.humanoid.Humanoid;
import state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

public abstract class NPC extends Humanoid {
    private AIManager aiManager;

    public NPC(EntityController controller, SpriteLibrary spriteLibrary,String name) {
        super(controller, spriteLibrary);
        animationManager = new AnimationManager(spriteLibrary.getSpriteSet(name));
        aiManager = new AIManager();
    }

    @Override
    public void update(State state) {
        super.update(state);
        aiManager.update(state, this);
    }

    @Override
    protected void handleCollision(GameObject other) {
        if(other instanceof Player) {
            motion.stop(willCollideX(other.getCollisionBox()), willCollideY(other.getCollisionBox()));
        }
    }
}
