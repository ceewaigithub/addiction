package world;

public class GameLogic {
    private GamePanel gp;
    private int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int titleState = 4;
    public final int gameOverState = 5;

    public GameLogic(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Updates the game state based on the current game state.
     * If the game state is playState, updates the user.
     * If the game state is pauseState, does nothing.
     * If the game state is dialogueState, does nothing.
     * If the game state is titleState, does nothing.
     * If the game state is gameOverState, does nothing.
     * If the user's money is less than 0, sets the game state to gameOverState.
     */
    public void update() {
        if (gameState == playState) {
            gp.user.update();
        }
        if (gameState == pauseState) {
            // Do nothing
        }
        if (gameState == dialogueState) {
            // Do nothing
        }
        if (gameState == titleState) {
            // Do nothing
        }
        if (gameState == gameOverState) {
            // Do nothing
        }
        if (gp.user.getBalance() < 0) {
            gameState = gameOverState;
        }
    }

    // Other game logic methods...
    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }
}