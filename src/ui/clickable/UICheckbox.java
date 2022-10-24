package ui.clickable;

import core.Size;
import game.settings.Setting;
import gfx.ImageUtils;
import state.State;
import ui.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UICheckbox extends UIComponent {

    private UIContainer container;

    public UICheckbox(String label, Setting<Boolean> setting) {
        this.container=new HorizontalContainer(new Size(0,0));
        container.addUIComponent(new Checkbox(setting));
        container.addUIComponent(new UIText(label));
    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }

    @Override
    public void update(State state) {
        container.update(state);
        size=container.getSize();
        container.setParent(parent);
        container.setAbsolutePosition(absolutePosition);
    }

    //UICheckbox va contine un label(un text) si un checkbox
    //dar vrem doar ca patratul sa fie clickable , nu si textul in sine
    private static class Checkbox extends UIClickabIe{

        private Setting<Boolean> setting;
        private Color color;

        private Checkbox(Setting<Boolean> setting) {
            this.setting = setting;
            size=new Size(20,20);
            color=Color.gray;
            margin=new Spacing(0,7,0,0);
        }

        @Override
        public void update(State state){
            super.update(state);

            if(setting.getValue()){
                //daca checkboxul este bifat (setting true) -> casuta e alba
                color=Color.white;
            }else{
                color=Color.gray;
            }

            //daca dam hover deasupra lui
            if(hasFocus){
                color=Color.lightGray;

                if(isPressed){
                    color=Color.darkGray;
                }
            }
        }

        @Override
        public Image getSprite() {
            BufferedImage sprite = (BufferedImage) ImageUtils.createCompatibleImage(size,ImageUtils.ALPHA_BIT_MASKED);
            Graphics2D graphics=sprite.createGraphics();

            graphics.setColor(color);
            if(setting.getValue()){
                //daca e true -> umplem casuta cu alb
                graphics.fillRect(0,0, size.getWidth(),size.getHeight());
            }else{
                //daca e fals facem doar conturul
                graphics.drawRect(0,0, size.getWidth()-1, size.getHeight()-1);//scadem unu pt ca drawRect deseneaza un dreptunghi lat de 1 pixel , si sa nu iesim din afara spriteului
            }
            graphics.dispose();
            return sprite;
        }

        @Override
        public void onClick(State state) {
            if(hasFocus){
                setting.setValue(!setting.getValue());//dam on/off la setting (e boolean deci true/false)
            }
        }

        @Override
        public void onDrag(State state) {

        }
    }
}
