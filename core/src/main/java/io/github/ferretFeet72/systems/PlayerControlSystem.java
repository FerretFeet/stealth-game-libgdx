package io.github.ferretFeet72.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import io.github.ferretFeet72.components.*;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class PlayerControlSystem extends IteratingSystem {
//    Should occur before render?
//    private static final int PRIORITY = 1;

    private final ComponentMapper<InputComponent> im = ComponentMapper.getFor(InputComponent.class);
    private final ComponentMapper<VelocityComponent> tm = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<SpeedComponent> sm = ComponentMapper.getFor(SpeedComponent.class);
    private final ComponentMapper<AccelerationComponent> am = ComponentMapper.getFor(AccelerationComponent.class);


    public PlayerControlSystem() {
//        run on all entities with Player, Input, Velocity
        super(Family.all(InputComponent.class, VelocityComponent.class, PlayerComponent.class).get());
    }

    public void limitToTopSpeed(SpeedComponent speed, VelocityComponent velocity) {
        Vector2 vec = new Vector2(velocity.getDx(), velocity.getDy());
        float topSpeed = speed.getTopSpeed();
        if (vec.len2() > topSpeed * topSpeed) {
            vec.nor().scl(topSpeed);
            velocity.setDx(vec.x);
            velocity.setDy(vec.y);
        }
    }

    @Override
    protected void processEntity(Entity entity, float v) {
//        get components
        InputComponent input = im.get(entity);
        VelocityComponent velocity = tm.get(entity);
        SpeedComponent speed = sm.get(entity);
        AccelerationComponent accel = am.get(entity);
        DecelerationComponent decel = entity.getComponent(DecelerationComponent.class);

        Vector2 vec = new Vector2(velocity.getDx(), velocity.getDy());

        float topSpeed = speed.getTopSpeed();

//        movement controls, change velocity
        if (input.isUpPressed()) {
            vec.y += accel.getAy() * v;
        }
        if (input.isDownPressed()) {
            vec.y -= accel.getAy() * v;
        }

        if (input.isLeftPressed()) {
            vec.x -= accel.getAx() * v;
        }
        if (input.isRightPressed()) {
            vec.x += accel.getAx() * v;
        }
        if (input.isInteractPressed()) {
            entity.add(new InteractionEventComponent());
        }

//      apply friction
        float friction = decel.getFriction();
        vec.scl(friction);

//        Clamp to top speed
        if (vec.len2() > topSpeed * topSpeed) {
            vec.nor().scl(topSpeed);
        }

//        set values
        speed.setCurSpeed((float) sqrt(vec.len2()));
        velocity.setDx(vec.x);
        velocity.setDy(vec.y);


    }
}
