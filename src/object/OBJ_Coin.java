package object;

import java.io.File;

import javax.imageio.ImageIO;

public class OBJ_Coin extends SuperObject {
    public OBJ_Coin() {
        name = "Coin";
        collision = true;
        String currentDirectory = new File("").getAbsolutePath();
        try {
            image = ImageIO.read(new File(currentDirectory + "/res/objects/coin.png"));
        } catch (Exception e) {
            System.out.println("Directory" + currentDirectory + "/res/objects/coin.png");
            e.printStackTrace();
        }
    }
}