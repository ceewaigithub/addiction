package object;

import java.io.File;

import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject {
    public OBJ_Door() {
        name = "Door";
        collision = true;
        String currentDirectory = new File("").getAbsolutePath();
        try {
            image = ImageIO.read(new File(currentDirectory + "/res/objects/door.png"));
        } catch (Exception e) {
            System.out.println("Directory" + currentDirectory + "/res/objects/door.png");
            e.printStackTrace();
        }
    }
}