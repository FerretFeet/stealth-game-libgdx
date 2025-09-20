package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;

public class DecelerationComponent implements Component {
    private float friction;
    public DecelerationComponent(float friction) {
        this.friction = friction;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }
}
