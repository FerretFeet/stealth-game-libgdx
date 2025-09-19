package io.github.ferretFeet72.utils.keyBindings;

import java.util.Map;

public class InputMapper {
    private final Map<Integer, PlayerActions> keyBinds;

    public InputMapper(Map<Integer, PlayerActions> keyBinds) {
        this.keyBinds = keyBinds;
    }

    public PlayerActions getActionForKey(int keyCode) {
        return keyBinds.get(keyCode);
    }
}
