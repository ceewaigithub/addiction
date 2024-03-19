package object;

import java.io.File;

import javax.imageio.ImageIO;

public class OBJ_DoorOpen extends SuperObject {
    public OBJ_DoorOpen() {
        name = "doorOpen";
        collision = false;
        String currentDirectory = new File("").getAbsolutePath();
        try {
            image = ImageIO.read(new File(currentDirectory + "/res/objects/doorOpen.png"));
        } catch (Exception e) {
            System.out.println("Directory" + currentDirectory + "/res/objects/doorOpen.png");
            e.printStackTrace();
        }
    }
}