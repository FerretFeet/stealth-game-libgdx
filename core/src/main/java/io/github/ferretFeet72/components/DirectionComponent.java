package io.github.ferretFeet72.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.ashley.core.Component;
import io.github.ferretFeet72.utils.DirectionHelper;


public class DirectionComponent implements Component {
    DirectionHelper.Direction direction;
    public DirectionComponent(DirectionHelper.Direction direction) {
        this.direction = direction;
    }
    public DirectionComponent() {
        this.direction = DirectionHelper.Direction.RIGHT;
    }
    public DirectionHelper.Direction getDirection() {
        return direction;
    }
    public void setDirection(DirectionHelper.Direction direction) {
        this.direction = direction;
    }

}
