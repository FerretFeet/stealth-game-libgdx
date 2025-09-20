package io.github.ferretFeet72.components;

import com.badlogic.ashley.core.Component;

public class InteractableComponent implements Component {
    public enum InteractableType {
        DOOR, NPC, CHEST
    }
    public InteractableType type;

    public InteractableComponent(InteractableType type) {
        this.type = type;
    }
}
