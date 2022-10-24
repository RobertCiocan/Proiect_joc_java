package ui;

import core.Position;
import core.Size;

public class HorizontalContainer extends UIContainer{
    public HorizontalContainer(Size windowSize) {
        super(windowSize);
    }

    @Override
    protected Size calculateContentSize() {
        //iteram prin copii si adugam pt latime , dar pt inaltime verificam maxHeight -> va deveni inaltimea
        int combinedChildWidth = 0;
        int maxHeight = 0;

        for(UIComponent uiComponent: children){
            combinedChildWidth += uiComponent.getSize().getWidth() + uiComponent.getMargin().getHorizontal();

            if(uiComponent.getSize().getHeight() > maxHeight){
                maxHeight=uiComponent.getSize().getHeight();
            }
        }

        return new Size(combinedChildWidth,maxHeight);
    }

    @Override
    protected void calculateContentPosition() {
        int currentX = padding.getLeft();

        for(UIComponent uiComponent : children){
            currentX += uiComponent.getMargin().getLeft();
            uiComponent.setRelativePosition(new Position(currentX,padding.getTop()));//mereu stanga sus
            uiComponent.setAbsolutePosition(new Position(currentX + absolutePosition.intX(),padding.getTop() + absolutePosition.intY()));
            currentX += uiComponent.getSize().getWidth();
            currentX += uiComponent.getMargin().getRight();
        }
    }
}
