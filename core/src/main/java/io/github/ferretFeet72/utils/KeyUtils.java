package io.github.ferretFeet72.utils;

import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.Map;

public class KeyUtils {
//    Used for keymapping
    public static Map<String, Integer> defKeyMap;
    public static void init() {
        defKeyMap = new HashMap<>();
//        Populate default keysettings
        defKeyMap.put("A", Input.Keys.A);
        defKeyMap.put("D", Input.Keys.D);
        defKeyMap.put("S", Input.Keys.S);
        defKeyMap.put("W", Input.Keys.W);

    }

    public static int getKeyCode(String key) {

        return defKeyMap.getOrDefault(key, -1);
    }


}
