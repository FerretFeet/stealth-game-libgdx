package io.github.ferretFeet72.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.ferretFeet72.components.CollisionComponent;
import io.github.ferretFeet72.components.DoorComponent;
import io.github.ferretFeet72.components.InteractionEventComponent;
import io.github.ferretFeet72.utils.GameResources;

public class DoorSystem extends IteratingSystem {
    private final ComponentMapper<CollisionComponent> cm = ComponentMapper.getFor(CollisionComponent.class);
    public DoorSystem() {
        super(Family.all(DoorComponent.class, InteractionEventComponent.class).get());
    }

    private void toggleDoor(Entity other) {
        DoorComponent door = other.getComponent(DoorComponent.class);

        if (door.doorLock) return; //cannot toggle if locked

//            get door layer
        TiledMapTileLayer closedDoorLayer = (TiledMapTileLayer) GameResources.map.getLayers().get("closedDoors");

        TiledMapTile openDoorTile = GameResources.map.getTileSets().getTile(door.openTileId);
        TiledMapTile closedDoorTile = GameResources.map.getTileSets().getTile(door.closedTileId);

        TiledMapTile curDoorTileID = closedDoorLayer.getCell(door.tileX, door.tileY).getTile();
        if (curDoorTileID == closedDoorTile) {
            closedDoorLayer.getCell(door.tileX, door.tileY).setTile(openDoorTile);
            door.doorOpen = true;
            other.remove(CollisionComponent.class);
        } else if (curDoorTileID == openDoorTile) {
            closedDoorLayer.getCell(door.tileX, door.tileY).setTile(closedDoorTile);
            door.doorOpen = false;
            other.add(new CollisionComponent(true));
        }
        other.remove(InteractionEventComponent.class);
        System.out.println("Door opened and collision removed!");
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        toggleDoor(entity);

    }
}
