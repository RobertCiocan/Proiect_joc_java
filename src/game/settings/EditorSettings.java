package game.settings;

public class EditorSettings {

    private Setting<Boolean> autoTile;

    public EditorSettings() {
        autoTile = new Setting<>(false);
    }

    public Setting<Boolean> getAutoTile() {
        return autoTile;
    }
}
