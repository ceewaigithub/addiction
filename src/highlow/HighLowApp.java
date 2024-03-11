package highlow;

public class HighLowApp {
    public static void main(String[] args) {

        HighLowGUI gui = new HighLowGUI();
        new HighLowGame(gui);
    }
}