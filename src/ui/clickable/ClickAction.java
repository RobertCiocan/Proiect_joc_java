package ui.clickable;

import ExceptionHandle.AllExceptions;
import state.State;

public interface ClickAction {
    void execute(State state) throws AllExceptions;
}
