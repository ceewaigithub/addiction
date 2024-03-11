public class BettingSystem {
    private int playerBalance;
    private int currentBet;

    public BettingSystem(int initialBalance) {
        this.playerBalance = initialBalance;
        this.currentBet = 0;
    }

    public int getPlayerBalance() {
        return playerBalance;
    }
    public int getPlayerBet(){
        return currentBet;
    }
    public void placeBet(int amount) {
        if (amount > 0 && amount <= playerBalance) {
            currentBet += amount;
            playerBalance -= amount;
            System.out.println(currentBet);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void winBet() {
        playerBalance += currentBet * 2; // Assuming 1:1 payout
        currentBet = 0;
    }
    public void resetBet(){
        playerBalance += currentBet;
        currentBet = 0;
        System.out.println("Cleared");
    }

    public void loseBet() {
        // Player loses the bet
        currentBet = 0;
        // Handle any additional logic, like checking if the player is bankrupt
    }

    public void pushBet() {
        // Player gets back the bet amount
        playerBalance += currentBet;
        currentBet = 0;
    }
}