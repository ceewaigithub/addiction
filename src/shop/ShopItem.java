package shop;

/**
 * The ShopItem class represents an item in a shop.
 */
public class ShopItem {
    private String name;
    private String description;
    private int price;
    private boolean purchased;

    /**
     * Constructs a new ShopItem object with the given name, description, and price.
     *
     * @param name        the name of the shop item
     * @param description the description of the shop item
     * @param price       the price of the shop item
     */
    public ShopItem(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.purchased = false;
    }

    /**
     * Returns the name of the shop item.
     *
     * @return the name of the shop item
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the shop item.
     *
     * @return the description of the shop item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the price of the shop item.
     *
     * @return the price of the shop item
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns whether the shop item has been purchased.
     *
     * @return true if the shop item has been purchased, false otherwise
     */
    public boolean isPurchased() {
        return purchased;
    }

    /**
     * Sets the purchased status of the shop item.
     *
     * @param purchased true if the shop item has been purchased, false otherwise
     */
    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    /**
     * Returns a string representation of the shop item.
     *
     * @return a string representation of the shop item
     */
    public String toString() {
        return name + " - " + description + " - " + price;
    }

    /**
     * Returns a string representation of the shop item for saving purposes.
     *
     * @return a string representation of the shop item for saving purposes
     */
    public String toSaveString() {
        return "ShopItem," + name + "," + description + "," + price + "," + purchased;
    }

    /**
     * Creates a new ShopItem object from the given string representation.
     *
     * @param str the string representation of the shop item
     * @return a new ShopItem object created from the string representation
     */
    public static ShopItem fromString(String str) {
        String[] parts = str.split(",");
        return new ShopItem(parts[0], parts[1], Integer.parseInt(parts[2]));
    }

    /**
     * Creates a new ShopItem object from the given string representation for saving purposes.
     *
     * @param str the string representation of the shop item for saving purposes
     * @return a new ShopItem object created from the string representation for saving purposes
     * @throws IllegalArgumentException if the string representation is invalid
     */
    public static ShopItem fromSaveString(String str) {
        String[] parts = str.split(",");
        if (parts.length >= 4) {
            String itemType = parts[0];
            if (itemType.equals("SpriteItem")) {
                // Assuming SpriteItem class exists
                SpriteItem item = new SpriteItem(parts[2], parts[3], Integer.parseInt(parts[4]), parts[6]);
                item.setPurchased(Boolean.parseBoolean(parts[5]));
                return item;
            } else {
                ShopItem item = new ShopItem(parts[1], parts[2], Integer.parseInt(parts[3]));
                item.setPurchased(Boolean.parseBoolean(parts[4]));
                return item;
            }
        } else {
            throw new IllegalArgumentException("Invalid save string: " + str);
        }
    }

    /**
     * Checks if the shop item is equal to the given object.
     *
     * @param obj the object to compare with
     * @return true if the shop item is equal to the given object, false otherwise
     */
    public boolean equals(Object obj) {
        if (obj instanceof ShopItem) {
            ShopItem other = (ShopItem) obj;
            return name.equals(other.name) && description.equals(other.description) && price == other.price;
        }
        return false;
    }

    /**
     * Returns the hash code value for the shop item.
     *
     * @return the hash code value for the shop item
     */
    public int hashCode() {
        return name.hashCode() + description.hashCode() + price;
    }

}