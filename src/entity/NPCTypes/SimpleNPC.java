package entity.NPCTypes;

import controller.EntityController;
import entity.NPCTypes.NPC;
import gfx.SpriteLibrary;

public class SimpleNPC extends NPC {
    public SimpleNPC(EntityController controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary,"simpleNPC");
    }
}
