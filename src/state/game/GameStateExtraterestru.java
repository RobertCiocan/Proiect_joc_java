package state.game;

import ExceptionHandle.AllExceptions;
import controller.PlayerController;
import core.Size;
import entity.Players.ExtraterestrialPlayer;
import entity.Players.HumanPlayer;
import entity.Players.Player;
import entity.Players.PlayerType;
import entity.SelectionCircle;
import game.settings.GameSettings;
import input.Input;

import java.util.List;

public class GameStateExtraterestru extends GameState{
    public GameStateExtraterestru(Size windowSize, Input input, GameSettings gameSettings, String name) throws AllExceptions {
        super(windowSize, input, gameSettings, name);
    }

    protected void initializeConditions() {
        victoryConditions = List.of(()->getNumberOfSick() == 0);
        defeatConditions = List.of(()-> 1.0*getNumberOfSick()/getNumberOfNPCs() > 0.25); //bolnavi mai mic decat total/4
    }
    @Override
    public Player choosePlayer(SelectionCircle circle) {
        return ExtraterestrialPlayer.getInstance(new PlayerController(input), spriteLibrary, circle);
    }
}
