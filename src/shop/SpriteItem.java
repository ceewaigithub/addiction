package shop;

/**
 * Represents a SpriteItem, which is a type of ShopItem.
 * A SpriteItem has a sprite name associated with it.
 */
public class SpriteItem extends ShopItem {
    String spriteName;
    
    /**
     * Constructs a new SpriteItem object with the specified name, description, price, and sprite name.
     * 
     * @param name the name of the SpriteItem
     * @param description the description of the SpriteItem
     * @param price the price of the SpriteItem
     * @param spriteName the sprite name of the SpriteItem
     */
    public SpriteItem(String name, String description, int price, String spriteName) {
        super(name, description, price);
        this.spriteName = spriteName;
    }

    /**
     * Returns the sprite name of the SpriteItem.
     * 
     * @return the sprite name of the SpriteItem
     */
    public String getSprite() {
        return spriteName;
    }

    /**
     * Sets the sprite name of the SpriteItem.
     * 
     * @param spriteName the new sprite name to set
     */
    public void setSpriteName(String spriteName) {
        this.spriteName = spriteName;
    }
    
    /**
     * Returns a string representation of the SpriteItem.
     * The string representation includes the name, description, price, and sprite name of the SpriteItem.
     * 
     * @return a string representation of the SpriteItem
     */
    public String toString() {
        return super.toString() + " - " + spriteName;
    }

    /**
     * Returns a string representation of the SpriteItem for saving purposes.
     * The string representation includes the class name, name, description, price, and sprite name of the SpriteItem.
     * 
     * @return a string representation of the SpriteItem for saving purposes
     */
    @Override
    public String toSaveString() {
        return "SpriteItem," + super.toSaveString() + "," + spriteName;
    }
}
