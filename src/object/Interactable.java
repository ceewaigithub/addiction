package object;

import entity.User;
import world.GamePanel;

/**
 * The Interactable interface represents an object that can be interacted with in the game.
 * Any class that implements this interface must provide an implementation for the interact method.
 */
public interface Interactable {
    /**
     * Performs an interaction between the specified user and the game panel.
     * 
     * @param user The user performing the interaction.
     * @param gp The game panel where the interaction takes place.
     */
    void interact(User user, GamePanel gp);
}