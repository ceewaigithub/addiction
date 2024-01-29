package world;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // window Frame
        JFrame window = new JFrame();
        window.setTitle("Crippling Addiction");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();

    }
}
