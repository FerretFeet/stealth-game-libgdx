package io.github.ferretFeet72.utils;

import com.badlogic.gdx.math.Vector2;

public class DirectionHelper {
    public enum Direction {
        UP (0,1) ,
        DOWN(0,-1),
        LEFT(-1,0),
        RIGHT(1,0),
        NONE(1,0) // Optional: for when the entity isn't moving
        ;
        private final Vector2 direction;
        Direction(float x, float y) {
            this.direction = new Vector2(x, y);
        }
        public Vector2 getDirection() {
            return direction;
        }

    }

    /**
     * Determines the 4-way cardinal direction from a given velocity vector.
     * The method uses the absolute value of the vector's components to
     * determine the dominant axis of movement.
     * * @param velocity A Vector2 representing the entity's velocity.
     * @return A Direction enum representing the direction.
     */
    public static Direction getDirection(Vector2 velocity) {
        // We use len2() for a more efficient check than len()
        // as it avoids a square root calculation.
        if (velocity.len2() == 0) {
            return Direction.NONE;
        }

        // Compare the absolute values of the x and y components to find the dominant axis.
        if (Math.abs(velocity.x) > Math.abs(velocity.y)) {
            // Horizontal movement is dominant.
            if (velocity.x > 0) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        } else {
            // Vertical movement is dominant (or equal).
            // A positive y value often means UP in LibGDX's default coordinate system.
            if (velocity.y > 0) {
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        }
    }
}
