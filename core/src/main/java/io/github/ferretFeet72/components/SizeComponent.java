package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;

public class SizeComponent implements Component {
    public int x, y, z;
    public SizeComponent(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
