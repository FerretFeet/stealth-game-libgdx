package io.github.ferretFeet72.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.ferretFeet72.components.*;

public class PlayerFactory {
    public static Entity create(Engine engine)  {
        Texture texture = new Texture(Gdx.files.internal("crawl-tiles/player/base/centaur_brown_f.png"));
        TextureRegion playerTexture = new TextureRegion(texture);

        Entity player = new Entity();
        player.add(new PlayerComponent());
        player.add(new InputComponent());
        player.add(new SpeedComponent(10f, 0f));
        player.add(new PositionComponent(50, 50, 1));
        player.add(new SizeComponent(50, 50, 1));
        player.add(new AccelerationComponent(1f, 1f, 0f));
        player.add(new VelocityComponent(0, 0, 0));
        player.add(new CollisionComponent(true));
        player.add(new TextureComponent(playerTexture));

        engine.addEntity(player);

        return player;
    }

}

