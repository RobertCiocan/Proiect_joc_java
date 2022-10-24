package core;

import controller.EntityController;

public class Motion {

    private Vector2D vector;
    private double speed;

    public Motion(double speed) {
        this.speed = speed;
        this.vector = new Vector2D(0, 0);
    }

    public void update(EntityController controller) {
        int deltaX = 0;
        int deltaY = 0;

        if(controller.isRequestingUp()) {
            deltaY--;
        }

        if(controller.isRequestingDown()) {
            deltaY++;
        }

        if(controller.isRequestingLeft()) {
            deltaX--;
        }

        if(controller.isRequestingRight()) {
            deltaX++;
        }

        vector = new Vector2D(deltaX, deltaY);
        vector.normalize();
        vector.multiply(speed);
    }

    public Vector2D getVector() {
        return vector;
    }

    public boolean isMoving() {
        return vector.length() > 0;
    }

    public void multiply(double multiplier) {
        vector.multiply(multiplier);
    }

    public void stop(boolean stopX, boolean stopY) {//parametrii bool pt a vedea daca ar trebui sa ne orpim pe fiecare axa
        vector = new Vector2D(
                stopX ? 0 : vector.getX(),  //daca ar trebui sa se opreasca pe X -> dam 0 ,daca nu -> continuam cu ce avem
                stopY ? 0 : vector.getY()
        );
    }

    public Vector2D getDirection() {
        Vector2D direction = Vector2D.copyOf(vector);
        direction.normalize();

        return direction;
    }

    public void add(Vector2D vector) {
        this.vector.add(vector);
    }
}
