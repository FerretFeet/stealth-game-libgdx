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
        PositionComponent pos = pm.get(entity);
        CollisionComponent col = cm.get(entity);
        SizeComponent size = sm.get(entity);
        VelocityComponent vel = vm.get(entity);

        // Check collisions against other entities
        for (Entity other : getEngine().getEntitiesFor(Family.all(PositionComponent.class, CollisionComponent.class).get())) {
            if (other == entity) continue; //skip self
            CollisionComponent otherCol = cm.get(other);
            if (!otherCol.isCollisionEnabled()) continue; //skip collison disabled

            PositionComponent otherPos = ComponentMapper.getFor(PositionComponent.class).get(other);
            SizeComponent otherSize = ComponentMapper.getFor(SizeComponent.class).get(other);


            if (checkCollision(pos, size, otherPos, otherSize)) {
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
                if (other.getComponent(DoorComponent.class) != null) {
                    DoorComponent door = other.getComponent(DoorComponent.class);
                    TiledMapTileLayer closedDoorLayer = (TiledMapTileLayer) GameResources.map.getLayers().get("closedDoors");
                    TiledMapTile openDoorTile = GameResources.map.getTileSets().getTile(door.openTileId);
                    System.out.println(openDoorTile.getId() + " " + GameResources.map.getTileSets().getTile(door.closedTileId).getId());
                    closedDoorLayer.getCell(door.tileX, door.tileY).setTile(openDoorTile);
                    System.out.println(closedDoorLayer.getCell(door.tileX, door.tileY).getTile().getId());
                    other.remove(CollisionComponent.class);

                    System.out.println("Door opened and collision removed!");
                }
            }
        }
    }

    private boolean checkCollision(PositionComponent aPos, SizeComponent aSize,
                                   PositionComponent bPos, SizeComponent bSize) {
        // Simple AABB (axis-aligned bounding box) collision
        return aPos.getX() < bPos.getX() + bSize.getX() &&
            aPos.getX() + aSize.getX() > bPos.getX() &&
            aPos.getY() < bPos.getY() + bSize.getY() &&
            aPos.getY() + aSize.getY() > bPos.getY();
    }
}
