package io.github.ferretFeet72;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.ferretFeet72.screens.ScreenEnum;
import io.github.ferretFeet72.screens.ScreenManager;
import io.github.ferretFeet72.utils.GameResources;
import io.github.ferretFeet72.utils.fontManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    ScreenManager screenManager = ScreenManager.getInstance();

    private BitmapFont font;
    private OrthographicCamera camera;
    private Viewport viewport;


    @Override
    public void create() {
//        Initialize resources
        GameResources.init();
        screenManager.init(this);



        screenManager.showScreen(ScreenEnum.MAIN_MENU);


    }
}
