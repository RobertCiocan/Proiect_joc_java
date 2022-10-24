package entity;

import core.CollisionBox;
import core.Position;
import core.Size;
import display.Camera;
import state.State;

import java.awt.*;

public abstract class GameObject {
    protected Position position;
    protected Size size;
    protected Position renderOffset;
    protected Position collisionBoxOffset;
    protected Size collisionBoxSize;

    protected GameObject parent;
    protected int renderOrder;

    public GameObject() {
        position = new Position(0, 0);
        size = new Size(64, 64) ;
        renderOffset=new Position(0,0);
        collisionBoxOffset=new Position(0,0);
        this.collisionBoxSize = new Size(size.getWidth(), size.getHeight());
        renderOrder=5;
    }

    public abstract void update(State state);
    public abstract Image getSprite();
    public abstract CollisionBox getCollisionBox();
    public  boolean collidesWith(GameObject other){
        return getCollisionBox().collidesWith(other.getCollisionBox());
    }

    public Position getPosition() {
        Position finalPosition = Position.copyOf(position);

        if(parent != null){
            finalPosition.add(parent.getPosition());
        }

        return finalPosition;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Size getSize() {
        return size;
    }

    public void parent(GameObject parent) {
        this.position=new Position(0,0);
        this.parent = parent;
    }

    public Position getRenderPosition(Camera camera) {
        return new Position(
                getPosition().getX() - camera.getPosition().getX() - renderOffset.getX(),
                getPosition().getY() - camera.getPosition().getY() - renderOffset.getY()
        );
    }

    public int getRenderOrder() {
        return renderOrder;
    }

    public void clearParent() {
        parent=null;
    }

    protected Position getRenderOffset() {
        return renderOffset;
    }

    public void setRenderOrder(int renderOrder) {
        this.renderOrder=renderOrder;
    }
}