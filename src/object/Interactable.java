package object;

import entity.User;
import world.GamePanel;

public interface Interactable {
    void interact(User user, GamePanel gp);
}