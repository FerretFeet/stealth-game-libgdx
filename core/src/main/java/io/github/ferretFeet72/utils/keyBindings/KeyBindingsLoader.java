package io.github.ferretFeet72.utils.keyBindings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import io.github.ferretFeet72.utils.GameResources;

import java.util.Map;

public class KeyBindingsLoader {


    public static Map<Integer, PlayerActions> loadKeyBinds(Map<Integer, PlayerActions> map) {
        KeyUtils.init();
        FileHandle file;
//        Check for user file, if not, use default
//        if (Gdx.files.local(GameResources.usrKeyMapLoc).exists()) {
//            file = Gdx.files.local(GameResources.usrKeyMapLoc);
//        } else {
            file = Gdx.files.internal("default-keymap.json");
//        }

//        translate from json to class
        Json json = new Json();
        KeyBindings bindings = json.fromJson(KeyBindings.class, file);

//        Empty keyBinds before mapping
        map.clear();
//        fill hashmap with keycode-player_action pairs
        for (KeyBindingEntry binding : bindings.keyBinds) {
//            Translate key string to key code
            int keyCode = KeyUtils.getKeyCode(binding.key);

//            Match action to enum
            PlayerActions keyAction = null;
            for (PlayerActions action : PlayerActions.values()) {
                if (action.name().equalsIgnoreCase(binding.action)) {
                    keyAction = action;
                    break;
                }
            }
//            input into table
            map.put(keyCode, keyAction);
        }
        return map;
    }
}
