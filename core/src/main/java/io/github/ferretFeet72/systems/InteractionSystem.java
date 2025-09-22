package io.github.ferretFeet72.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.ferretFeet72.components.*;
import io.github.ferretFeet72.utils.DirectionHelper;

public class InteractionSystem extends EntitySystem {

    // Component mappers for efficient component retrieval
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<DirectionComponent> dm = ComponentMapper.getFor(DirectionComponent.class);
    private final ComponentMapper<InputComponent> im = ComponentMapper.getFor(InputComponent.class);
    private final ComponentMapper<InteractableComponent> icm = ComponentMapper.getFor(InteractableComponent.class);
    private final ComponentMapper<SizeComponent> sm = ComponentMapper.getFor(SizeComponent.class);

    // Families for different types of entities
    private ImmutableArray<Entity> players;
    private ImmutableArray<Entity> interactables;

    private static float INTERACTION_RANGE = 32.0f; // Distance from player to scan

    @Override
    public void addedToEngine(Engine engine) {
        // Get all entities with the required components for our players.
        players = engine.getEntitiesFor(Family.all(PositionComponent.class, DirectionComponent.class, InputComponent.class).get());
        // Get all entities that can be interacted with.
        interactables = engine.getEntitiesFor(Family.all(PositionComponent.class, InteractableComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {

        // We only expect one player entity.
        if (players.size() > 0) {
            Entity player = players.first();
            InputComponent playerInput = im.get(player);

            // Check if the "interact" key is currently pressed.
            if (playerInput.isInteractPressed()) {
                // Get the player's position and direction vector.
                PositionComponent playerPos = pm.get(player);
                DirectionComponent playerDir = dm.get(player);
                SizeComponent playerSize = sm.get(player);
                float curInteractionRange = INTERACTION_RANGE;
//                if (playerDir.getDirection() == DirectionHelper.Direction.UP) {
//                    System.out.println("dir up");
//                    curInteractionRange = 2*INTERACTION_RANGE;
//                } else {
//                    curInteractionRange = INTERACTION_RANGE;
//                }
                // Calculate the target interaction point.
                // This is a simple 'raycast' using the player's direction.
                float centerX = playerPos.getX() + (playerSize.getX() / 2);
                float centerY = playerPos.getY() + (playerSize.getY() / 2);


                Vector2 targetPoint = new Vector2(centerX, centerY)
                    .add(playerDir.getDirection().getDirection().cpy().scl(curInteractionRange));


                // Find the interactable entity at the target point.
                Entity targetEntity = findInteractableAtPoint(targetPoint);

                // If an interactable entity was found, perform the interaction.
                if (targetEntity != null) {
                    performInteraction(targetEntity);
                } else {
                    Gdx.app.log("InteractionSystem", "Nothing to interact with.");
                }

                // Reset the interact flag so the action only happens once per key press.
                playerInput.setInteractPressed(false);
            }
        }
    }

    /**
     * Finds the closest interactable entity to a given point within a tolerance.
     */
    private Entity findInteractableAtPoint(Vector2 point) {
        System.out.println("Finding Interactable at " + point);
        for (Entity interactableEntity : interactables) {
            PositionComponent interactablePos = pm.get(interactableEntity);
            SizeComponent interactableSize = sm.get(interactableEntity);
            Rectangle bounds = new Rectangle(
                interactablePos.getX(), interactablePos.getY(),
                interactableSize.getX(), interactableSize.getY()
            );
            // Check if the interactable is close enough to the target point.
            // You can adjust the tolerance (e.g., 0.5f) based on your game's scale.
            if (bounds.contains(point)) {
                return interactableEntity;
            }
//            if (point.dst(interactablePos.getX(), interactablePos.getY()) < 0.5f) {
//                return interactableEntity;
//            }
        }
        return null;
    }

    /**
     * Performs a different action based on the interactable object's type.
     */
    private void performInteraction(Entity targetEntity) {
        InteractableComponent interactableComp = icm.get(targetEntity);
        if (interactableComp == null) return; // Should not happen with our family check
        System.out.println("Interaction Occuring");
        switch (interactableComp.type) {
            case DOOR:
                Gdx.app.log("InteractionSystem", "Player opened a door!");
                DoorComponent door = targetEntity.getComponent(DoorComponent.class);
                if (!door.doorLock) { //door unlocked
                    targetEntity.add(new InteractionEventComponent());
                }
                // Add your door-specific logic here (e.g., change level, unlock door).
                break;
//            case NPC:
//                Gdx.app.log("InteractionSystem", "Player talked to an NPC!");
//                // Add your NPC-specific logic here (e.g., show dialogue box).
//                break;
//            case CHEST:
//                Gdx.app.log("InteractionSystem", "Player opened a chest!");
//                // Add your chest-specific logic here (e.g., give item to player).
//                break;
//            default:
//                Gdx.app.log("InteractionSystem", "Interacted with an unknown object.");
//                break;
        }
    }
}
