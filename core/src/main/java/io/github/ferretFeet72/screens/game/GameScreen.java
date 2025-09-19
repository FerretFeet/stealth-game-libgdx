package io.github.ferretFeet72.screens.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.ferretFeet72.components.PositionComponent;
import io.github.ferretFeet72.entities.PlayerFactory;
import io.github.ferretFeet72.screens.BaseScreen;
import io.github.ferretFeet72.systems.MovementSystem;
import io.github.ferretFeet72.systems.PlayerControlSystem;
import io.github.ferretFeet72.systems.RenderSystem;
import io.github.ferretFeet72.utils.GameResources;
import io.github.ferretFeet72.utils.InputManager;

import java.awt.*;

public class GameScreen extends BaseScreen {
    private Entity player;
    private Engine engine = GameResources.engine;
    public GameScreen() {
        super(new ScreenViewport());
        engine.addSystem(new RenderSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerControlSystem());
    }


    @Override
    public void show() {
        player = PlayerFactory.create();
        engine.addEntity(player);
        Gdx.input.setInputProcessor(new InputManager());

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(), Color.GREEN.getAlpha());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GameResources.viewport.apply();
        GameResources.batch.setProjectionMatrix(GameResources.viewport.getCamera().combined);

        engine.update(v);
        PositionComponent pos = (PositionComponent) player.getComponent(PositionComponent.class);

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
