package io.github.ferretFeet72.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import io.github.ferretFeet72.components.InputComponent;
import io.github.ferretFeet72.components.PlayerComponent;
import io.github.ferretFeet72.components.SpeedComponent;
import io.github.ferretFeet72.components.VelocityComponent;

public class PlayerControlSystem extends IteratingSystem {
//    Should occur before render?
    private static final int PRIORITY = 1;

    private final ComponentMapper<InputComponent> im = ComponentMapper.getFor(InputComponent.class);
    private final ComponentMapper<VelocityComponent> tm = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<SpeedComponent> sm = ComponentMapper.getFor(SpeedComponent.class);


    public PlayerControlSystem() {
//        run on all entities with Player, Input, Velocity
        super(Family.all(InputComponent.class, VelocityComponent.class, PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
//        get components
        InputComponent input = im.get(entity);
        VelocityComponent velocity = tm.get(entity);
        SpeedComponent speed = sm.get(entity);

//        Need momentum system?
//        Kill all non-active movement
        if (!input.leftPressed && !input.rightPressed) {
            velocity.dx = 0;
        }
        if (!input.upPressed && !input.downPressed) {
            velocity.dy = 0;
        }
//        movement controls, change velocity
        if (input.upPressed) {
            velocity.dy = speed.topSpeed;
        }
        if (input.downPressed) {
            velocity.dy = -speed.topSpeed;
        }

        if (input.leftPressed) {
            velocity.dx = -speed.topSpeed;
        }
        if (input.rightPressed) {
            velocity.dx = speed.topSpeed;
        }

    }
}
