package io.github.ferretFeet72.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccelerationComponentTest {

    @Test
    public void testConstructorSetsFields() {
        AccelerationComponent component = new AccelerationComponent(3f, 2f, 1f);

        assertEquals(3f, component.getAx(), 0.0001f);
        assertEquals(2f, component.getAy(), 0.0001f);
        assertEquals(1f, component.getAz(), 0.0001f);
    }

    @Test
    public void testMutableFields() {
        AccelerationComponent component = new AccelerationComponent(3f, 2f, 1f);

        component.setAx(4f);
        component.setAy(5f);
        component.setAz(6f);

        assertEquals(4f, component.getAx(), 0.0001f);
        assertEquals(5f, component.getAy(), 0.0001f);
        assertEquals(6f, component.getAz(), 0.0001f);
    }
}
