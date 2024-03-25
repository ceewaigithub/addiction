package shop;

import java.util.ArrayList;
import java.util.List;
import world.GamePanel;

/**
 * The ShopManager class represents a manager for the in-game shop.
 * It handles the management of shop items, purchases, and effects.
 */
public class ShopManager {
    private List<ShopItem> shopItems;
    private GamePanel gp;
    private String currentSprite;

    /**
     * Constructs a ShopManager object with the specified GamePanel.
     * 
     * @param gp the GamePanel object associated with the shop manager
     */
    public ShopManager(GamePanel gp) {
        this.gp = gp;
        shopItems = new ArrayList<>();
        initializeShopItems();
        loadShopItems();
        currentSprite = gp.user.sprite;
    }

    /**
     * Initializes the shop items with default values.
     * This method is called during the construction of the ShopManager object.
     */
    private void initializeShopItems() {
        shopItems.add(new ShopItem("Sound", "Be able to hear!", 50));
        ShopItem defaultSprite = new SpriteItem("Default Sprite : default", "Default player sprite", 0, "default");
        defaultSprite.setPurchased(true);
        shopItems.add(defaultSprite);
        shopItems.add(new SpriteItem("New Sprite 1 : player1", "Unlock a new player sprite", 200, "player1"));
        shopItems.add(new ShopItem("Speed Upgrade", "Increase player speed by 1", 100));
        shopItems.add(new ShopItem("Speed Upgrade", "Increase player speed by 2", 500));
        shopItems.add(new ShopItem("Speed Upgrade", "Increase player speed by 3", 1000));
    }

    /**
     * Checks if the sound item has been purchased.
     * 
     * @return true if the sound item has been purchased, false otherwise
     */
    public boolean isSoundPurchased() {
        for (ShopItem item : shopItems) {
            if (item.getName().equals("Sound") && item.isPurchased()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Buys the shop item at the specified index.
     * 
     * @param index the index of the shop item to buy
     * @return true if the item was successfully purchased, false otherwise
     */
    public boolean buyItem(int index) {
        ShopItem item = shopItems.get(index);
        if (item instanceof SpriteItem) {
            SpriteItem spriteItem = (SpriteItem) item;
            if (!spriteItem.isPurchased() && gp.user.money > spriteItem.getPrice()) {
                gp.user.money -= spriteItem.getPrice();
                spriteItem.setPurchased(true);
                currentSprite = spriteItem.getSprite();
                gp.user.sprite = currentSprite;
                gp.user.getPlayerImage();
            }
            return true;
        } else {
            if (!item.isPurchased() && gp.user.money > item.getPrice()) {
                gp.user.money -= item.getPrice();
                item.setPurchased(true);
                applyItemEffect(item);
                if (item.getName().equals("Sound")) {
                    gp.setBackgroundMusic();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Applies the effect of the purchased shop item.
     * 
     * @param item the shop item to apply the effect of
     */
    private void applyItemEffect(ShopItem item) {
        switch (item.getName()) {
            case "Sound":
                gp.musicEnabled = true;
                break;
            case "Speed Upgrade":
                gp.user.speed++;
                break;
        }
    }

    /**
     * Returns the list of shop items.
     * 
     * @return the list of shop items
     */
    public List<ShopItem> getShopItems() {
        return shopItems;
    }

    /**
     * Sets the list of shop items.
     * 
     * @param shopItems the list of shop items to set
     */
    public void setShopItems(List<ShopItem> shopItems) {
        this.shopItems = shopItems;
    }

    /**
     * Saves the shop items to a file.
     * This method is called when the shop items need to be saved.
     */
    public void saveShopItems() {
        StringBuilder sb = new StringBuilder();
        for (ShopItem item : shopItems) {
            sb.append(item.toSaveString()).append("\n");
        }
        sb.append("currentSprite:").append(currentSprite).append("\n");
        gp.config.saveShopItems(sb.toString());
    }

    /**
     * Loads the shop items from a file.
     * This method is called when the shop items need to be loaded.
     */
    public void loadShopItems() {
        String data = gp.config.loadShopItems();
        if (data.isEmpty()) {
            return;
        }
        String[] lines = data.split("\n");
        shopItems.clear();
        for (String line : lines) {
            if (line.startsWith("currentSprite:")) {
                currentSprite = line.substring("currentSprite:".length());
                gp.user.sprite = currentSprite;
                gp.user.getPlayerImage();
            } else {
                shopItems.add(ShopItem.fromSaveString(line));
            }
        }
    }

    /**
     * Resets the purchased status of all shop items.
     * This method is called when the shop items need to be reset.
     */
    public void resetShopItems() {
        for (ShopItem item : shopItems) {
            item.setPurchased(false);
        }
    }

    /**
     * Returns the current sprite.
     * 
     * @return the current sprite
     */
    public String getCurrentSprite() {
        return currentSprite;
    }
}