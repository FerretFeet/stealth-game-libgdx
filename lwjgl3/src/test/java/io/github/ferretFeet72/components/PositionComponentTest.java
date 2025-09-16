package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PositionComponentTest {
    @Test
    void testConstructorSetsFields() {
        PositionComponent component = new PositionComponent(1f, 2f, 3f);

        assertEquals(1f, component.getX(), 0.0001);
        assertEquals(2f, component.getY(), 0.0001);
        assertEquals(3f, component.getZ(), 0.0001);
    }

    @Test
    void testFieldsAreMutable() {
        PositionComponent component = new PositionComponent(1f, 2f, 3f);

        component.setX(5f);
        component.setY(6f);
        component.setZ(7f);

        assertEquals(5f, component.getX(), 0.0001);
        assertEquals(6f, component.getY(), 0.0001);
        assertEquals(7f, component.getZ(), 0.0001);
    }
}
