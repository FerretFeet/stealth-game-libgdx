package io.github.ferretFeet72.utils.keyBindings;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import io.github.ferretFeet72.components.InputComponent;
import io.github.ferretFeet72.components.PlayerComponent;
import io.github.ferretFeet72.utils.GameResources;

import java.util.HashMap;
import java.util.Map;

import static io.github.ferretFeet72.utils.keyBindings.PlayerActions.MOVE_UP;


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
            switch (action)
                {
                case PlayerActions.MOVE_UP:
                    inputComponent.setUpPressed(true);
                    break;
                case PlayerActions.MOVE_RIGHT:
                    inputComponent.setRightPressed(true);
                    break;
                case PlayerActions.MOVE_LEFT:
                    inputComponent.setLeftPressed(true);
                    break;
                case PlayerActions.MOVE_DOWN:
                    inputComponent.setDownPressed(true);
                    break;
                case PlayerActions.INTERACT:
                    System.out.println("Interact Pressed");
                    inputComponent.setInteractPressed(true);
                    break;
                }
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        PlayerActions action = keyBinds.get(i);
        if (action != null) {
            switch (action)
            {
                case MOVE_UP:
                    inputComponent.setUpPressed(false);
                    break;
                case MOVE_RIGHT:
                    inputComponent.setRightPressed(false);
                    break;
                case MOVE_LEFT:
                    inputComponent.setLeftPressed(false);
                    break;
                case MOVE_DOWN:
                    inputComponent.setDownPressed(false);
                    break;
                case INTERACT:
                    inputComponent.setInteractPressed(false);
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
