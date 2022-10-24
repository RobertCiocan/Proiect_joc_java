package input.mouse;

import input.Input;
import input.mouse.action.MouseAction;
import state.State;
import ui.clickable.UIImage;

import java.util.Optional;

public class MouseHandler {
    private MouseConsumer activeConsumer;
    private MouseAction primaryButtonAction; //left click

    public void update(State state){
        final Input input = state.getInput();

        handelPrimaryButton(state);
        handleActiveConsumer(state,input);

        cleanUp(input);
    }

    private void handelPrimaryButton(State state) {
        if(primaryButtonAction != null){
            setActiveConsumer(primaryButtonAction);
            primaryButtonAction.update(state);
        }
    }

    private void cleanUp(Input input) {
        if(!input.isMousePressed()){ //daca nu mai e apasat (am lasat clickul)
            activeConsumer=null;
        }
        input.clearMouseClick();
    }

    private void handleActiveConsumer(State state, Input input) {
        //un fel de singleton (ne asiguram ca avem doar un singur active consumer )
        if(activeConsumer != null){
            if(input.isMouseClicked()){
                activeConsumer.onClick(state);
            }else if (input.isMousePressed()){
                activeConsumer.onDrag(state);
            }
        }
    }

    public MouseConsumer getActiveConsumer() {
        return activeConsumer;
    }

    public void setActiveConsumer(MouseConsumer activeConsumer) {
        if(this.activeConsumer == null){
            this.activeConsumer = activeConsumer;
        }
    }

    public void setPrimaryButtonAction(MouseAction primaryButtonAction) {
        this.primaryButtonAction = primaryButtonAction;
    }

    public MouseAction getPrimaryButtonAction() {
        return primaryButtonAction;
    }

    public Optional<UIImage> getSprite(){
        if(primaryButtonAction != null){
            return Optional.ofNullable(primaryButtonAction.getSprite());
        }

        return Optional.empty();
    }
}
