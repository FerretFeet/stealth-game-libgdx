package io.github.ferretFeet72.components;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class InputComponentTest {

    @Test
    void testConstructorSetsFields() {
        InputComponent component = new InputComponent();

        assertFalse(component.isRightPressed());
        assertFalse(component.isDownPressed());
        assertFalse(component.isLeftPressed());
        assertFalse(component.isUpPressed());
    }

    void testMutableFields() {
        InputComponent component = new InputComponent();

        component.setDownPressed(true);
        component.setUpPressed(true);
        component.setLeftPressed(true);
        component.setRightPressed(true);

        assertTrue(component.isDownPressed());
        assertTrue(component.isUpPressed());
        assertTrue(component.isLeftPressed());
        assertFalse(component.isRightPressed());
    }
}
