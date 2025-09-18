package io.github.ferretFeet72.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.ferretFeet72.components.PositionComponent;
import io.github.ferretFeet72.components.SizeComponent;
import io.github.ferretFeet72.components.TextureComponent;
import io.github.ferretFeet72.utils.GameResources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class RenderSystemTest {

    private RenderSystem renderSystem;
    private Engine engine;
    private SpriteBatch mockBatch;

    // A small delta for float comparisons
    private static final float EPSILON = 0.0001f;

    @BeforeEach
    public void setUp() {
        // Mock Gdx.app to allow the tests to run in a non-LibGDX environment
        Gdx.app = Mockito.mock(Application.class);

        // Mock the Batch and TextureRegion objects
        mockBatch = Mockito.mock(SpriteBatch.class);
        GameResources.batch = mockBatch;

        // Initialize the system and engine
        engine = new Engine();
        renderSystem = new RenderSystem();
        engine.addSystem(renderSystem);
    }

    @Test
    public void testProcessEntity_DrawsCorrectly() {
        // Create components with specific values
        PositionComponent position = new PositionComponent(10, 20, 0);
        TextureComponent texture = new TextureComponent(Mockito.mock(TextureRegion.class));
        SizeComponent size = new SizeComponent(50, 60, 0);

        // Create and add entity with components to the engine
        Entity entity = new Entity();
        entity.add(position);
        entity.add(texture);
        entity.add(size);
        engine.addEntity(entity);

        // Call update, which will in turn call processEntity
        renderSystem.update(0);

        // Verify that the batch's draw method was called exactly once with the correct parameters
        verify(mockBatch, times(1)).draw(
            eq(texture.getRegion()),
            eq(position.getX()),
            eq(position.getY()),
            eq(size.getX()),
            eq(size.getY())
        );
    }

    @Test
    public void testUpdate_BatchIsCalledCorrectly() {
        // Add a mock entity so the system has something to process
        Entity entity = new Entity();
        entity.add(new PositionComponent(0, 0, 0));
        entity.add(new TextureComponent(Mockito.mock(TextureRegion.class)));
        entity.add(new SizeComponent(0, 0, 0));
        engine.addEntity(entity);

        // Call the update method
        renderSystem.update(0);

        // Verify that batch.begin() and batch.end() were called exactly once
        verify(mockBatch, times(1)).begin();
        verify(mockBatch, times(1)).end();
    }

    @Test
    public void testEntityWithoutRequiredComponentsIsNotProcessed() {
        // Create an entity with only a PositionComponent
        Entity entity = new Entity();
        entity.add(new PositionComponent(10, 20, 0));
        engine.addEntity(entity);

        // Call the update method
        renderSystem.update(0);

        // Verify that the batch's draw method was never called
        verify(mockBatch, never()).draw((Texture) any(), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }
}
