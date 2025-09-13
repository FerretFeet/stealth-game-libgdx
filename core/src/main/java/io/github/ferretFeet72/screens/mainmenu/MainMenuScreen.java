package io.github.ferretFeet72.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.ferretFeet72.screens.BaseScreen;

import java.awt.*;

public class MainMenuScreen extends BaseScreen {
    private Skin skin;
    private Stage stage;


    public MainMenuScreen() {
        super(new ScreenViewport());
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new BaseInputProcessor());


        skin = new Skin(Gdx.files.internal("skin/metal-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton testButton = new TextButton("Test", skin);
        table.add(testButton);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.YELLOW.getBlue(), Color.YELLOW.getAlpha());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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
