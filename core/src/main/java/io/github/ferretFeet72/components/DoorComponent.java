package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;

public class DoorComponent implements Component {
    public boolean doorOpen = false;
    public boolean doorLock = false;
    public int closedTileId;
    public int openTileId;
    public int tileX, tileY;
    public DoorComponent(int closedTileID, int openTileID, int tileX, int tileY) {
        this.closedTileId = closedTileID;
        this.openTileId = openTileID;
        this.tileX = tileX;
        this.tileY = tileY;
    }
}
