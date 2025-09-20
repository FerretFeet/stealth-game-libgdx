package io.github.ferretFeet72.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import io.github.ferretFeet72.components.CollisionComponent;
import io.github.ferretFeet72.components.DirectionComponent;
import io.github.ferretFeet72.components.PositionComponent;
import io.github.ferretFeet72.components.VelocityComponent;
import io.github.ferretFeet72.utils.DirectionHelper;

import static io.github.ferretFeet72.utils.DirectionHelper.getDirection;

public class MovementSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<VelocityComponent> tm = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<DirectionComponent> dm = ComponentMapper.getFor(DirectionComponent.class);

    public MovementSystem() {
//        run on all entities with position, velocity
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }




    @Override
    protected void processEntity(Entity entity, float v) {
        PositionComponent pos = pm.get(entity);
        VelocityComponent vel = tm.get(entity);
        DirectionComponent dir = dm.get(entity);

        pos.setX(pos.getX() + vel.getDx() * v);
        pos.setY(pos.getY() + vel.getDy() * v);
        Vector2 dirVec = new Vector2(vel.getDx(), vel.getDy());

        DirectionHelper.Direction direction = getDirection(dirVec);
        dir.setDirection(direction);
    }


}
