package io.github.ferretFeet72.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import io.github.ferretFeet72.components.*;
import io.github.ferretFeet72.entities.PlayerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerControlSystemTest {
    private static PlayerControlSystem system;
    private static Entity entity;
    private static InputComponent input;
    private static VelocityComponent velocity;
    private static SpeedComponent speed;
    private static AccelerationComponent acceleration;

    private final float DELTA_TIME = 1 / 60f;

    @BeforeEach
    public void setup() {
        Gdx.app = Mockito.mock(Application.class);

        system = new PlayerControlSystem();

        Engine engine = new Engine();
        engine.addSystem(system);

        entity = new Entity();

        input = new InputComponent();
        velocity = new VelocityComponent(0, 0, 0);
        speed = new SpeedComponent(500, 0);
        acceleration = new AccelerationComponent(10, 10, 0);

        entity.add(input);
        entity.add(velocity);
        entity.add(speed);
        entity.add(acceleration);
        entity.add(new PlayerComponent());

        engine.addEntity(entity);
    }

    @Test
    public void testLimitToTopSpeed_VelocityClamped() {
        velocity.setDx(150);
        velocity.setDy(150);
        speed.setTopSpeed(100);

        system.limitToTopSpeed(speed, velocity);

        float finalSpeed = (float) Math.sqrt(
            Math.pow(velocity.getDx(), 2) + Math.pow(velocity.getDy(), 2)
        );
        assertEquals(100, finalSpeed, 0.001f);
    }

    @Test
    public void testLimitToTopSpeed_VelocityNotClamped() {

        velocity.setDx(50);
        velocity.setDy(50);
        speed.setTopSpeed(200);
//        keep values for comparison
        float origDx = velocity.getDx();
        float origDy = velocity.getDy();

        system.limitToTopSpeed(speed, velocity);

        assertEquals(origDx, velocity.getDx(), 0.0001f);
        assertEquals(origDy, velocity.getDy(), 0.0001f);
    }

    @Test
    public void testProcessEntity_AccelUp() {
        input.setUpPressed(true);

        system.processEntity(entity, DELTA_TIME);


//        vel.y increase
        assertEquals(acceleration.getAy() * DELTA_TIME, velocity.getDy(), 0.0001f, "Velocity y should increase");
//        vel.x is constant
        assertEquals(0, velocity.getDx(), 0.001f, "Velocity x should be constant");
//        curSpeed updates
        assertEquals(acceleration.getAy() * DELTA_TIME, speed.getCurSpeed(), 0.0001f, "speed should be set");
    }

    @Test
    public void testProcessEntity_AccelDiag() {
        input.setDownPressed(true);
        input.setRightPressed(true);
        system.processEntity(entity, DELTA_TIME);

        assertEquals(-acceleration.getAy() * DELTA_TIME, velocity.getDy(), 0.0001f, "Velocity y should decrease");
        assertEquals(acceleration.getAx() * DELTA_TIME, velocity.getDx(), 0.0001f, "Velocity x should increase");
        assertEquals(
            Math.sqrt(Math.pow(acceleration.getAy() * DELTA_TIME, 2) + (Math.pow(acceleration.getAx() * DELTA_TIME, 2))),
            speed.getCurSpeed(), 0.0001f, "speed should be set");
    }

    @Test
    public void testProcessEntity_FrictionWithNoInput() {
        velocity.setDx(100);
        velocity.setDy(50);

        system.processEntity(entity, DELTA_TIME);


        // Calculate expected velocity after friction (0.8f)
        float expectedDx = 100 * 0.8f;
        float expectedDy = 50 * 0.8f;

        // Verify velocity has decreased
        assertEquals(expectedDx, velocity.getDx(), 0.0001f);
        assertEquals(expectedDy, velocity.getDy(), 0.0001f);
        assertTrue(speed.getCurSpeed() < Math.sqrt(100 * 100 + 50 * 50));
    }

    @Test
    public void testProcessEntity_OpposingInputCancelsAcceleration() {
        input.setUpPressed(true);
        input.setDownPressed(true);
        system.processEntity(entity, DELTA_TIME);

        // Verify that velocity remains zero
        assertEquals(0, velocity.getDx(), 0.0001f);
        assertEquals(0, velocity.getDy(), 0.0001f);
    }
}

