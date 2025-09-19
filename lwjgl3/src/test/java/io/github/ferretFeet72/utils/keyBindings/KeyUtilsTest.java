package io.github.ferretFeet72.utils.keyBindings;

import com.badlogic.gdx.Input;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyUtilsTest {

    @BeforeEach
    void setUp() {
        KeyUtils.init(); // make sure default keys are loaded before each test
    }

    @Test
    void testInitPopulatesDefaultKeyMap() {
        assertNotNull(KeyUtils.defKeyMap, "defKeyMap should not be null after init");
        assertEquals(Input.Keys.A, KeyUtils.defKeyMap.get("A"));
        assertEquals(Input.Keys.D, KeyUtils.defKeyMap.get("D"));
        assertEquals(Input.Keys.S, KeyUtils.defKeyMap.get("S"));
        assertEquals(Input.Keys.W, KeyUtils.defKeyMap.get("W"));

        // The map should have exactly 4 keys
        assertEquals(4, KeyUtils.defKeyMap.size(), "defKeyMap should have 4 entries");
    }

    @Test
    void testGetKeyCodeReturnsCorrectCode() {
        assertEquals(Input.Keys.A, KeyUtils.getKeyCode("A"));
        assertEquals(Input.Keys.D, KeyUtils.getKeyCode("D"));
        assertEquals(Input.Keys.S, KeyUtils.getKeyCode("S"));
        assertEquals(Input.Keys.W, KeyUtils.getKeyCode("W"));
    }

    @Test
    void testGetKeyCodeReturnsMinusOneForUnknownKey() {
        assertEquals(-1, KeyUtils.getKeyCode("Z"));
        assertEquals(-1, KeyUtils.getKeyCode("INVALID"));
        assertEquals(-1, KeyUtils.getKeyCode(""), "Empty string should return -1");
        assertEquals(-1, KeyUtils.getKeyCode(null), "Null key should return -1 safely");
    }
}
