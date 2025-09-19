package io.github.ferretFeet72.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.ferretFeet72.components.*;
import io.github.ferretFeet72.utils.GameResources;

public class CollisionSystem  extends IteratingSystem {
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<CollisionComponent> cm = ComponentMapper.getFor(CollisionComponent.class);
    private final ComponentMapper<SizeComponent> sm = ComponentMapper.getFor(SizeComponent.class);
    private final  ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public CollisionSystem() {
        super(Family.all(PositionComponent.class, CollisionComponent.class, SizeComponent.class, VelocityComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        resolveOutOfBounds(entity);

        // Check collisions against other entities
        for (Entity other : getEngine().getEntitiesFor(Family.all(PositionComponent.class, CollisionComponent.class).get())) {
//            Skips
            if (other == entity) continue; //skip self
            CollisionComponent otherCol = cm.get(other);
            if (!otherCol.isCollisionEnabled()) continue; //skip collison disabled

//            Handle collision
            if (!checkCollision(entity, other)) continue; //if no collision
            resolveCollision(entity, other); //handle position change

            otherCol.setColliding(true);
            if (other.getComponent(DoorComponent.class) != null) {
                toggleDoor(other);
            }
//            can theoretically only collide with one object at a time
//            if reaches this piece of code, should have collided
//            break;

        }
    }

    private void resolveCollision(Entity obj, Entity other) {
        PositionComponent pos = pm.get(obj);
        SizeComponent size = sm.get(obj);
        VelocityComponent vel = vm.get(obj);

        PositionComponent otherPos =  pm.get(other);
        SizeComponent otherSize = sm.get(other);
        float xOverlap = Math.min(pos.getX() + size.getX(), otherPos.getX() + otherSize.getX()) - Math.max(pos.getX(), otherPos.getX());
        float yOverlap = Math.min(pos.getY() + size.getY(), otherPos.getY() + otherSize.getY()) - Math.max(pos.getY(), otherPos.getY());

        if (Math.abs(xOverlap) < Math.abs(yOverlap)) {
            // X-axis collision is smaller, so push player out on the X-axis
            if (pos.getX() < otherPos.getX()) {
                pos.setX(pos.getX() - xOverlap); // Move left
            } else {
                pos.setX(pos.getX() + xOverlap); // Move right
            }
            vel.setDx(0);
        } else {
            // Y-axis collision is smaller, so push player out on the Y-axis
            if (pos.getY() < otherPos.getY()) {
                pos.setY(pos.getY() - yOverlap); // Move down
            } else {
                pos.setY(pos.getY() + yOverlap); // Move up
            }
            vel.setDy(0);
        }
    }

    private void toggleDoor(Entity other) {
            DoorComponent door = other.getComponent(DoorComponent.class);
//            get door layer
            TiledMapTileLayer closedDoorLayer = (TiledMapTileLayer) GameResources.map.getLayers().get("closedDoors");

            TiledMapTile openDoorTile = GameResources.map.getTileSets().getTile(door.openTileId);
            TiledMapTile closedDoorTile = GameResources.map.getTileSets().getTile(door.closedTileId);

            TiledMapTile curDoorTileID = closedDoorLayer.getCell(door.tileX, door.tileY).getTile();
            if (curDoorTileID == closedDoorTile) {
                closedDoorLayer.getCell(door.tileX, door.tileY).setTile(openDoorTile);
            } else if (curDoorTileID == openDoorTile) {
                closedDoorLayer.getCell(door.tileX, door.tileY).setTile(closedDoorTile);
            }
            other.remove(CollisionComponent.class);

            System.out.println("Door opened and collision removed!");
        }


    private void resolveOutOfBounds(Entity entity) {
        PositionComponent entityPos = pm.get(entity);
        SizeComponent entitySize = sm.get(entity);

        // Get map boundaries from GameResources
        int mapWidth = GameResources.map.getProperties().get("width", Integer.class) * GameResources.map.getProperties().get("tilewidth", Integer.class);
        int mapHeight = GameResources.map.getProperties().get("height", Integer.class) * GameResources.map.getProperties().get("tileheight", Integer.class);

        // Check for out-of-bounds collision on the X-axis
        if (entityPos.getX() < 0) {
            entityPos.setX(0); // Snap to the left edge
        } else if (entityPos.getX() + entitySize.getX() > mapWidth) {
            entityPos.setX(mapWidth - entitySize.getX()); // Snap to the right edge
        }

        // Check for out-of-bounds collision on the Y-axis
        if (entityPos.getY() < 0) {
            entityPos.setY(0); // Snap to the bottom edge
        } else if (entityPos.getY() + entitySize.getY() > mapHeight) {
            entityPos.setY(mapHeight - entitySize.getY()); // Snap to the top edge
        }

    }
    private boolean checkCollision(Entity obj, Entity other) {
        PositionComponent aPos = pm.get(obj);
        SizeComponent aSize = sm.get(obj);

        PositionComponent bPos =  pm.get(other);
        SizeComponent bSize = sm.get(other);
        // Simple AABB (axis-aligned bounding box) collision
        return aPos.getX() < bPos.getX() + bSize.getX() &&
            aPos.getX() + aSize.getX() > bPos.getX() &&
            aPos.getY() < bPos.getY() + bSize.getY() &&
            aPos.getY() + aSize.getY() > bPos.getY();
    }
}
