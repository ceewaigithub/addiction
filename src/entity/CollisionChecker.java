package entity;

import world.GamePanel;

/**
 * The CollisionChecker class is responsible for checking collisions between
 * entities and objects in the game.
 */
public class CollisionChecker {
    GamePanel gp;

    /**
     * Constructs a CollisionChecker object with the specified GamePanel.
     * 
     * @param gp The GamePanel object to associate with the CollisionChecker.
     */
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Checks for collisions between the specified entity and tiles in the game.
     * 
     * @param entity The entity to check for collisions.
     */
    public void checkTile(Entity entity) {
        // Calculate the world coordinates of the entity's solid area
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Calculate the column and row indices of the entity's solid area in the tile
        // map
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                // Adjust the top row index based on the entity's speed
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tm.mapTileNum[entityTopRow][entityRightCol];
                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                // Adjust the bottom row index based on the entity's speed
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tm.mapTileNum[entityBottomRow][entityRightCol];
                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                // Adjust the left column index based on the entity's speed
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tm.mapTileNum[entityBottomRow][entityLeftCol];
                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                // Adjust the right column index based on the entity's speed
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityTopRow][entityRightCol];
                tileNum2 = gp.tm.mapTileNum[entityBottomRow][entityRightCol];
                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    /**
     * Checks for collisions between the specified entity and objects in the game.
     * 
     * @param entity The entity to check for collisions.
     * @param player A boolean value indicating whether the entity is the player.
     * @return The index of the object that the entity collided with, or 999 if no
     *         collision occurred.
     */
    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null && gp.obj[i].collision) {

                // Adjust the solid areas of the entity and object based on their world
                // coordinates
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                gp.obj[i].solidArea.x = gp.obj[i].x + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].y + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        // Adjust the entity's solid area based on its speed and check for intersection
                        // with the object's solid area
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                }

                // Reset the solid areas of the entity and object to their default values
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    /**
     * Determines if the given entity is facing any object within the interaction
     * range.
     * 
     * @param entity The entity to check.
     * @return The index of the object that the entity is facing, or 999 if no
     *         object is found.
     */
    public int isFacing(Entity entity) {
        int index = 999;
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // Calculate the distance between the entity and the object
                int distanceX = Math.abs(entity.worldX - gp.obj[i].x);
                int distanceY = Math.abs(entity.worldY - gp.obj[i].y);

                // Check if the entity is facing the object and within the interaction range
                int interactionRange = gp.tileSize;
                switch (entity.direction) {
                    case "up":
                        if (distanceX < interactionRange && distanceY < interactionRange
                                && entity.worldY > gp.obj[i].y) {
                            index = i;
                        }
                        break;
                    case "down":
                        if (distanceX < interactionRange && distanceY < interactionRange
                                && entity.worldY < gp.obj[i].y) {
                            index = i;
                        }
                        break;
                    case "left":
                        if (distanceX < interactionRange && distanceY < interactionRange
                                && entity.worldX > gp.obj[i].x) {
                            index = i;
                        }
                        break;
                    case "right":
                        if (distanceX < interactionRange && distanceY < interactionRange
                                && entity.worldX < gp.obj[i].x) {
                            index = i;
                        }
                        break;
                }
            }
        }
        return index;
    }
}
