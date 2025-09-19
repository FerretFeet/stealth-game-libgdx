package io.github.ferretFeet72.screens.game.levels;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.ferretFeet72.components.CollisionComponent;
import io.github.ferretFeet72.components.DoorComponent;
import io.github.ferretFeet72.components.PositionComponent;
import io.github.ferretFeet72.components.SizeComponent;
import io.github.ferretFeet72.entities.PlayerFactory;
import io.github.ferretFeet72.systems.CollisionSystem;
import io.github.ferretFeet72.systems.MovementSystem;
import io.github.ferretFeet72.systems.PlayerControlSystem;
import io.github.ferretFeet72.systems.RenderSystem;
import io.github.ferretFeet72.utils.GameResources;
import io.github.ferretFeet72.utils.keyBindings.InputManager;

import java.nio.file.Path;

public class LevelOne implements Screen {
    private Game game;
    private Engine engine = GameResources.engine;


    private OrthographicCamera camera;
    private Viewport viewport;

    private TmxMapLoader tmxMapLoader = new TmxMapLoader();
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Entity player;

    int tileWidth, tileHeight;
    int mapWidthInPixels, mapHeightInPixels;


    public LevelOne(Game game) {
//        initialize vars
        this.game = game;
//        this.stage = new Stage(viewport);

        map = tmxMapLoader.load("crawl-tiles/level1.tmx");
        GameResources.map = map;
        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);

        mapWidthInPixels = map.getProperties().get("width", Integer.class) * tileWidth;
        mapHeightInPixels = map.getProperties().get("height", Integer.class) * tileHeight;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, mapWidthInPixels, mapHeightInPixels);
        viewport = new FitViewport(mapWidthInPixels, mapHeightInPixels, camera);
//        viewport = new ScreenViewport(camera);
//        ScreenViewport screenViewport = (ScreenViewport) viewport;
//        screenViewport.setUnitsPerPixel((float) 1/tileWidth);
//        screenViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        GameResources.viewport = screenViewport;
        GameResources.viewport = viewport;
//        screenViewport.apply();
        viewport.apply();
        GameResources.batch.setProjectionMatrix(camera.combined);


//        set invisible layers not visible
        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().equals("collision") ||
                layer.getName().equals("openDoors"))
            {
                layer.setVisible(false);
                System.out.println("layer invisible");
            }
        }

        //        center camera
        camera.position.set(
            map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class) / 2f,
            map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class) / 2f,
            0
        );
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map);

//        add player
        player = PlayerFactory.create();
        engine.addEntity(player);

//        generate entities
        generateCollisionEntities(map);

//        set inputs
        Gdx.input.setInputProcessor(new InputManager());


//        add systems
        engine.addSystem(new RenderSystem());
        engine.addSystem(new PlayerControlSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new CollisionSystem());


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        camera.update();
        renderer.setView(camera);
        renderer.render();

        engine.update(v);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(mapWidthInPixels / 2f, mapHeightInPixels / 2f, 0);
        camera.update();
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


    public void generateCollisionEntities(TiledMap map) {
        MapLayer tempClosed = map.getLayers().get("closedDoors");
        MapLayer tempOpen = map.getLayers().get("openDoors");
        System.out.println(tempOpen.getName());
        TiledMapTileLayer closedDoorLayer = (TiledMapTileLayer) tempClosed;
        TiledMapTileLayer openDoorLayer = (TiledMapTileLayer) tempOpen;
        System.out.println(openDoorLayer.getName() + openDoorLayer.getWidth());
        MapLayer collisionLayerTemp = map.getLayers().get("collision");

        int tileWidth = map.getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getProperties().get("tileheight", Integer.class);

//        if(collisionLayerTemp instanceof TiledMapTileLayer) {
            System.out.println("collision layer instance");
            TiledMapTileLayer collisionLayer = (TiledMapTileLayer) collisionLayerTemp;
//        }
        for (int i = 0; i < collisionLayer.getWidth(); i++) {
            for (int j = 0; j < collisionLayer.getHeight(); j++) {
                TiledMapTileLayer.Cell cell = collisionLayer.getCell(i, j);
                TiledMapTileLayer.Cell openDoorCell = (openDoorLayer != null) ? openDoorLayer.getCell(i, j) : null;
                TiledMapTileLayer.Cell closedDoorCell = (closedDoorLayer != null) ? closedDoorLayer.getCell(i, j) : null;
                System.out.println(openDoorCell == null);

                if (cell == null) {
                    continue;
                }
                System.out.println("adding entity");
                Entity entity = new Entity();
                entity.add(new CollisionComponent(true));
                entity.add(new PositionComponent(i*tileWidth, j*tileHeight, 0));
                entity.add(new SizeComponent(tileWidth, tileHeight, 0));

//                check if door or other type of collision object
                if (openDoorCell != null && closedDoorCell != null) {
                    int openTileID = openDoorCell.getTile().getId();
                    int closedTileID = closedDoorCell.getTile().getId();
                    entity.add(new DoorComponent(closedTileID, openTileID, i, j));
                }

                engine.addEntity(entity);
            }
        }
    }
}

