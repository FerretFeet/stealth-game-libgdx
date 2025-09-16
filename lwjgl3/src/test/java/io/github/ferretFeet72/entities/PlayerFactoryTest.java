package io.github.ferretFeet72.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import io.github.ferretFeet72.components.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PlayerFactoryTest {

    // Mocks for LibGDX dependencies
    private Engine engine;
    private FileHandle fileHandleMock;

    @BeforeEach
    public void setup() {
        // Mock the LibGDX static classes
        Gdx.files = mock(com.badlogic.gdx.Files.class);
        Gdx.gl = mock(com.badlogic.gdx.graphics.GL20.class);

        // Mock the Engine and FileHandle
        engine = mock(Engine.class);
        fileHandleMock = mock(FileHandle.class);

        // Define the behavior of the mocks
        when(Gdx.files.internal(anyString())).thenReturn(fileHandleMock);
        // The Texture constructor calls Gdx.gl.glTexImage2D, so we need to mock it.
        when(fileHandleMock.name()).thenReturn("centaur_brown_f.png");
        doNothing().when(Gdx.gl).glTexImage2D(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), any());
    }

    @Test
    public void testCreatePlayerEntityAddsAllComponents() {
        try (MockedConstruction<Texture> mockedTexture = mockConstruction(Texture.class)) {
            // Act
            Entity playerEntity = PlayerFactory.create(engine);

            // Assert that the entity was created and has the correct components
            assertNotNull(playerEntity);
            assertNotNull(playerEntity.getComponent(PlayerComponent.class));
            assertNotNull(playerEntity.getComponent(InputComponent.class));
            assertNotNull(playerEntity.getComponent(SpeedComponent.class));
            assertNotNull(playerEntity.getComponent(PositionComponent.class));
            assertNotNull(playerEntity.getComponent(SizeComponent.class));
            assertNotNull(playerEntity.getComponent(VelocityComponent.class));
            assertNotNull(playerEntity.getComponent(CollisionComponent.class));
            assertNotNull(playerEntity.getComponent(TextureComponent.class));

            // Assert that the Texture was correctly constructed
            verify(Gdx.files).internal("crawl-tiles/player/base/centaur_brown_f.png");
            assertNotNull(mockedTexture.constructed());

            // Verify that the entity was added to the engine
            verify(engine).addEntity(playerEntity);
        }
    }
}

