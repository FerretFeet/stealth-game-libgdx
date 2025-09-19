package io.github.ferretFeet72.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import io.github.ferretFeet72.screens.game.GameScreen;
import io.github.ferretFeet72.screens.game.levels.LevelOne;
import io.github.ferretFeet72.screens.mainmenu.MainMenuScreen;

public enum ScreenEnum {
    MAIN_MENU {
        public Screen getScreen(Object... params) {
            return new MainMenuScreen((Game) params[0]);
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
    },

    LEVEL_ONE {
        public Screen getScreen(Object... params) {
            return new LevelOne((Game) params[0]);
        }
    };

    public abstract Screen getScreen(Object... params);
}
