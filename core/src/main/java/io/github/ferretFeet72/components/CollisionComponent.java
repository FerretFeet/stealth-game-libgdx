package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;

public class CollisionComponent implements Component {
    private boolean collisionEnabled;



    private boolean isColliding;
    public CollisionComponent(boolean collisionEnabled) {
        this.collisionEnabled = collisionEnabled;
    }

    public boolean isCollisionEnabled() {
        return collisionEnabled;
    }

    public void setCollisionEnabled(boolean collisionEnabled) {
        this.collisionEnabled = collisionEnabled;
    }

    public boolean isColliding() {
        return isColliding;
    }

    public void setColliding(boolean colliding) {
        isColliding = colliding;
    }
}
