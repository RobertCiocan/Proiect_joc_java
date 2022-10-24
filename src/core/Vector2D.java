package core;

public class Vector2D {

    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        double length = length();
        x = x == 0 ? 0 : x/length;
        y = y == 0 ? 0 : y/length;
    }

    public static  Vector2D copyOf(Vector2D vector){
        return new Vector2D(vector.getX(), vector.getY());
    }

    public static Vector2D directionBetweenPositions(Position start,Position end){
        Vector2D direction = new Vector2D(start.getX() - end.getX(), start.getY() - end.getY());
        direction.normalize();

        return direction;  //mereu cand avem o directie -> vect unitate
    }

    public static double dotProduct(Vector2D v1, Vector2D v2){//ne va ajuta sa targetam doar ce e in fata noastra , nu si in spate
        return v1.getX()*v2.getX() + v1.getY()*v2.getY();
    }

    public void multiply(double speed) {
        x *= speed;
        y *= speed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void add(Vector2D vector) {
        x += vector.getX();
        y += vector.getY();
    }
}
