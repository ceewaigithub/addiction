package shop;

public class SpriteItem extends ShopItem {
    String spriteName;
    
    public SpriteItem(String name, String description, int price, String spriteName) {
        super(name, description, price);
        this.spriteName = spriteName;
    }

    public String getSprite() {
        return spriteName;
    }

    public void setSpriteName(String spriteName) {
        this.spriteName = spriteName;
    }
    
    public String toString() {
        return super.toString() + " - " + spriteName;
    }
    
    public static SpriteItem fromSaveString(String str) {
        String[] parts = str.split(",");
        if (parts.length >= 4) {
            SpriteItem item = new SpriteItem(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]);
            item.setPurchased(Boolean.parseBoolean(parts[4]));
            return item;
        } else {
            throw new IllegalArgumentException("Invalid save string: " + str);
        }
    }
}
