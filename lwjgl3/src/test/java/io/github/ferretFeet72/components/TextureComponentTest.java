package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TextureComponentTest {
    private TextureRegion mockRegion;

    @BeforeEach
    void setup() {
        // Create mocks before each test
        mockRegion = mock(TextureRegion.class);
    }

    @Test
    void testTextureRegionConstructor() throws IllegalAccessException {
        TextureComponent component = new TextureComponent(mockRegion);

        assertSame(mockRegion, component.getRegion(), "Constructor should set region field to the passed TextureRegion");
    }

    @Test
    void testMutableFields() {
        TextureComponent component = new TextureComponent(mockRegion);
        TextureRegion mockNewRegion = mock(TextureRegion.class);
        component.setRegion(mockNewRegion);

        assertEquals(component.getRegion(), mockNewRegion, "Region field not mutable");
        assertNotEquals(component.getRegion(), mockRegion, "Region field not mutable");

    }
}
