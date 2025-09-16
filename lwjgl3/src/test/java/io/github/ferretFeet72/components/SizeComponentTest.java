package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SizeComponentTest {

    @Test
    void testConstructorAssignsFields() {
        SizeComponent component = new SizeComponent(5f, 3f, 0f);
        assertEquals(5f, component.getX(), 0.0001f);
        assertEquals(3f, component.getY(), 0.0001f);
        assertEquals(0f, component.getZ(), 0.0001f);
    }

    @Test
    void testFieldsAreMutable() {
        SizeComponent component = new SizeComponent(5f, 3f, 0f);

        component.setX(1f);
        component.setY(2f);
        component.setZ(3f);

        assertEquals(1f, component.getX(), 0.0001f);
        assertEquals(2f, component.getY(), 0.0001f);
        assertEquals(3f, component.getZ(), 0.0001f);
    }
}
