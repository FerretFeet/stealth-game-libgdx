package io.github.ferretFeet72.utils.keyBindings;

import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.Map;

public class KeyUtils {
//    Used for keymapping
    public static Map<Integer, PlayerActions> defKeyMap;
    public static Map<Integer, PlayerActions> defMouseMap;

    public static void init() {
        defKeyMap = new HashMap<>();
        // Populate default keysettings with the new order
        defKeyMap.put(Input.Keys.A, PlayerActions.MOVE_LEFT);
        defKeyMap.put(Input.Keys.D, PlayerActions.MOVE_RIGHT);
        defKeyMap.put(Input.Keys.S, PlayerActions.MOVE_DOWN);
        defKeyMap.put(Input.Keys.W, PlayerActions.MOVE_UP);
        defKeyMap.put(Input.Keys.E, PlayerActions.INTERACT);
        defKeyMap.put(Input.Keys.SPACE, PlayerActions.KNOCKOUT);

        // Populate default mouse buttons
        defMouseMap = new HashMap<>();
        defMouseMap.put(Input.Buttons.LEFT, PlayerActions.SHOOT);
    }

//    public static int getKeyCode(PlayerActions key) {
//
//        return defKeyMap.getOrDefault(key, -1);
//    }
//
//    public static int getMouseCode(PlayerActions key) {
//        return defMouseMap.getOrDefault(key, -1);
//    }


}
