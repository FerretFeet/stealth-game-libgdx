package io.github.ferretFeet72.screens.game.levels;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public abstract class Level {
    TiledMap map;
    private List<Entity> enemies;
    private List<Entity> items;
    private Vector2 playerSpawn;

    public Level(TiledMap map) {
        this.map = map;
        this.enemies = new ArrayList<>();
        this.items = new ArrayList<>();
//        this.playerSpawn = playerSpawn;
    }

    public void spawnEntities(Engine engine) {
        for (Entity e : enemies) {
            engine.addEntity(e);
        }
        for (Entity i : items) {
            engine.addEntity(i);
        }
    }

    public Vector2 getPlayerSpawn() {
        return playerSpawn;
    }

    public abstract void generateItems();

    public abstract void generateEnemies();

}
