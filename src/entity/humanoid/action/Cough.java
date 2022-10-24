package entity.humanoid.action;

import core.CollisionBox;
import core.Position;
import core.Size;
import entity.humanoid.Humanoid;
import entity.humanoid.efect.Sick;
import game.Game;
import game.GameLoop;
import state.State;

public class Cough extends Action{
    private int lifeSpanInSeconds;
    private Size spreadAreaSize;
    private double risk;

    public Cough() {
        lifeSpanInSeconds= GameLoop.UPDATES_PER_SECOND * 1;
        spreadAreaSize = new Size(2* Game.SPRITE_SIZE,2*Game.SPRITE_SIZE);
        risk = 1/10d; //unul din 10 se inbolnavesc
    }

    @Override
    public void update(State state, Humanoid performer) {
        if(--lifeSpanInSeconds == 0) {
            Position spreadAreaPosition = new Position(
                    performer.getPosition().getX() - spreadAreaSize.getWidth() / 2,
                    performer.getPosition().getY() - spreadAreaSize.getHeight() / 2
            );

            CollisionBox spreadArea = CollisionBox.of(spreadAreaPosition, spreadAreaSize);

            state.getGameObjectsOfClass(Humanoid.class).stream()
                    .filter(humanoid -> humanoid.getCollisionBox().collidesWith(spreadArea))
                    .filter(humanoid -> !humanoid.isAffectedBy(Sick.class))
                    .forEach(humanoid -> {
                        if(Math.random() < risk) {
                            humanoid.addEffect(new Sick());
                        }
                    });
        }
    }

    @Override
    public boolean isDone() {
        return lifeSpanInSeconds <= 0;
    }

    @Override
    public String getAnimationName() {
        return "cough";
    }
}
