package io.github.ferretFeet72.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input.Keys;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BaseInputProcessor implements InputProcessor {
    private static final String TAG = BaseInputProcessor.class.getSimpleName();
    private final Map<Integer, Runnable> keyActions = new HashMap<>();
    private final Map<String, Integer> actionKeyMap = new HashMap<>();
    private final Preferences preferences = Gdx.app.getPreferences("game.prefs");

    BaseInputProcessor() {
        setDefaultActions();
        loadBindings();
    }

    private void setDefaultActions() {
        // Clear existing mappings
        keyActions.clear();
        actionKeyMap.clear();

        // Initialize default key bindings with a descriptive action name
        setKeyAction(Keys.W, "move_up", () -> Gdx.app.log(TAG, "W key was pressed! (Move Up)"));
        setKeyAction(Keys.A, "move_left", () -> Gdx.app.log(TAG, "A key was pressed! (Move Left)"));
        setKeyAction(Keys.S, "move_down", () -> Gdx.app.log(TAG, "S key was pressed! (Move Down)"));
        setKeyAction(Keys.D, "move_right", () -> Gdx.app.log(TAG, "D key was pressed! (Move Right)"));
    }

    private void setKeyAction(int keycode, String actionName, Runnable action) {
        if (action != null) {
            keyActions.put(keycode, action);
            actionKeyMap.put(actionName, keycode);
        } else {
            keyActions.remove(keycode);
            actionKeyMap.remove(actionName);
        }
    }

    public void loadBindings() {
        Gdx.app.log(TAG, "Loading key bindings from preferences...");

        // Get all saved actions
        Map<String, ?> savedActions = preferences.get().entrySet().stream()
            .filter(entry -> !entry.getKey().startsWith("gamelog.")) // Exclude log data
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Apply the saved bindings, but keep default actions for unsaved ones
        for (Map.Entry<String, ?> entry : savedActions.entrySet()) {
            String actionName = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Integer) {
                int keycode = (Integer) value;
                if (actionKeyMap.containsKey(actionName)) {
                    // Update the key for the existing action
                    int oldKey = actionKeyMap.get(actionName);
                    Runnable oldAction = keyActions.get(oldKey);
                    keyActions.remove(oldKey);
                    keyActions.put(keycode, oldAction);
                    actionKeyMap.put(actionName, keycode);
                }
            }
        }
    }

    public void saveBindings() {
        Gdx.app.log(TAG, "Saving key bindings to preferences...");
        for (Map.Entry<String, Integer> entry : actionKeyMap.entrySet()) {
            preferences.putInteger(entry.getKey(), entry.getValue());
        }
        preferences.flush(); // This writes the changes to the file
        Gdx.app.log(TAG, "Key bindings saved.");
    }

    @Override
    public boolean keyDown(int keycode) {
        // Look up the keycode in map and execute the corresponding action.
        Runnable action = keyActions.get(keycode);
        if (action != null) {
            action.run();
            // Return true to indicate the event was handled.
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
