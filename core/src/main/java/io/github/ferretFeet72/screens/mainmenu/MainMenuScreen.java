package io.github.ferretFeet72.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.ferretFeet72.screens.BaseScreen;
import io.github.ferretFeet72.screens.ScreenEnum;
import io.github.ferretFeet72.screens.ScreenManager;
import io.github.ferretFeet72.ui.MainMenuUI;
import io.github.ferretFeet72.ui.SettingsUI;

public class MainMenuScreen extends BaseScreen implements MainMenuUI.MainMenuListener{
    private Skin skin;
    private MainMenuUI mainMenuUI;
    private SettingsUI settingsUI;

    public MainMenuScreen() {
        super(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("skin/metal-ui.json"));
        this.mainMenuUI = new MainMenuUI(skin);
        this.settingsUI = new SettingsUI(skin);

    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(new BaseInputProcessor());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(mainMenuUI.getRootActor());

        mainMenuUI.setListener(this);

    }

//    @Override
//    public void render(float v) {
//
//    }

//    @Override
//    public void resize(int i, int i1) {
//
//    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void onStartGameClicked() {
        ScreenManager.getInstance().showScreen(ScreenEnum.GAME);
    }

    @Override
    public void onSettingsClicked() {
        this.settingsUI =  new SettingsUI(skin);
        stage.addActor(settingsUI.getRootActor());
        settingsUI.setVisibility(true);
    }
}
