package io.github.ferretFeet72.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public enum ScreenEnum {
    MAIN_MENU {
        public Screen getScreen(Object... params) {
            return new MainMenuScreen();
        }
    },

    LEVEL_SELECT {
        public Screen getScreen(Object... params) {
            return new LevelSelectScreen();
        }
    },

    GAME {
        public Screen getScreen(Object... params) {
            return new GameScreen();
        }
    };

    public abstract Screen getScreen(Object... params);
}
