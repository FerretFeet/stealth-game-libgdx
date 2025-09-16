package io.github.ferretFeet72.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import io.github.ferretFeet72.components.InputComponent;
import io.github.ferretFeet72.components.PlayerComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

enum PlayerAction {
//    Allowed player actions
    MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT
}

class Binding {
//    hold key & associated action
    public String key;
    public String action;
    public Binding(String key, String value) {
        this.key = key;
        this.action = value;
    }
    public Binding() {}
}

class Bindings {
//    hold instances of binding
    public ArrayList<Binding> keyBinds;
    public Bindings() {
        keyBinds = new ArrayList<>();
    }

}



public class InputManager implements InputProcessor {
    private final Map<Integer, PlayerAction> keyBinds = new HashMap<>();
    Entity player = GameResources.engine.getEntitiesFor(Family.all(PlayerComponent.class, InputComponent.class).get()).first();
    ComponentMapper<InputComponent> im = ComponentMapper.getFor(InputComponent.class);
    InputComponent inputComponent = im.get(player);


    @Override
    public boolean keyDown(int i) {
        if(keyBinds.isEmpty()){
            getKeyBinds();
        }
        PlayerAction action = keyBinds.get(i);
        if (action != null) {
            switch (i)
                {
                case Input.Keys.W:
                    inputComponent.setUpPressed(true);
                    break;
                case Input.Keys.D:
                    inputComponent.setRightPressed(true);
                    break;
                case Input.Keys.A:
                    inputComponent.setLeftPressed(true);
                    break;
                case Input.Keys.S:
                    inputComponent.setDownPressed(true);
                    break;
                }
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        PlayerAction action = keyBinds.get(i);
        if (action != null) {
            switch (i)
            {
                case Input.Keys.W:
                    inputComponent.setUpPressed(false);
                    break;
                case Input.Keys.D:
                    inputComponent.setRightPressed(false);
                    break;
                case Input.Keys.A:
                    inputComponent.setLeftPressed(false);
                    break;
                case Input.Keys.S:
                    inputComponent.setDownPressed(false);
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    private Map<Integer, PlayerAction> getKeyBinds() {
        KeyUtils.init();
        FileHandle file;
//        Check for user file, if not, use default
        if (Gdx.files.local(GameResources.usrKeyMapLoc).exists()) {
            file = Gdx.files.local(GameResources.usrKeyMapLoc);
        } else {
            file = Gdx.files.internal("default-keymap.json");
        }

//        translate from json to class
        Json json = new Json();
        Bindings bindings = json.fromJson(Bindings.class, file);

//        Empty keyBinds before mapping
        keyBinds.clear();
//        fill hashmap with keycode-player_action pairs
        for (Binding binding : bindings.keyBinds) {
//            Translate key string to key code
            int keyCode = KeyUtils.getKeyCode(binding.key);

//            Match action to enum
            PlayerAction keyAction = null;
            for (PlayerAction action : PlayerAction.values()) {
                if (action.name().equalsIgnoreCase(binding.action)) {
                    keyAction = action;
                    break;
                }
            }
//            input into table
            keyBinds.put(keyCode, keyAction);
        }
        return keyBinds;
    }

    public void dispose() {
        keyBinds.clear();
//        inputComponent.
    }
}
