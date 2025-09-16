package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerComponentTest {
    @Test
    void testSchema () {
        Field[] fields = PlayerComponent.class.getDeclaredFields();
        assertEquals(0, fields.length, "PlayerComponent should have exactly 0 fields");
    }
}
