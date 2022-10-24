package display;

import core.CollisionBox;
import core.Position;
import game.Game;
import game.settings.RenderSettings;
import state.State;
import map.GameMap;

import java.awt.*;

public class Renderer {
    public void render(State state, Graphics graphics) {
        renderMap(state, graphics);

        renderGameObjects(state,graphics);
        renderUI(state,graphics);
    }

    private void renderUI(State state, Graphics graphics) {
        //rendam inainte sa fie "sub" meniuri etc.
        state.getMouseHandler().getSprite().ifPresent(uiImage -> graphics.drawImage(
                uiImage.getSprite(),
                uiImage.getAbsolutePosition().intX(),
                uiImage.getAbsolutePosition().intY(),
                null
        ));
        state.getUiContainers().forEach(uiContainer -> graphics.drawImage(
                uiContainer.getSprite(),
                uiContainer.getRelativePosition().intX(),
                uiContainer.getRelativePosition().intY(),
                null
        ));
    }

    private void renderGameObjects(State state, Graphics graphics){
        Camera camera = state.getCamera();
        state.getGameObjects().stream()
                .filter(gameObject -> camera.isInView(gameObject))
                .forEach(gameObject -> {
                    graphics.drawImage(
                            gameObject.getSprite(),
                            gameObject.getRenderPosition(camera).intX(),
                            gameObject.getRenderPosition(camera).intY(),
                            null
                    );

                    if (state.getGameSettings().getRenderSettings().getCollisionBox().getValue()){
                        drawCollisionBox(gameObject.getCollisionBox(),graphics,camera);
                    }
                });
    }

    private void renderMap(State state, Graphics graphics) {
        GameMap map = state.getGameMap();
        Camera camera = state.getCamera();

        Position start = map.getViewableStartingGridPosition(camera);
        Position end = map.getViewableEndingGridPosition(camera);

        for(int x = start.intX(); x < end.intX(); x++) {
            for(int y = start.intY(); y < end.intY(); y++) {
                int drawPositionX = x * Game.SPRITE_SIZE - camera.getPosition().intX();
                int drawPositionY = y * Game.SPRITE_SIZE - camera.getPosition().intY();

                graphics.drawImage(map.getTiles()[x][y].getSprite(), drawPositionX, drawPositionY, null);
            }
        }

        //daca trebuie sa desenam gridul pt editor
        if(state.getGameSettings().getRenderSettings().getGrid().getValue()) {
            graphics.setColor(Color.GRAY);
            //inainte desenam un patrat pt fiecare tile , acum desenam linii pe axa X si apoi axa Y
            for(int x = start.intX(); x < end.intX(); x++) {
                graphics.drawLine(
                        x * Game.SPRITE_SIZE - camera.getPosition().intX(),
                        start.intY() * Game.SPRITE_SIZE - camera.getPosition().intY(),
                        x * Game.SPRITE_SIZE - camera.getPosition().intX(),
                        end.intY() * Game.SPRITE_SIZE - camera.getPosition().intY()
                );
            }
            for(int y = start.intY(); y < end.intY(); y++) {
                graphics.drawLine(
                        start.intX() * Game.SPRITE_SIZE - camera.getPosition().intX(),
                        y * Game.SPRITE_SIZE - camera.getPosition().intY(),
                        end.intX() * Game.SPRITE_SIZE - camera.getPosition().intX(),
                        y * Game.SPRITE_SIZE - camera.getPosition().intY()
                );
            }
        }
    }

    private void drawCollisionBox(CollisionBox collisionBox, Graphics graphics, Camera camera){
        graphics.setColor(Color.red);
        graphics.drawRect(
                (int)collisionBox.getBounds().getX() - camera.getPosition().intX(),
                (int)collisionBox.getBounds().getY() - camera.getPosition().intY(),
                (int)collisionBox.getBounds().getWidth(),
                (int)collisionBox.getBounds().getHeight()
        );
    }
}
