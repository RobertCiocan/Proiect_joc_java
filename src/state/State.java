package state;

import ExceptionHandle.AllExceptions;
import controller.PlayerController;
import core.Position;
import core.Size;
import display.Camera;
import entity.GameObject;
import entity.NPCTypes.NPCType;
import entity.Players.ExtraterestrialPlayer;
import entity.Players.HumanPlayer;
import entity.Players.Player;
import entity.Players.PlayerType;
import entity.SelectionCircle;
import game.Game;
import game.Time;
import game.settings.GameSettings;
import gfx.SpriteLibrary;
import input.Input;
import input.mouse.MouseHandler;
import map.GameMap;
import io.MapIO;
import ui.UIContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class State {

    protected GameSettings gameSettings;
    protected GameMap gameMap;
    protected List<GameObject> gameObjects;
    protected List<UIContainer> uiContainers;
    protected SpriteLibrary spriteLibrary;
    protected Input input;
    protected Camera camera;
    protected Time time;
    protected MouseHandler mouseHandler;//facem un obiect nou pentru a lasa doar un singur obiect ce foloseste mouse ul sa aiba acces la mouse(sa nu apasam pe harta si sa desenam un tile in acelasi timp)

    protected Size windowSize;

    private State nextState;

    public State(Size windowSize, Input input, GameSettings gameSettings) {
        this.windowSize=windowSize;
        this.input = input;
        this.gameSettings=gameSettings;
        gameObjects = new ArrayList<>();
        uiContainers = new ArrayList<>();
        spriteLibrary = new SpriteLibrary();
        mouseHandler=new MouseHandler();
        camera = new Camera(windowSize);
        time = new Time();
    }

    public void update(Game game) {
        time.update();
        sortObjectsByPosition();
        updateGameObjects();
        updateUIContainers();
        camera.update(this);
        mouseHandler.update(this);

        if(nextState != null){
            game.enterState(nextState);
        }
    }

    //folosim bucla for pt ca forEach da eroarea ConcurrentModficationException (forEach blocheaza lista care se itereaza, de aceea nu putem adauga sau sterge elemente)
    protected void updateGameObjects() {
        for(int i=0; i<gameObjects.size(); i++){
            gameObjects.get(i).update(this);
        }
    }

    private void updateUIContainers() {
        for(int i=0; i<uiContainers.size(); i++){
            uiContainers.get(i).update(this);
        }
    }

    private void sortObjectsByPosition() {
        gameObjects.sort(Comparator.comparing(GameObject::getRenderOrder).thenComparing(gameObject -> gameObject.getPosition().getY()));
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Camera getCamera() {
        return camera;
    }

    public Time getTime() {
        return time;
    }

    public Position getRandomPosition() {
        return gameMap.getRandomPosition();
    }

    public List<GameObject> getCollidingGameObjects(GameObject gameObject) {
        return gameObjects.stream()
                .filter(other -> other.collidesWith(gameObject))
                .collect(Collectors.toList());
    }

    public List<UIContainer> getUiContainers() {
        return uiContainers;
    }

    public Input getInput() {
        return input;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public <T extends GameObject> List<T> getGameObjectsOfClass(Class<T> clazz) {
        return gameObjects.stream()
                .filter(clazz::isInstance)
                .map(gameObject -> (T) gameObject)
                .collect(Collectors.toList());
    }

    public SpriteLibrary getSpriteLibrary() {
        return spriteLibrary;
    }

    public void spwan(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    public void loadGameMap(String name) throws AllExceptions {
        gameMap= MapIO.load(spriteLibrary,name);
    }
}
