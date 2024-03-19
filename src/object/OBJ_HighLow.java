package object;

import java.io.File;

import javax.imageio.ImageIO;

public class OBJ_HighLow extends SuperObject {
    public OBJ_HighLow() {
        name = "HighLow";
        collision = true;
        String currentDirectory = new File("").getAbsolutePath();
        try {
            image = ImageIO.read(new File(currentDirectory + "/res/objects/chest.png"));
        } catch (Exception e) {
            System.out.println("Directory" + currentDirectory + "/res/objects/chest.png");
            e.printStackTrace();
        }
    }
}
