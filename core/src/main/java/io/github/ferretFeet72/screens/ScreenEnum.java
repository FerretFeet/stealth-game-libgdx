package io.github.ferretFeet72.screens;

import com.badlogic.gdx.Screen;
import io.github.ferretFeet72.screens.game.GameScreen;
import io.github.ferretFeet72.screens.mainmenu.MainMenuScreen;

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
