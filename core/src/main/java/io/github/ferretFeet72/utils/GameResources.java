package io.github.ferretFeet72.utils;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameResources {
    public static OrthographicCamera camera;
    public static Viewport viewport;
    public static SpriteBatch batch;
    public static AssetManager assets;
    public static String usrSettingsLoc = "saved-settings.json";
    public static String usrKeyMapLoc = "config/keymap.json";
    public static Engine engine;

    public static void init() {
        camera = new OrthographicCamera();
//        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        assets = new AssetManager();
        engine = new Engine();
//        settingsLoc = "saved-settings.json";

    }


    public static void dispose() {
        batch.dispose();
        assets.dispose();
    }
}

