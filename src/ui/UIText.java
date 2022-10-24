package ui;

import core.Size;
import state.State;
import gfx.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIText extends UIComponent{

    private String text;
    private int fontSize;
    private int fontStyle;
    private String fontFamily;
    private Color color = Color.white;

    private boolean dropShadow;
    private int dropShadowOffset;
    private Color shadowColor;

    private Font font;

    public UIText(String text) {
        this.text = text;
        this.fontSize=16;
        this.fontStyle=Font.BOLD;
        this.fontFamily="Joystix Monospace";

        this.dropShadow = true;
        this.dropShadowOffset=2;
        this.shadowColor=new Color(140,140,140);
    }

    @Override
    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(size,ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(font);

        if(dropShadow){
            graphics.setColor(shadowColor);
            graphics.drawString(text, padding.getLeft() + dropShadowOffset, padding.getTop()+fontSize + dropShadowOffset);//textul e desenat din colt stanga
        }

        graphics.setColor(color);
        graphics.drawString(text, padding.getLeft(), padding.getTop()+fontSize);

        graphics.dispose();
        return image;
    }

    @Override
    public void update(State state) {
        createFont();
        calculateSize();
    }

    private void calculateSize() {
        FontMetrics fontMetrics = new Canvas().getFontMetrics(font); //cat de lungi vor fi Stringurile in fct de font (bold etc), vor fi mai late etc.
        int width = fontMetrics.stringWidth(text)+padding.getHorizontal();
        int height = fontMetrics.getHeight() + padding.getVertical();

        if(dropShadow){
            width+=dropShadowOffset;
        }

        size=new Size(width, height);
    }

    private void createFont() {
        font = new Font(fontFamily, fontStyle, fontSize);
    }

    public void setText(String text) {
        this.text = text;
    }
}
