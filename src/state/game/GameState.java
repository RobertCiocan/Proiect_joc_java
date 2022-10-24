package state.game;

import ExceptionHandle.AllExceptions;
import core.Condition;
import core.Size;
import entity.*;
import entity.NPCTypes.NPC;
import entity.NPCTypes.NPCFactory;
import entity.NPCTypes.NPCType;
import entity.Players.Player;
import entity.humanoid.efect.Isolated;
import entity.humanoid.efect.Sick;
import game.Game;
import game.Timer;
import game.settings.GameSettings;
import io.MapIO;
import state.game.ui.UIGameTime;
import state.game.ui.UIScore;
import state.game.ui.UISicknessStatistics;
import input.Input;
import state.State;
import state.game.ui.UIPauseMenu;
import ui.HorizontalContainer;
import ui.UIContainer;
import ui.UIText;
import ui.VerticalContainer;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public abstract class GameState extends State {

    protected List<Condition> victoryConditions;
    protected List<Condition> defeatConditions;
    protected boolean playing;
    private boolean paused;
    private UIPauseMenu gameMenu;
    private Timer gameTimer;

    private int score;//incepe de la 0

    public GameState(Size windowSize, Input input, GameSettings gameSettings,String name) throws AllExceptions {
        super(windowSize, input, gameSettings);
        //gameMap = new GameMap(new Size(20, 20), spriteLibrary);
        gameMap= MapIO.load(spriteLibrary,name);
        gameSettings.getRenderSettings().getGrid().setValue(false);

        gameMenu = new UIPauseMenu(windowSize,input, gameSettings);
        gameTimer = new Timer(180,this::lose);//cate sec vrem sa dureze

        playing=true;
        initializeCharacters();
        initializeUI(windowSize);
        initializeConditions();
    }

    protected abstract void initializeConditions();

    private void initializeUI(Size windowSize) {
        uiContainers.add(new UIGameTime(windowSize));
        uiContainers.add(new UISicknessStatistics(windowSize));
        uiContainers.add(new UIScore(windowSize));
    }

    private void initializeCharacters() {
        SelectionCircle circle = new SelectionCircle();
        Player player = choosePlayer(circle);
        player.setPosition(getRandomPosition());
        gameObjects.add(player);
        camera.focusOn(player);
        gameObjects.add(circle);

        initializeNPCs(50);
        makeNumberOfNPCsSick(5);
    }

    public abstract Player choosePlayer(SelectionCircle circle);

    private void makeNumberOfNPCsSick(int number) {
        getGameObjectsOfClass(NPC.class).stream()
                .limit(number)
                .forEach(npc -> npc.addEffect(new Sick()));
    }

    private void initializeNPCs(int numberOfNPCs) {
        for(int i = 0; i < numberOfNPCs; i++) {
            //NPC npc = new FloatingNPC(new NPCController(), spriteLibrary);
            NPCFactory factory = new NPCFactory();
            NPC npc = factory.makeNPC(randomType(),spriteLibrary);
            npc.setPosition(gameMap.getRandomPosition());
            gameObjects.add(npc);
        }
    }

    private NPCType randomType(){
        int index = new Random().nextInt(NPCType.values().length);
        return NPCType.values()[index];
    }

    @Override
    public void update(Game game) {
        super.update(game);

        if(!paused) {
            gameTimer.update();
        }

        handleInput();

        if(playing) {
            if(victoryConditions.stream().allMatch(Condition::isMet)) {
                win();
            }

            if(defeatConditions.stream().allMatch(Condition::isMet)) {
                lose();
            }
        }
    }

    private void handleInput(){
        if(input.isPressed(KeyEvent.VK_ESCAPE)){
            togglePause(!paused);//inversam starea de pauza
        }
    }

    @Override
    public void updateGameObjects() {
        //nu updatatam obiectele (NPC) daca punem pauza
        if(!paused) {
            super.updateGameObjects();
        }
    }

    private void lose() {//poate fi folosit ca un runnable
        playing = false;
        UIContainer content = new VerticalContainer(windowSize);
        content.addUIComponent(new UIText("ATI PIERDUT"));
        gameMenu.setHeaderContent(content);
        toggleMenu(true);
    }

    private void win() {
        playing = false;
        //adaugam la scor puncte direct proportional cu secundele ramase (cat de repede am terminat nivelul)
        applyToScore(gameTimer.asSeconds() * 100);
        UIContainer content = new VerticalContainer(windowSize);
        content.addUIComponent(new UIText("ATI CASTIGAT"));
        content.addUIComponent(new UIText(String.valueOf(score)));
        gameMenu.setHeaderContent(content);
        toggleMenu(true);
    }

    public void applyToScore(int ammount) {
        score += ammount;
    }

    public int getScore() {
        return score;
    }

    public void togglePause(boolean shouldPause) {
        if(shouldPause) {
            paused = true;
            UIContainer content = new VerticalContainer(windowSize);
            content.addUIComponent(new UIText("PAUSE"));
            gameMenu.setHeaderContent(content);
            toggleMenu(true);
        } else {
            paused = false;
            toggleMenu(false);
        }
    }

    private void toggleMenu(boolean shouldShowMenu) {
        if(shouldShowMenu && !uiContainers.contains(gameMenu)) {
            uiContainers.add(gameMenu);
        } else if (!shouldShowMenu) {
            uiContainers.remove(gameMenu);
        }
    }

    public long getNumberOfSick(){
        return getGameObjectsOfClass(NPC.class).stream()
                .filter(npc -> npc.isAffectedBy(Sick.class) && !npc.isAffectedBy(Isolated.class))
                .count();
    }

    public long getNumberOfIsolated(){
        return getGameObjectsOfClass(NPC.class).stream()
                .filter(npc -> npc.isAffectedBy(Sick.class) && npc.isAffectedBy(Isolated.class))
                .count();
    }

    public long getNumberOfHealthy(){
        return getGameObjectsOfClass(NPC.class).stream()
                .filter(npc -> !npc.isAffectedBy(Sick.class))
                .count();
    }

    public long getNumberOfNPCs(){
        return getGameObjectsOfClass(NPC.class).size();
    }

    public Timer getGameTimer() {
        return gameTimer;
    }
}
