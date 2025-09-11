package io.github.ferretFeet72.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.ferretFeet72.screens.BaseScreen;

import java.awt.*;

public class MainMenuScreen extends BaseScreen {
    public MainMenuScreen() {
        super(new ScreenViewport());
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new BaseInputProcessor());
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.YELLOW.getBlue(), Color.YELLOW.getAlpha());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int i, int i1) {

    }

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

    }
}
