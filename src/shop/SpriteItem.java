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

    @Override
    public String toSaveString() {
        return "SpriteItem," + super.toSaveString() + "," + spriteName;
    }
}
