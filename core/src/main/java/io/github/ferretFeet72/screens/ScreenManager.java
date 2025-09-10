package io.github.ferretFeet72.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.ferretFeet72.utils.fontManager;

public class ScreenManager {
//    singleton
    private static ScreenManager instance;
    private Game game;


    private ScreenManager() {
        super();
        instance = this;
    }

    public static ScreenManager getInstance() {
        if(instance == null)
            instance = new ScreenManager();
        return instance;
    }

//    initialize with Game class
    public void init(Game game) {
        this.game = game;
    }

    public void showScreen(ScreenEnum screenEnum, Object... params) {
//        get current screen to dispose
        Screen currentScreen = game.getScreen();

//        Show new screen
        Screen newScreen = screenEnum.getScreen(params);
//        initalize new screen?
//        newScreen.stage.buildStage
        game.setScreen(newScreen);

//        dispose of previous screen
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }
}
