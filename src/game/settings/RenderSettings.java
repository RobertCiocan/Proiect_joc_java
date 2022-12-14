package game.settings;

public class RenderSettings {

    private final Setting<Boolean> grid;
    private final Setting<Boolean> collisionBox;
    public RenderSettings(){
        this.grid = new Setting<>(false);
        this.collisionBox =new Setting<>(false);
    }

    public Setting<Boolean> getGrid() {
        return grid;
    }

    public Setting<Boolean> getCollisionBox() {
        return collisionBox;
    }

}
