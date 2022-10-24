package ui.clickable;

import ExceptionHandle.AllExceptions;
import core.Size;
import state.State;
import ui.Spacing;
import ui.UIContainer;
import ui.UIText;
import ui.VerticalContainer;

import java.awt.*;

public class UIButton extends UIClickabIe {
    private UIContainer container;
    private UIText label;

    private ClickAction clickAction; // se va intampla cand apasam pe buton

    public UIButton(String label, ClickAction clickAction) {
        this.label=new UIText(label);
        this.clickAction = clickAction;

        setMargin(new Spacing(5,0,0,0));

        container = new VerticalContainer(new Size(0,0));
        container.addUIComponent(this.label);
        container.setFixedSize(new Size(150,30));
    }

    @Override
    public void update(State state){
        super.update(state);
        container.update(state);

        size=container.getSize();

        Color color=Color.GRAY;

        if(hasFocus){
            color=Color.LIGHT_GRAY;
        }

        if(isPressed){
            color=Color.DARK_GRAY;
        }

        container.setBackgroundColor(color);
    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }

    @Override
    public void onClick(State state) {
        if(hasFocus){
            try {
                clickAction.execute(state);
            } catch (AllExceptions e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onDrag(State state) {

    }
}
