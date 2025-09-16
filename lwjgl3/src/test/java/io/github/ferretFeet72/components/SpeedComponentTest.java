package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SpeedComponentTest {
    @Test
    void testConstructorAssignsFields() {
        SpeedComponent component = new SpeedComponent(5f, 1f);

        assertEquals(5f, component.getTopSpeed(), 0.0001f, "Top speed should match constructor value");
        assertEquals(1f, component.getBottomSpeed(), 0.0001f, "Bottom speed should match constructor value");
        assertEquals(0f, component.getCurSpeed(), 0.0001f, "Cur speed should match constructor value");
    }

    @Test
    void testFieldsAreMutable() {
        SpeedComponent component = new SpeedComponent(5f, 1f);

        component.setTopSpeed(1f);
        component.setBottomSpeed(2f);
        component.setCurSpeed(3f);

        assertEquals(1f,  component.getTopSpeed(), 0.0001f, "Top speed should be mutable");
        assertEquals(2f,  component.getBottomSpeed(), 0.0001f, "Bottom speed should be mutable");
        assertEquals(3f,  component.getCurSpeed(), 0.0001f, "Cur speed should be mutable");
    }
}
