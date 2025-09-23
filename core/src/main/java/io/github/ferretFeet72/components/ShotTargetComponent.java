package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;

public class ShotTargetComponent implements Component {
    float x, y;
    public ShotTargetComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
