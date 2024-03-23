package object;

import java.io.File;

import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject {
    boolean isOpen;

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

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isLocked() {
        return !isOpen;
    }

    @Override
    public String toString() {
        return name + " " + x + " " + y + " " + isOpen;
    }
}