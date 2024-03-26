package object;

import java.io.File;

import javax.imageio.ImageIO;

public class OBJ_Baccarat extends SuperObject {
    public OBJ_Baccarat() {
        name = "Baccarat";
        collision = true;
        String currentDirectory = new File("").getAbsolutePath();
        try {
            image = ImageIO.read(new File(currentDirectory + "/res/objects/table.png"));
        } catch (Exception e) {
            System.out.println("Directory" + currentDirectory + "/res/objects/table.png");
            e.printStackTrace();
        }
    }
}
