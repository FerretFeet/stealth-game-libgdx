package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;

public class AccelerationComponent implements Component {
    private float ax, ay, az;

    public AccelerationComponent(float ax, float ay, float az) {
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }

    public float getAz() {
        return az;
    }

    public void setAz(float az) {
        this.az = az;
    }

    public float getAy() {
        return ay;
    }

    public void setAy(float ay) {
        this.ay = ay;
    }

    public float getAx() {
        return ax;
    }

    public void setAx(float ax) {
        this.ax = ax;
    }
}
