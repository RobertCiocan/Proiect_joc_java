package entity;

import controller.NPCController;
import core.CollisionBox;
import core.Direction;
import core.Vector2D;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import gfx.SpriteSet;

public class UFO extends MovingEntity{

    private boolean halted;

    public UFO(NPCController npcController, SpriteLibrary spriteLibrary) {
        super(npcController, spriteLibrary);
        this.animationManager=new AnimationManager(new SpriteSet(spriteLibrary.getImage("ufo")),false);
    }

    @Override
    protected void handleTileCollision(CollisionBox collisionBox) {

    }

    @Override
    protected void handleCollision(GameObject other) {}

    @Override
    protected void handleMotion() {
        if(!halted){
            motion.add(new Vector2D(0,-0.5));//va zbura spre exterior (pe axa y)
        }

        halted=false;
        direction= Direction.S;
    }

    @Override
    protected String decideAnimation() {
        return "default";
    }

    public void halt(){
        halted=true;
    }

    public void insert(GameObject toInsert){//ce obiect vrem sa punbem in interiorul bulei
        this.position=toInsert.getPosition();
        this.renderOffset=toInsert.getRenderOffset();//sa fie in centrru
        this.collisionBoxOffset = renderOffset;
        toInsert.parent(this);
    }
}
