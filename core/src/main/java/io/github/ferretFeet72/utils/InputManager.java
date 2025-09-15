package io.github.ferretFeet72.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import io.github.ferretFeet72.components.InputComponent;
import io.github.ferretFeet72.components.PlayerComponent;
import io.github.ferretFeet72.utils.GameResources;
import io.github.ferretFeet72.utils.KeyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

enum PlayerAction {
    MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT
}

class Binding {
    public String key;
    public String action;
    public Binding(String key, String value) {
        this.key = key;
        this.action = value;
    }
    public Binding() {}
}

class Bindings {
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
                    inputComponent.upPressed = true;
                    break;
                case Input.Keys.D:
                    inputComponent.rightPressed = true;
                    break;
                case Input.Keys.A:
                    inputComponent.leftPressed = true;
                    break;
                case Input.Keys.S:
                    inputComponent.downPressed = true;
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
                    inputComponent.upPressed = false;
                    break;
                case Input.Keys.D:
                    inputComponent.rightPressed = false;
                    break;
                case Input.Keys.A:
                    inputComponent.leftPressed = false;
                    break;
                case Input.Keys.S:
                    inputComponent.downPressed = false;
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
        Json json = new Json();
        System.out.println("INPUT MANAGER JSON" + file.readString());

//        JsonValue jsonFile = json.fromJson(null, file);



        Bindings bindings = json.fromJson(Bindings.class, file);
        System.out.println("INPUT MANAGER BINDINGS" + bindings.keyBinds);

        keyBinds.clear();
        for (Binding binding : bindings.keyBinds) {
            int keyCode = KeyUtils.getKeyCode(binding.key);
            PlayerAction keyAction = null;
            for (PlayerAction action : PlayerAction.values()) {
                if (action.name().equalsIgnoreCase(binding.action)) {
                    keyAction = action;
                    break;
                }
            }
            keyBinds.put(keyCode, keyAction);
        }


        return keyBinds;
    }

    public void dispose() {
        keyBinds.clear();
//        inputComponent.
    }
}
