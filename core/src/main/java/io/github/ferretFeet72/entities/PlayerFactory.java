package io.github.ferretFeet72.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import io.github.ferretFeet72.components.*;

public class PlayerFactory {
    public static Entity create(Engine engine)  {
        Entity player = new Entity();
        player.add(new PlayerComponent());
        player.add(new InputComponent());
        player.add(new SpeedComponent(5));
        player.add(new PositionComponent(50, 50, 1));
        player.add(new SizeComponent(50, 50, 1));
        player.add(new VelocityComponent(0, 0, 0));
        player.add(new CollisionComponent(true));
        Texture texture = new Texture(Gdx.files.internal("crawl-tiles/player/base/centaur_brown_f.png"));
        player.add(new TextureComponent(texture));

        System.out.println("=================PLAYER FACTORY =============================");
        System.out.println(texture.toString());
        engine.addEntity(player);
        return player;
    }

}

