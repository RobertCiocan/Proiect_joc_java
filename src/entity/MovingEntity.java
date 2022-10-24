package entity;

import controller.EntityController;
import core.*;
import state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.awt.*;

public abstract class MovingEntity extends GameObject {

    protected EntityController entityController;
    protected Motion motion;
    protected AnimationManager animationManager;
    protected Direction direction;

    protected Vector2D directionVector;
    protected Size collisionBoxSize;

    public MovingEntity(EntityController controller, SpriteLibrary spriteLibrary) {
        super();
        this.entityController = controller;
        this.motion = new Motion(2);
        this.direction = Direction.S;
        this.directionVector=new Vector2D(0,0);
        this.animationManager = new AnimationManager(spriteLibrary.getSpriteSet("gigel"));
        this.collisionBoxSize=new Size(size.getWidth(),size.getHeight());
    }

    public MovingEntity() {

    }

    @Override
    public void update(State state) {
        motion.update(entityController);
        handleMotion();

        animationManager.update(direction);

        handleCollisions(state);//pt entitati
        handleTileCollisions(state);//pt tileuri
        animationManager.playAnimation(decideAnimation());

        apply(motion);

    }

    private void apply(Motion motion) {
        manageDirection(motion);
        position.apply(motion);
    }

    protected void handleTileCollisions(State state) {
        state.getGameMap().getCollidingUnwalkableTileBoxes(getCollisionBox())
                .forEach(this::handleTileCollision);//fiecare obiect va implementa modiul proporiu de coliziuni cu tileurile (la fel ca si cu entitatile)
    }

    protected abstract void handleTileCollision(CollisionBox collisionBox);

    private void handleCollisions(State state) {
        state.getCollidingGameObjects(this).forEach(this::handleCollision); // gasim toate obiectele ce se intersecteaza cu obiectul actual
    }

    protected abstract void handleCollision(GameObject other);  //fiecare obiect va implementa diferit coliziunile

    protected abstract void handleMotion();


    protected abstract String decideAnimation();//ret numele animatiei pe care il vrem

    private void manageDirection(Motion motion) {
        if(motion.isMoving()) {
            this.direction = Direction.fromMotion(motion);
            this.directionVector=motion.getDirection();
        }
    }

    @Override
    public CollisionBox getCollisionBox() {
        //daca luam pozitia fara a aplica directia (motion),  hitboxurile se vor intersecta putin (verificarea se face inainte )
        Position positionWithMotion = Position.copyOf(getPosition());//facem o copie pt a putea folisi motion fara a distruge pozitia anteriaoara
        positionWithMotion.apply(motion);
        positionWithMotion.subtract(collisionBoxOffset);

        return new CollisionBox(
                new Rectangle(
                        positionWithMotion.intX() ,
                        positionWithMotion.intY(),
                        collisionBoxSize.getWidth(),
                        collisionBoxSize.getHeight()
                )
        );
    }

    @Override
    public Image getSprite() {
        return animationManager.getSprite();
    }

    public EntityController getEntityController() {
        return entityController;
    }

    public void multiplySpeed(double multiplier) {
        motion.multiply(multiplier);
    }

    public boolean willCollideX(CollisionBox otherBox) {
        Position positionWithXApplied = Position.copyOf(position);
        positionWithXApplied.applyX(motion);
        positionWithXApplied.subtract(collisionBoxOffset);

        return CollisionBox.of(positionWithXApplied, collisionBoxSize).collidesWith(otherBox);
    }

    public boolean willCollideY(CollisionBox otherBox) {
        Position positionWithYApplied = Position.copyOf(position);
        positionWithYApplied.applyY(motion);
        positionWithYApplied.subtract(collisionBoxOffset);

        return CollisionBox.of(positionWithYApplied, collisionBoxSize).collidesWith(otherBox);
    }

    public boolean isFacing(Position other){
        Vector2D direction = Vector2D.directionBetweenPositions(other,getPosition());
        double dotProduct = Vector2D.dotProduct(direction,directionVector);

        return dotProduct > 0; //daca e mai mare decat 0 -> in fata noastra
    }
}


