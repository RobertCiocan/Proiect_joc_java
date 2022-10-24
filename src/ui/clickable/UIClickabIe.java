package ui.clickable;

import core.Position;
import input.mouse.MouseConsumer;
import state.State;
import ui.UIComponent;

import java.awt.*;

public abstract class UIClickabIe extends UIComponent implements MouseConsumer {
    protected boolean hasFocus;
    protected boolean isPressed;

    @Override
    public void update(State state){
        Position mousePosition = state.getInput().getMousePosition();

        hasFocus = getBounds().contains(mousePosition.intX(),mousePosition.intY());//stim daca avem mouse -ul deasupra elementului
        isPressed = hasFocus && state.getInput().isMousePressed();//putem da hover peste dar nu este apasat

        if(hasFocus){
            state.getMouseHandler().setActiveConsumer(this);
        }
    }

    private Rectangle getBounds(){
        return  new Rectangle(
            absolutePosition.intX(),
            absolutePosition.intY(),
            size.getWidth(),
            size.getHeight()
        );
    }
}
