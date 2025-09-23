package io.github.ferretFeet72.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.ferretFeet72.components.*;

public class ShootingSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<ShotTargetComponent> sm = ComponentMapper.getFor(ShotTargetComponent.class);



    public ShootingSystem() {
        super(Family.all(ShotTargetComponent.class, PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        System.out.println("Pew pew");
        ShotTargetComponent shot = sm.get(entity);
        PositionComponent position = pm.get(entity);

//        create entity
        Entity bullet = new Entity();
        int bulletWidth, bulletHeight;
        bulletWidth = 12;
        bulletHeight = 4;
        float topSpeed = 100f;
        Texture texture = new Texture(Gdx.files.internal("crawl-tiles/dc-misc/ray.png"));
        TextureRegion bulletTexture = new TextureRegion(texture, bulletWidth, bulletHeight);
//        TextureRegion bulletTexture = new TextureRegion(texture);
        bullet.add(new PositionComponent(position.getX(), position.getY(), position.getZ()));
        bullet.add(new SizeComponent(bulletWidth, bulletHeight, 0));
        bullet.add(new TextureComponent(bulletTexture));
        bullet.add(new CollisionComponent(true));
        bullet.add(new DirectionComponent());
        bullet.add(new SpeedComponent(topSpeed,0f));
        bullet.add(new ShooterComponent(entity));
//        calculate velocity based off of current pos  and target pos
       Vector2 pos = new Vector2(position.getX(), position.getY());
       Vector2 target = new Vector2(shot.getX(), shot.getY());
       float rise = target.y - pos.y;
       float run  = target.x - pos.x;
       System.out.println(target.x + " "+ target.y);
       System.out.println(pos.x + " "+ pos.y);
       Vector2 vec = new Vector2(run, rise).nor().scl(topSpeed);
       bullet.add(new VelocityComponent(vec.x, vec.y, 0));
//        bullet.add(new VelocityComponent(0f, 5f, 0));
//       Vector2 displacement = target.cpy().sub(pos);
//       Vector2 velocity = displacement.scl(1.0f / v);
//        bullet.add(new VelocityComponent(velocity.x, velocity.y, 0));
//        Clamp to top speed

System.out.println(vec.x + " " + vec.y);
        this.getEngine().addEntity(bullet);

        entity.remove(ShotTargetComponent.class);
    }
}
