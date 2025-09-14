package io.github.ferretFeet72.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.ferretFeet72.utils.GameResources;


public class BaseScreen implements Screen {
    protected Stage stage;

    public BaseScreen(Viewport viewport) {
        this.stage = new Stage(viewport);
        GameResources.viewport = viewport;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
//        clear screen
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        call stage methods?
        stage.act(v);
        stage.draw();
//        act, draw

    }

    @Override
    public void resize(int w, int h) {
//        getViewport().update(w, h, true);
        GameResources.viewport.update(w, h, true);
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
        stage.dispose();
    }
}
