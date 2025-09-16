package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionComponentTest {

    @Test
    void testConstructorSetsFields() {
        CollisionComponent component = new CollisionComponent(true);

        assertTrue(component.isCollisionEnabled());
    }

    @Test
    void testMutableFields() {
        CollisionComponent component = new CollisionComponent(true);
        component.setCollisionEnabled(false);

        assertFalse(component.isCollisionEnabled());


    }
}

