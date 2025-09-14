package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;

public class CollisionComponent implements Component {
    boolean collisionEnabled;
    public CollisionComponent(boolean collisionEnabled) {
        this.collisionEnabled = collisionEnabled;
    }
}
