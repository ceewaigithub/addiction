package highlow;
import java.awt.*;
import javax.swing.*;
import card.BettingSystem;
import card.Card;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import entity.User;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}