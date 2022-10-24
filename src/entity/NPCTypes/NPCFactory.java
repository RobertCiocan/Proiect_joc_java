package entity.NPCTypes;

import ExceptionHandle.AllExceptions;
import controller.NPCController;
import gfx.SpriteLibrary;

public class NPCFactory {
    public NPC makeNPC(NPCType type, SpriteLibrary spriteLibrary){
        return switch (type) {
            case SIMPLE -> new SimpleNPC(new NPCController(), spriteLibrary);
            case FLOATING -> new FloatingNPC(new NPCController(), spriteLibrary);
            case CAFFEINATED -> new CaffeinatedNPC(new NPCController(), spriteLibrary);
            /*case null -> {
                try {
                    throw new AllExceptions("Nu s-a gasit acest tip de NPC");
                } catch (AllExceptions e) {
                    throw new RuntimeException(e);
                }
            }*/
        };
    }
}
