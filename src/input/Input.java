package input;

import core.Position;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener {
    private Position mousePosition;
    private boolean mouseClicked;
    private boolean mousePressed;

    private boolean[] currentlyPressed;
    private boolean[] pressed;

    public Input() {
        pressed=new boolean[255];
        currentlyPressed = new boolean[255];
        mousePosition = new Position(0,0);
    }

    public boolean isPressed(int keyCode){
        if(!pressed[keyCode] && currentlyPressed[keyCode]){
            pressed[keyCode]=true;
            return true;
        }
        return false;
    }

    public boolean isCurrentlyPressed(int keyCode) {
        return currentlyPressed[keyCode];
    }

    public Position getMousePosition() {
        return mousePosition;
    }

    public boolean isMouseClicked() {
        return mouseClicked;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        currentlyPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentlyPressed[e.getKeyCode()] = false;
        pressed[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed=true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked=true;
        mousePressed=false;
    }

    public void clearMouseClick(){ //in loopruile update -> sa nu inregistram de mai multe ori un click
        //clickurile nu sunt inregistrate in updateurile noastre , ci intr-un "thread" separat . de accea trebuie sa ne asiguram ca nu inregistram de mai multe ori acelasi click
        mouseClicked=false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = new Position(e.getPoint().getX(), e.getPoint().getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = new Position(e.getPoint().getX(), e.getPoint().getY());
    }
}
