package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {
    public float dx, dy, dz;
    public VelocityComponent(float dx, float dy, float dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }
}
