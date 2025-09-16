package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;

public class SpeedComponent implements Component {
    private float curSpeed;
    private float topSpeed;
    private float bottomSpeed;

    public SpeedComponent(float topSpeed, float bottomSpeed) {
        this.topSpeed = topSpeed;
        this.bottomSpeed = bottomSpeed;
    }

    public float getCurSpeed() {
        return curSpeed;
    }

    public void setCurSpeed(float curSpeed) {
        this.curSpeed = curSpeed;
    }

    public float getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(float topSpeed) {
        this.topSpeed = topSpeed;
    }

    public float getBottomSpeed() {
        return bottomSpeed;
    }

    public void setBottomSpeed(float bottomSpeed) {
        this.bottomSpeed = bottomSpeed;
    }
}
