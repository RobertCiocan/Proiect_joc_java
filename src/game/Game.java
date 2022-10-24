package game;

import ExceptionHandle.AllExceptions;
import controller.GameController;
import core.Size;
import display.Display;
import game.settings.GameSettings;
import state.game.GameState;
import state.State;
import input.Input;
import state.menu.MenuState;

public class Game {

    public static int SPRITE_SIZE = 64;

    private Display display;
    private Input input;
    private State state;
    private GameSettings settings;
    private GameController gameController;

    public Game(int width, int height) throws AllExceptions {
        input = new Input();
        display = new Display(width, height, input);
        settings=new GameSettings(false); // debug = false
        state = new MenuState(new Size(width, height), input, settings);
        gameController=new GameController(input);
    }

    public void update() {
        state.update(this);
        gameController.update(this);
    }

    public void render() {
        display.render(state,settings.isDebugMode());
    }

    public GameSettings getSettings() {
        return settings;
    }

    public void enterState(State state) {
        this.state = state;
    }
}
