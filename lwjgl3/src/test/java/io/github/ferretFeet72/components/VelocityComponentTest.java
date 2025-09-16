package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class VelocityComponentTest {

    @Test
    void testConstructorAssignsFields() {
        VelocityComponent v = new VelocityComponent(1.5f, -2.0f, 3.4f);

        assertEquals(1.5f, v.getDx(), 0.0001f, "dx should match constructor value");
        assertEquals(-2.0f, v.getDy(), 0.0001f, "dy should match constructor value");
        assertEquals(3.4f, v.getDz(), 0.0001f, "dz should match constructor value");
    }

    @Test
    void testMutableFields() {
        VelocityComponent v = new VelocityComponent(1.5f, -2.0f, 3.4f);

        v.setDx(-5f);
        v.setDy(8.0f);
        v.setDz(2.1f);

        assertEquals(-5f, v.getDx(), 0.0001f, "dx should match new value");
        assertEquals(8.0f, v.getDy(), 0.0001f, "dy should match new value");
        assertEquals(2.1f, v.getDz(), 0.0001f, "dz should match new value");
    }

}
