package entity.NPCTypes;

import controller.EntityController;
import entity.humanoid.efect.Caffeinated;
import gfx.SpriteLibrary;

public class CaffeinatedNPC extends NPC{
    public CaffeinatedNPC(EntityController controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary,"fastNPC");
        this.addEffect(new Caffeinated());
    }
}
