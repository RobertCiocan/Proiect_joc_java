package ui;

import core.Position;
import core.Size;
import state.State;
import gfx.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class UIContainer extends UIComponent{
    protected Color backgroundColor;

    protected Size fixedSize;

    protected Alignment alignment;
    protected Size windowSize;

    protected List<UIComponent> children;
    protected Image sprite;

    public UIContainer(Size windowSize) {
        super();
        this.windowSize=windowSize;
        alignment=new Alignment(Alignment.Position.START,Alignment.Position.START );
        backgroundColor=new Color(0,0,0,0);
        margin = new Spacing(5);
        padding=new Spacing(5);
        children=new ArrayList<>();
        calculateSize();
        calculatePosition();
    }

    protected abstract Size calculateContentSize();
    protected abstract void calculateContentPosition();

    private void calculateSize(){
        Size calculatedContentSize = calculateContentSize();
        //daca avem fixSize -> size va fi mereu fixata , daca e nul calculam marimea
        size=  fixedSize != null
                ? fixedSize
                : new Size(      //marimea finala = padding + contents (fii)
                padding.getHorizontal() + calculatedContentSize.getWidth(),
                padding.getVertical() + calculatedContentSize.getHeight()
        );
    }

    private void calculatePosition(){ //pozitia fiilor nu afecteaza pozitia elementului UI pe ecran
        int x = padding.getLeft();
        if(alignment.getHorizontal().equals(Alignment.Position.CENTER)){
            x = windowSize.getWidth()/2 - size.getWidth()/2;
        }

        if(alignment.getHorizontal().equals(Alignment.Position.END)){
            x=windowSize.getWidth() - size.getWidth() -margin.getRight();
        }

        int y = padding.getTop();
        if(alignment.getVertical().equals(Alignment.Position.CENTER)){
            y = windowSize.getHeight()/2 - size.getHeight()/2;
        }

        if(alignment.getVertical().equals(Alignment.Position.END)){
            y=windowSize.getHeight() - size.getHeight() -margin.getBottom();
        }

        this.relativePosition = new Position(x,y);
        //calculam pozitia contianerului in care ne aflam doar daca NU are un parinte (daca are parinte , parintele calculeaza pozitia absoulta)
        if(parent == null ){
            this.absolutePosition = new Position(x,y);
        }else{

        }
        calculateContentPosition();
    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    @Override
    public void update(State state) {
        for(UIComponent component:children){
            component.update(state);
        }
        calculateSize();
        calculatePosition();

        //redesenam harta doar de 10 ori pe sec
        if(state.getTime().secondsDividableBy(0.1)){
            generateSprite();
        }
    }

    private void generateSprite() {
        sprite =  ImageUtils.createCompatibleImage(size,ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = (Graphics2D) sprite.getGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRect(0,0,size.getWidth(),size.getHeight());

        //cautam in fii
        for(UIComponent uiComponent: children){
            graphics.drawImage(
                    uiComponent.getSprite(),
                    uiComponent.getRelativePosition().intX(),
                    uiComponent.getRelativePosition().intY(),
                    null
            );
        }

        graphics.dispose();
    }

    public void clear() {
        children.clear();
    }

    public void addUIComponent(UIComponent uiComponent){
        children.add(uiComponent);
        uiComponent.setParent(this);
    }

    public void setBackgroundColor(Color color) {
        backgroundColor=color;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public void setFixedSize(Size fixedSize) {
        this.fixedSize = fixedSize;
    }
}
