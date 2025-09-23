package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class ShooterComponent implements Component {
    private Entity entity;
    public ShooterComponent(Entity entity) {
        this.entity = entity;
    }


    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
