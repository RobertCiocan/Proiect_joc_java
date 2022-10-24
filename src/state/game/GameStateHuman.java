package state.game;

import ExceptionHandle.AllExceptions;
import controller.PlayerController;
import core.Size;
import entity.Players.HumanPlayer;
import entity.Players.Player;
import entity.SelectionCircle;
import game.settings.GameSettings;
import input.Input;
import java.util.List;

public class GameStateHuman extends GameState{
    public GameStateHuman(Size windowSize, Input input, GameSettings gameSettings, String name) throws AllExceptions {
        super(windowSize, input, gameSettings, name);
    }

    protected void initializeConditions() {
        victoryConditions = java.util.List.of(()->getNumberOfSick() == getNumberOfNPCs());
        defeatConditions = List.of(()-> 1.0*getNumberOfSick()/getNumberOfNPCs() > 1); //niciodata -> doar cand se termina timpul
    }

    @Override
    public Player choosePlayer(SelectionCircle circle) {
        return HumanPlayer.getInstance(new PlayerController(input), spriteLibrary, circle);
    }

}
