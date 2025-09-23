package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;

public class InputComponent implements Component {
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean interactPressed;
    private boolean shootPressed;
    private boolean knockoutPressed;


    public InputComponent() {
        this.upPressed = false;
        this.downPressed = false;
        this.leftPressed = false;
        this.rightPressed = false;
        this.interactPressed = false;
        this.shootPressed = false;
        this.knockoutPressed = false;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isInteractPressed() {
        return interactPressed;
    }

    public void setInteractPressed(boolean interactPressed) {
        this.interactPressed = interactPressed;
    }

    public boolean isShootPressed() {
        return shootPressed;
    }

    public boolean isKnockoutPressed() {
        return knockoutPressed;
    }

    public void setShootPressed(boolean shootPressed) {
        this.shootPressed = shootPressed;
    }

    public void setKnockoutPressed(boolean knockoutPressed) {
        this.knockoutPressed = knockoutPressed;
    }
}
