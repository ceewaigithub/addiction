package shop;

import java.util.ArrayList;
import java.util.List;

import world.GamePanel;

public class ShopManager {
    private List<ShopItem> shopItems;
    private GamePanel gp;

    public ShopManager(GamePanel gp) {
        this.gp = gp;
        shopItems = new ArrayList<>();
        initializeShopItems();
        loadShopItems();
    }

    private void initializeShopItems() {
        shopItems.add(new ShopItem("Sound", "Be able to hear!", 50));
        ShopItem defaultSprite = new SpriteItem("Default Sprite : player1", "Default player sprite", 0, "default");
        defaultSprite.setPurchased(true);
        shopItems.add(defaultSprite);
        shopItems.add(new SpriteItem("New Sprite 1 : player1", "Unlock a new player sprite", 200, "player1"));
        shopItems.add(new ShopItem("Speed Upgrade", "Increase player speed by 1", 100));
        shopItems.add(new ShopItem("Speed Upgrade", "Increase player speed by 2", 500));
        shopItems.add(new ShopItem("Speed Upgrade", "Increase player speed by 3", 1000));
    }

    public boolean buyItem(int index) {
        ShopItem item = shopItems.get(index);
        if (!item.isPurchased() && gp.user.money >= item.getPrice()) {
            gp.user.money -= item.getPrice();
            item.setPurchased(true);
            applyItemEffect(item);
            return true;
        }
        return false;
    }

    private void applyItemEffect(ShopItem item) {
        switch (item.getName()) {
            case "Sound":
                gp.musicEnabled = true;
                break;
            case "New Sprite 1 : player1":
                gp.user.sprite = "player1";
                gp.user.getPlayerImage();
                break;
            case "Speed Upgrade":
                gp.user.speed++;
                break;
        }
    }

    public List<ShopItem> getShopItems() {
        return shopItems;
    }

    public void setShopItems(List<ShopItem> shopItems) {
        this.shopItems = shopItems;
    }

    public void saveShopItems() {
        StringBuilder sb = new StringBuilder();
        for (ShopItem item : shopItems) {
            sb.append(item.toSaveString()).append("\n");
        }
        gp.config.saveShopItems(sb.toString());
    }

    public void loadShopItems() {
        String data = gp.config.loadShopItems();
        if (data.isEmpty()) {
            return;
        }
        String[] lines = data.split("\n");
        shopItems.clear();
        for (String line : lines) {
            shopItems.add(ShopItem.fromSaveString(line));
        }
    }

    public void resetShopItems() {
        for (ShopItem item : shopItems) {
            item.setPurchased(false);
        }
    }

}
