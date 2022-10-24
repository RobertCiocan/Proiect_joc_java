package entity.NPCTypes;

import controller.EntityController;
import entity.NPCTypes.NPC;
import gfx.SpriteLibrary;
import state.State;

public class FloatingNPC extends NPC {
    public FloatingNPC(EntityController controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary,"floatingNPC");
    }

    @Override
    protected void handleTileCollisions(State state) {

    }
}
