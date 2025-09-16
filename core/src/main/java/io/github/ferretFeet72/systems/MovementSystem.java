package io.github.ferretFeet72.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import io.github.ferretFeet72.components.PositionComponent;
import io.github.ferretFeet72.components.VelocityComponent;

public class MovementSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<VelocityComponent> tm = ComponentMapper.getFor(VelocityComponent.class);

    public MovementSystem() {
//        run on all entities with position, velocity
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }




    @Override
    protected void processEntity(Entity entity, float v) {
        PositionComponent pos = pm.get(entity);
        VelocityComponent vel = tm.get(entity);
//        calculate position based off current position and velocity
        pos.setX(pos.getX() + vel.getDx());
        pos.setY(pos.getY() + vel.getDy());
    }
}
