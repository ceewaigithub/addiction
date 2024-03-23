package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import object.OBJ_Door;
import object.SuperObject;
import shop.ShopItem;
import world.GamePanel;
import java.io.File;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void setGameConfig() {
        // Check if config.txt exists, if not create one
        File configFile = new File("config.txt");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
            bw.write("money=" + gp.user.money);
            bw.newLine();
            bw.write("sprite=" + gp.user.sprite);
            bw.newLine();
            bw.write("worldX=" + gp.user.worldX);
            bw.newLine();
            bw.write("worldY=" + gp.user.worldY);
            bw.newLine();
            bw.write("speed=" + gp.user.speed);
            bw.newLine();

            SuperObject obj[] = gp.obj;
            // save opened doors
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    if (obj[i].name.equals("Door")) {
                       bw.write(i + "=" + ((object.OBJ_Door) obj[i]).isOpen() + ",");
                    }
                }
            }
            
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        gp.sm.saveShopItems();
    }

    public void loadGameConfig() throws NewGameException {
        // Check if config.txt exists, if not create one
        File configFile = new File("config.txt");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            String[] data = new String[8];
            String line;
            int i = 0;
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("config.txt"));
            while ((line = br.readLine()) != null) {
                data[i] = line;
                i++;
            }
            br.close();

            gp.sm.loadShopItems();
            List<ShopItem> shopItems = gp.sm.getShopItems();
            for (ShopItem item : shopItems) {
                if (item.getName().equals("Sound")) {
                    gp.musicEnabled = true;
                }
            }
            try {
                gp.user.money = Integer.parseInt(data[0].substring(6));
                gp.user.sprite = data[1].substring(7);
                gp.user.worldX = Integer.parseInt(data[2].substring(7));
                gp.user.worldY = Integer.parseInt(data[3].substring(7));
                gp.user.speed = Integer.parseInt(data[4].substring(6));
                

                String[] doorData = data[5].split(",");
                for (int j = 0; j < doorData.length; j++) {
                    String[] door = doorData[j].split("=");
                    int idx = Integer.parseInt(door[0]);
                    boolean isOpen = Boolean.parseBoolean(door[1]);
                    if (idx >= 0 && idx < gp.obj.length && gp.obj[idx] instanceof OBJ_Door) {
                        ((OBJ_Door) gp.obj[idx]).setOpen(isOpen);
                    }
                }

            } catch (Exception e) {
                throw new NewGameException("New game, empty config file.");
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void saveShopItems(String data) {
        // Check if config.txt exists, if not create one
        File configFile = new File("shopItems.txt");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("shopItems.txt"));
            bw.write(data);
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String loadShopItems() {
        // Check if config.txt exists, if not create one
        File configFile = new File("shopItems.txt");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String data = "";
        try {
            String line;
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("shopItems.txt"));
            while ((line = br.readLine()) != null) {
                data += line + "\n";
            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    public void restartGame() {
        File configFile = new File("config.txt");
        if (configFile.exists()) {
            configFile.delete();
        }
        File shopItemsFile = new File("shopItems.txt");
        if (shopItemsFile.exists()) {
            shopItemsFile.delete();
        }
        gp.sm.resetShopItems();
        gp.sm.loadShopItems();
        gp.user.setDefaultValues();
    }

}
