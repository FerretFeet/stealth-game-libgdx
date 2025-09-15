package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;

public class SpeedComponent implements Component {
    public int topSpeed;

    public SpeedComponent(int i) {
        topSpeed = i;
    }
}
