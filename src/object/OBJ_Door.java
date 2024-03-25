package object;

import java.io.File;

import javax.imageio.ImageIO;

/**
 * The `OBJ_Door` class represents a door object in a game.
 * It extends the `SuperObject` class and inherits its properties and methods.
 * The door can be opened or closed, and it can be locked or unlocked.
 * When the door is opened, the collision is disabled and the image is updated to the open state.
 * When the door is closed, the collision is enabled and the image is updated to the closed state.
 */
import java.io.File;
import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject {
    boolean isOpen;

    /**
     * Constructor for the OBJ_Door class.
     * Initializes the name, collision, and isOpen variables.
     * Loads the image for the closed door from the resources directory.
     */
    public OBJ_Door() {
        name = "Door";
        collision = true;
        isOpen = false;
        String currentDirectory = new File("").getAbsolutePath();
        try {
            image = ImageIO.read(new File(currentDirectory + "/res/objects/door.png"));
        } catch (Exception e) {
            System.out.println("Directory" + currentDirectory + "/res/objects/door.png");
            e.printStackTrace();
        }
    }

    /**
     * Sets the isOpen variable to the specified value.
     * If the door is open, sets collision to false.
     * Updates the door image to the open state.
     * 
     * @param isOpen the new value for isOpen
     */
    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
        if (isOpen) {
            collision = false;
            // Update the door image to the open state
            String currentDirectory = new File("").getAbsolutePath();
            try {
                image = ImageIO.read(new File(currentDirectory + "/res/objects/doorOpen.png"));
            } catch (Exception e) {
                System.out.println("Directory" + currentDirectory + "/res/objects/doorOpen.png");
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if the door is open.
     * 
     * @return true if the door is open, false otherwise
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Checks if the door is locked.
     * 
     * @return true if the door is locked, false if it is open
     */
    public boolean isLocked() {
        return !isOpen;
    }

    /**
     * Returns a string representation of the OBJ_Door object.
     * 
     * @return a string containing the name, x-coordinate, y-coordinate, and isOpen status of the door
     */
    @Override
    public String toString() {
        return name + " " + x + " " + y + " " + isOpen;
    }
}