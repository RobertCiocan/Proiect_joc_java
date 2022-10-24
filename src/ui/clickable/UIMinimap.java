package ui.clickable;

import core.Position;
import core.Size;
import display.Camera;
import game.Game;
import gfx.ImageUtils;
import map.GameMap;
import map.Tile;
import state.State;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIMinimap extends UIClickabIe{
    private double ratio; //ratia intre minimap si harta reala
    private int pixelPerGrid;
    private Position pixelOffset;
    private Rectangle cameraViewBounds;
    private BufferedImage mapImage; //nu generam imaginea hartii in fiecare render (pt optimizare), si generam doar cand se schimba imaginea
    private Color color;

    public UIMinimap(GameMap gameMap) {
        size = new Size(128,128);
        cameraViewBounds = new Rectangle( 0,0,0,0); //obiectul nu este null, deci nu vom primi NullPointerException
        color=Color.gray;

        calculateRatio(gameMap);
        generateMap(gameMap);
    }

    @Override
    public void update(State state){
        super.update(state);

        //generam un interval fix sa se updateze
        if(state.getTime().secondsDividableBy(0.25)){//facem asta pt optimizare(se face mapa doar de 4 ori pe secunda)
            generateMap(state.getGameMap());
        }
        Camera camera=state.getCamera();
        cameraViewBounds=new Rectangle(
                (int) (camera.getPosition().getX()*ratio + pixelOffset.intX()),
                (int) (camera.getPosition().getY()*ratio + pixelOffset.intY()),
                (int) (camera.getSize().getWidth()*ratio),
                (int) (camera.getSize().getHeight()*ratio)
        );

        color = Color.gray;
        if(hasFocus){
            color= Color.white;
        }
    }

    private void generateMap(GameMap gameMap) {
        mapImage = (BufferedImage) ImageUtils.createCompatibleImage(size,ImageUtils.ALPHA_OPAQUE);
        Graphics2D graphics = mapImage.createGraphics();

        for(int x=0; x<gameMap.getTiles().length; x++){
            for(int y=0; y<gameMap.getTiles()[0].length; y++){
                graphics.drawImage(
                        gameMap.getTiles()[x][y].getSprite().getScaledInstance(pixelPerGrid,pixelPerGrid,Image.SCALE_AREA_AVERAGING), //luam fiecare patrat din gameMap si il scalam
                        x*pixelPerGrid + pixelOffset.intX(),
                        y*pixelPerGrid +pixelOffset.intY(),
                        null
                );
            }
        }

        //graphics.dispose();
    }

    private void calculateRatio(GameMap gameMap) {
        //alegem cea mai mica val dintre lunigme si latime (harta nu este un patrat )
        ratio=Math.min(
                size.getWidth()/ (1.0*gameMap.getWidth()),
                size.getHeight()/(1.0*gameMap.getHeight())
        );
        pixelPerGrid = (int) Math.round(Game.SPRITE_SIZE*ratio);

        pixelOffset= new Position(
                (size.getWidth() - gameMap.getTiles().length*pixelPerGrid) / 2,
                (size.getHeight() - gameMap.getTiles()[0].length*pixelPerGrid) / 2
        );
    }

    @Override
    public Image getSprite() {
        BufferedImage sprite = (BufferedImage) ImageUtils.createCompatibleImage(size,ImageUtils.ALPHA_OPAQUE);
        Graphics2D graphics = sprite.createGraphics();

        graphics.drawImage(mapImage,0,0,null);

        graphics.setColor(color);
        graphics.drawRect(0,0, size.getWidth() -1,size.getHeight()-1); //pt a avea un border de 1 pixel

        //redam camera
        graphics.draw(cameraViewBounds);

        graphics.dispose();
        return sprite;
    }

    @Override
    public void onDrag(State state) {
        Position mousePosition = Position.copyOf(state.getInput().getMousePosition());
        mousePosition.subtract(absolutePosition);  //pt a gasi pozitia relativa a acestui component
        mousePosition.subtract(pixelOffset);

        state.getCamera().setPosition(
                new Position(
                        mousePosition.getX()/ratio - cameraViewBounds.getSize().getWidth() / ratio / 2, //centram pozitia unde am apasat click
                        mousePosition.getY()/ratio - cameraViewBounds.getSize().getHeight() / ratio / 2
                )
        );
    }

    @Override
    public void onClick(State state) {

    }
}
