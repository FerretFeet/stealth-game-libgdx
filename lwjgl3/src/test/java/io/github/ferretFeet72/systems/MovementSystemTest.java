package io.github.ferretFeet72.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import io.github.ferretFeet72.components.PositionComponent;
import io.github.ferretFeet72.components.VelocityComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovementSystemTest {
    private MovementSystem movementSystem;
    private Engine engine;
    private Entity entity;
    private PositionComponent positionComponent;
    private VelocityComponent velocityComponent;

    // A small delta for float comparisons
    private static final float EPSILON = 0.0001f;

    @BeforeEach
    public void setUp() {
        // Mock Gdx.app to allow the tests to run in a non-LibGDX environment
        Gdx.app = Mockito.mock(Application.class);

        // Initialize the system and engine
        engine = new Engine();
        movementSystem = new MovementSystem();
        engine.addSystem(movementSystem);

        // Create the components and the entity
        positionComponent = new PositionComponent(0, 0, 0);
        velocityComponent = new VelocityComponent(0, 0, 0);
        entity = new Entity();

        // Add components to the entity
        entity.add(positionComponent);
        entity.add(velocityComponent);

        // Add the entity to the engine
        engine.addEntity(entity);
    }

    @Test
    public void testProcessEntity_MovesRight() {
        // Set a positive X velocity
        velocityComponent.setDx(100f);

        // Process the entity (delta time is irrelevant for this test)
        movementSystem.processEntity(entity, 0);

        // Assert that the x-position has been updated correctly
        assertEquals(100f, positionComponent.getX(), EPSILON);
        assertEquals(0f, positionComponent.getY(), EPSILON);
    }

    @Test
    public void testProcessEntity_MovesDiagonally() {
        // Set a positive X and Y velocity
        velocityComponent.setDx(75f);
        velocityComponent.setDy(50f);

        // Process the entity
        movementSystem.processEntity(entity, 0);

        // Assert that both the x and y positions have been updated
        assertEquals(75f, positionComponent.getX(), EPSILON);
        assertEquals(50f, positionComponent.getY(), EPSILON);
    }

    @Test
    public void testProcessEntity_NoMovement() {
        // Set zero velocity for both axes
        velocityComponent.setDx(0);
        velocityComponent.setDy(0);

        // Process the entity
        movementSystem.processEntity(entity, 0);

        // Assert that the position remains unchanged
        assertEquals(0f, positionComponent.getX(), EPSILON);
        assertEquals(0f, positionComponent.getY(), EPSILON);
    }

    @Test
    public void testProcessEntity_MultipleUpdates() {
        // Set a constant velocity
        velocityComponent.setDx(20f);
        velocityComponent.setDy(30f);

        // Process the entity three times
        movementSystem.processEntity(entity, 0);
        movementSystem.processEntity(entity, 0);
        movementSystem.processEntity(entity, 0);

        // Assert that the position is the sum of all updates
        assertEquals(60f, positionComponent.getX(), EPSILON); // 20 * 3
        assertEquals(90f, positionComponent.getY(), EPSILON); // 30 * 3
    }
}
