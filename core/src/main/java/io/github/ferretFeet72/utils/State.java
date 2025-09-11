package io.github.ferretFeet72.utils;

public interface State {
    public void enter();
    public void exit();
    public void update(float delta);
    public void handleInput(float delta);
}

// used in objects which hold a State object.
//    public void setState(PlayerState newState) {
//        if (currentState != null) {
//            currentState.exit(this);
//        }
//        this.currentState = newState;
//        if (newState != null) {
//            newState.enter(this);
//        }
//    }
