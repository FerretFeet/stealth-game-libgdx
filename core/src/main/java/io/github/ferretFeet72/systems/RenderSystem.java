package io.github.ferretFeet72.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;
import io.github.ferretFeet72.components.DirectionComponent;
import io.github.ferretFeet72.components.PositionComponent;
import io.github.ferretFeet72.components.SizeComponent;
import io.github.ferretFeet72.components.TextureComponent;
import io.github.ferretFeet72.utils.DirectionHelper;
import io.github.ferretFeet72.utils.GameResources;

public class RenderSystem extends IteratingSystem {
    Batch batch;
    private ComponentMapper<TextureComponent> tm;
    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<SizeComponent> sm;
    private ComponentMapper<DirectionComponent> dm;


    public RenderSystem() {
//        Apply to all entities with position, texture, and size.
        super(Family.all(PositionComponent.class, TextureComponent.class, SizeComponent.class).get());
//        get list of entities with components
        this.pm = ComponentMapper.getFor(PositionComponent.class);
        this.tm = ComponentMapper.getFor(TextureComponent.class);
        this.sm = ComponentMapper.getFor(SizeComponent.class);
        this.dm = ComponentMapper.getFor(DirectionComponent.class);


        this.batch = GameResources.batch;
    }
    @Override
    public void update(float deltaTime) {
//        draw batch. batch should be called  once per frame.
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
//        get entity values
        PositionComponent pos = pm.get(entity);
        TextureComponent texture = tm.get(entity);
        SizeComponent size = sm.get(entity);
        DirectionComponent dir = dm.get(entity);

        float drawWidth = size.getX(); // Default width
        float drawHeight = size.getY();
        float drawX = pos.getX(); // Default X position
        float drawY = pos.getY(); // Default Y position

        if (dir != null && dir.getDirection() == DirectionHelper.Direction.RIGHT) {
//          if facing right, mirror horizontally
            drawWidth = -size.getX(); //negative width flips image
//            adjust X to keep image in original location
            drawX = pos.getX() + size.getX();
        }

//        draw
        batch.draw(texture.getRegion(), drawX, drawY, drawWidth, drawHeight);    }
}
