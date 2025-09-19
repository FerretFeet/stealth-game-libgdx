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


public class InputManager implements InputProcessor {
    private Map<Integer, PlayerActions> keyBinds = new HashMap<>();
    Entity player = GameResources.engine.getEntitiesFor(Family.all(PlayerComponent.class, InputComponent.class).get()).first();
    ComponentMapper<InputComponent> im = ComponentMapper.getFor(InputComponent.class);
    InputComponent inputComponent = im.get(player);


    @Override
    public boolean keyDown(int i) {
        if(keyBinds.isEmpty()){
            keyBinds = KeyBindingsLoader.loadKeyBinds(keyBinds);
        }
        PlayerActions action = keyBinds.get(i);
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
        PlayerActions action = keyBinds.get(i);
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



    public void dispose() {
        keyBinds.clear();
//        inputComponent.
    }
}
