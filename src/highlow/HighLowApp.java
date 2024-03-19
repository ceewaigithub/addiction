package highlow;

import javax.swing.JFrame;

import entity.User;

public class HighLowApp {

    // Variable
    User user;
    JFrame mapFrame;

    public HighLowApp(User user, JFrame mapFrame) {
        this.user = user;
        this.mapFrame = mapFrame;
        HighLowGUI highLowGUI = new HighLowGUI();
        HighLowGame highLowGame = new HighLowGame(highLowGUI);
    }


    public static void main(String[] args) {

        HighLowGUI gui = new HighLowGUI();
        new HighLowGame(gui);
    }
}