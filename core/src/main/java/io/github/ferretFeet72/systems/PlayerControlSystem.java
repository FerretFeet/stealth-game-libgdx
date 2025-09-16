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
    private static final int PRIORITY = 1;

    private final ComponentMapper<InputComponent> im = ComponentMapper.getFor(InputComponent.class);
    private final ComponentMapper<VelocityComponent> tm = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<SpeedComponent> sm = ComponentMapper.getFor(SpeedComponent.class);
    private final ComponentMapper<AccelerationComponent> am = ComponentMapper.getFor(AccelerationComponent.class);


    public PlayerControlSystem() {
//        run on all entities with Player, Input, Velocity
        super(Family.all(InputComponent.class, VelocityComponent.class, PlayerComponent.class).get());
    }

    float limitToTopSpeed(float topSpeed, float changedVectorVal, float controlVectorVal) {
//        take the destructured vector values
//        ensure changed value does not cause
//        to exceed topSpeed
        if ((changedVectorVal * changedVectorVal) + (controlVectorVal * controlVectorVal) >= topSpeed) {
            float newChangedValSquared = ((topSpeed * topSpeed) - (controlVectorVal * controlVectorVal));
            changedVectorVal = (float) sqrt(newChangedValSquared);
            return changedVectorVal;
        }
        return changedVectorVal;
    }

    float decelerate(float vectorDir, float accelDir) {
        if (vectorDir != 0) {
            float newVectorDir = abs(vectorDir) - abs(accelDir);
            if (newVectorDir <= 0) {
                return 0;
            } else {
                return newVectorDir;
            }
        }
        return 0;
    }

    @Override
    protected void processEntity(Entity entity, float v) {
//        get components
        InputComponent input = im.get(entity);
        VelocityComponent velocity = tm.get(entity);
        SpeedComponent speed = sm.get(entity);
        AccelerationComponent accel = am.get(entity);

        Vector2 vec = new Vector2(velocity.getDx(), velocity.getDy());

        float topSpeed = speed.getTopSpeed();


//        Need momentum system?
//        Kill all non-active movement
        if (!input.isLeftPressed() && !input.isRightPressed()) {
//            if (vec.x > 0) {
//                vec.x = decelerate(vec.x, accel.getAx());
//            }
//            if (vec.x < 0) {
//                vec.x = -decelerate(vec.x, accel.getAx());
//            }
//            velocity.setDx(vec.x);
//            velocity.setDx(0);
        }
//
        if (!input.isUpPressed() && !input.isDownPressed()) {
//            if (vec.y > 0) {
//                vec.y = decelerate(vec.y, accel.getAy());
//            }
//            if (vec.y < 0) {
//                vec.y = -decelerate(vec.y, accel.getAy());
//            }
//            velocity.setDy(vec.y);
//            velocity.setDy(0);
        }
//        movement controls, change velocity
        if (input.isUpPressed()) {
//            if exceeding top speed
            vec.y = limitToTopSpeed(topSpeed, vec.y, vec.x);
//            set velocity to velocity + accel
            velocity.setDy(vec.y + accel.getAy());

        }
        if (input.isDownPressed()) {
            vec.y = -limitToTopSpeed(topSpeed, vec.y, vec.x);
            velocity.setDy(vec.y - accel.getAy());
        }

        if (input.isLeftPressed()) {
            vec.x =  -limitToTopSpeed(topSpeed, vec.x, vec.y);
            velocity.setDx(vec.x - accel.getAx());
        }
        if (input.isRightPressed()) {
            vec.x = limitToTopSpeed(topSpeed, vec.x, vec.y);
            velocity.setDx(vec.x + accel.getAx());
        }


    }
}
