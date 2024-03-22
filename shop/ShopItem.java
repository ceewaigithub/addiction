package shop;

public class ShopItem {
    private String name;
    private String description;
    private int price;
    private boolean purchased;

    public ShopItem(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.purchased = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public String toString() {
        return name + " - " + description + " - " + price;
    }

    public String toSaveString() {
        return "ShopItem," + name + "," + description + "," + price + "," + purchased;
    }

    public static ShopItem fromString(String str) {
        String[] parts = str.split(",");
        return new ShopItem(parts[0], parts[1], Integer.parseInt(parts[2]));
    }

    public static ShopItem fromSaveString(String str) {
        String[] parts = str.split(",");
        if (parts.length >= 4) {
            String itemType = parts[0];
            if (itemType.equals("SpriteItem")) {

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

    public boolean equals(Object obj) {
        if (obj instanceof ShopItem) {
            ShopItem other = (ShopItem) obj;
            return name.equals(other.name) && description.equals(other.description) && price == other.price;
        }
        return false;
    }

    public int hashCode() {
        return name.hashCode() + description.hashCode() + price;
    }

}