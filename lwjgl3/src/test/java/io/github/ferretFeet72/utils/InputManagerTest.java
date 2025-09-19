package io.github.ferretFeet72.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import io.github.ferretFeet72.components.InputComponent;
import io.github.ferretFeet72.components.PlayerComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InputManagerTest {

    @Mock
    private Engine mockEngine;
    @Mock
    private ImmutableArray<Entity> mockEntities;
    @Mock
    private Entity mockPlayer;
    @Mock
    private InputComponent mockInputComponent;
    @Mock
    private Gdx gdx;
    @Mock
    private Gdx.Files files;
    @Mock
    private Json mockJson;

    private InputManager inputManager;

    @BeforeEach
    public void setUp() {
        // Mock the static GameResources class and its engine field
        try (MockedStatic<GameResources> gameResourcesMock = mockStatic(GameResources.class)) {
            gameResourcesMock.when(() -> GameResources.engine).thenReturn(mockEngine);

            // Mock the Ashley ECS to return a player entity with an InputComponent
            when(mockEngine.getEntitiesFor(any(Family.class))).thenReturn(mockEntities);
            when(mockEntities.first()).thenReturn(mockPlayer);

            // Mock the ComponentMapper to return the mock InputComponent
            ComponentMapper<InputComponent> mockMapper = mock(ComponentMapper.class);
            when(mockMapper.get(mockPlayer)).thenReturn(mockInputComponent);
            try (MockedStatic<ComponentMapper> mapperMock = mockStatic(ComponentMapper.class)) {
                mapperMock.when(() -> ComponentMapper.getFor(InputComponent.class)).thenReturn(mockMapper);

                // Initialize the InputManager instance
                inputManager = new InputManager();
            }
        }
    }

    private void mockKeyBinds(boolean userFileExists) {
        try (MockedStatic<Gdx> gdxMock = mockStatic(Gdx.class);
             MockedStatic<KeyUtils> keyUtilsMock = mockStatic(KeyUtils.class)) {
            // Mock Gdx and its files
            gdxMock.when(() -> Gdx.files).thenReturn(files);
            FileHandle mockFileHandle = mock(FileHandle.class);

            // Create sample bindings data
            Binding wBinding = new Binding("W", "MOVE_UP");
            Bindings bindings = new Bindings();
            bindings.keyBinds = new java.util.ArrayList<>(Collections.singletonList(wBinding));

            // Mock the behavior for a user-defined keymap
            if (userFileExists) {
                when(files.local(any(String.class))).thenReturn(mockFileHandle);
                when(files.local(any(String.class)).exists()).thenReturn(true);
            } else {
                when(files.local(any(String.class)).exists()).thenReturn(false);
                when(files.internal(any(String.class))).thenReturn(mockFileHandle);
            }
            // Mock the JSON parsing process
            when(mockFileHandle.readString()).thenReturn("mock-json-content");
            when(new Json().fromJson(eq(Bindings.class), any(FileHandle.class))).thenReturn(bindings);

            // Mock the KeyUtils to translate the key string
            keyUtilsMock.when(() -> KeyUtils.getKeyCode("W")).thenReturn(Input.Keys.W);
        }
    }

    @Test
    public void testKeyDown_W_setsUpPressedTrue() {
        mockKeyBinds(false);
        inputManager.keyDown(Input.Keys.W);
        verify(mockInputComponent, times(1)).setUpPressed(true);
    }

    @Test
    public void testKeyDown_D_setsRightPressedTrue() {
        mockKeyBinds(false);
        inputManager.keyDown(Input.Keys.D);
        verify(mockInputComponent, times(1)).setRightPressed(true);
    }

    @Test
    public void testKeyDown_A_setsLeftPressedTrue() {
        mockKeyBinds(false);
        inputManager.keyDown(Input.Keys.A);
        verify(mockInputComponent, times(1)).setLeftPressed(true);
    }

    @Test
    public void testKeyDown_S_setsDownPressedTrue() {
        mockKeyBinds(false);
        inputManager.keyDown(Input.Keys.S);
        verify(mockInputComponent, times(1)).setDownPressed(true);
    }

    @Test
    public void testKeyUp_W_setsUpPressedFalse() {
        mockKeyBinds(false);
        inputManager.keyUp(Input.Keys.W);
        verify(mockInputComponent, times(1)).setUpPressed(false);
    }

    @Test
    public void testKeyUp_D_setsRightPressedFalse() {
        mockKeyBinds(false);
        inputManager.keyUp(Input.Keys.D);
        verify(mockInputComponent, times(1)).setRightPressed(false);
    }

    @Test
    public void testKeyUp_A_setsLeftPressedFalse() {
        mockKeyBinds(false);
        inputManager.keyUp(Input.Keys.A);
        verify(mockInputComponent, times(1)).setLeftPressed(false);
    }

    @Test
    public void testKeyUp_S_setsDownPressedFalse() {
        mockKeyBinds(false);
        inputManager.keyUp(Input.Keys.S);
        verify(mockInputComponent, times(1)).setDownPressed(false);
    }

    @Test
    public void testGetKeyBinds_UserFileExists() {
        try (MockedStatic<Gdx> gdxMock = mockStatic(Gdx.class)) {
            gdxMock.when(() -> Gdx.files).thenReturn(files);
            FileHandle mockFileHandleLocal = mock(FileHandle.class);
            when(files.local(any(String.class)).exists()).thenReturn(true);
            when(files.local(eq("user-keymap.json"))).thenReturn(mockFileHandleLocal);

            // Test logic that relies on the getKeyBinds() method
            inputManager.keyDown(Input.Keys.W); // This will trigger getKeyBinds()

            // Verify that the local file was checked
            verify(files, times(1)).local(eq("user-keymap.json"));
            // Verify that the internal file was NOT checked
            verify(files, never()).internal(any(String.class));
        }
    }

    @Test
    public void testGetKeyBinds_DefaultFileUsed() {
        try (MockedStatic<Gdx> gdxMock = mockStatic(Gdx.class)) {
            gdxMock.when(() -> Gdx.files).thenReturn(files);
            FileHandle mockFileHandleInternal = mock(FileHandle.class);
            when(files.local(any(String.class)).exists()).thenReturn(false);
            when(files.internal(eq("default-keymap.json"))).thenReturn(mockFileHandleInternal);

            // Test logic that relies on the getKeyBinds() method
            inputManager.keyDown(Input.Keys.W); // This will trigger getKeyBinds()

            // Verify that the local file was checked
            verify(files, times(1)).local(eq("user-keymap.json"));
            // Verify that the internal file WAS checked
            verify(files, times(1)).internal(eq("default-keymap.json"));
        }
    }
}
