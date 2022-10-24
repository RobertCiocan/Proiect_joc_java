package game.settings;

public class GameSettings {
    private boolean debugMode;
    private double gameSpeedMultiplier;
    private final RenderSettings renderSettings;
    private final EditorSettings editorSettings;

    public GameSettings(boolean debugMode) {
        this.debugMode = debugMode;
        this.gameSpeedMultiplier=1;
        renderSettings=new RenderSettings();
        editorSettings=new EditorSettings();
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void toggleDebugMode() {
        debugMode=!debugMode;
    }

    public void increaseGameSpeed(){
        gameSpeedMultiplier+=0.2;
    }

    public void decreaseGameSpeed(){
        if(gameSpeedMultiplier - 0.2 > 0){
            gameSpeedMultiplier-=0.2;
        }
    }

    public double getGameSpeedMultiplier() {
        return gameSpeedMultiplier;
    }

    public RenderSettings getRenderSettings() {
        return renderSettings;
    }

    public EditorSettings getEditorSettings() {
        return editorSettings;
    }
}
