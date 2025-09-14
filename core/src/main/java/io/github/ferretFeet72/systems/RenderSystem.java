package io.github.ferretFeet72.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;
import io.github.ferretFeet72.components.PositionComponent;
import io.github.ferretFeet72.components.SizeComponent;
import io.github.ferretFeet72.components.TextureComponent;
import io.github.ferretFeet72.utils.GameResources;

public class RenderSystem extends IteratingSystem {
    Batch batch;
    private ComponentMapper<TextureComponent> tm;
    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<SizeComponent> sm;


    public RenderSystem() {
        super(Family.all(PositionComponent.class, TextureComponent.class, SizeComponent.class).get());
        this.pm = ComponentMapper.getFor(PositionComponent.class);
        this.tm = ComponentMapper.getFor(TextureComponent.class);
        this.sm = ComponentMapper.getFor(SizeComponent.class);

        this.batch = GameResources.batch;
    }
    @Override
    public void update(float deltaTime) {
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        PositionComponent pos = pm.get(entity);
        TextureComponent texture = tm.get(entity);
        SizeComponent size = sm.get(entity);
        batch.draw(texture.region, pos.x, pos.y, size.x, size.y);
    }
}
