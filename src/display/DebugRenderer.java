package display;

import core.CollisionBox;
import entity.humanoid.Humanoid;
import state.State;
import ui.UIText;

import java.awt.*;
import java.util.stream.Collectors;

public class DebugRenderer {

    public void render(State state, Graphics graphics) {
        drawEffects(state,graphics);
    }

    private void drawEffects(State state, Graphics graphics){
        Camera camera = state.getCamera();
        state.getGameObjectsOfClass(Humanoid.class).stream()
                .forEach(humanoid -> {
                    UIText effectsText = new UIText(
                            humanoid.getEffects().stream().map(effect -> effect.getClass().getSimpleName()).collect(Collectors.joining(","))
                    );
                    effectsText.update(state);

                    graphics.drawImage(
                            effectsText.getSprite(),
                            humanoid.getPosition().intX() - camera.getPosition().intX(),
                            humanoid.getPosition().intY() - camera.getPosition().intY(),
                            null
                    );
                });
    }
}
