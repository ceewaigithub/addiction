public class BettingSystem {
//    private int playerBalance;
    private int currentBet;
    private Player player;

    public BettingSystem(Player player) {
        this.player = player;
//        this.playerBalance = player.getBalance();
        this.currentBet = 0;
    }

    public int getPlayerBalance() {
        return player.getBalance();
    }
    public int getPlayerBet(){
        return currentBet;
    }
    public void placeBet(int amount) {
        if (amount > 0 && amount <= player.getBalance()) {
            currentBet += amount;
            player.setBalance(player.getBalance() - amount);
//            playerBalance -= amount;
            System.out.println(currentBet);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void winBet(int multiplier) {
        player.setBalance(player.getBalance() + currentBet * multiplier);
        currentBet = 0;
    }
    public void loseBet() {
        // Player loses the bet
        currentBet = 0;
        // Handle any additional logic, like checking if the player is bankrupt
    }

    public void pushBet() {
        // Player gets back the bet amount
        player.setBalance(player.getBalance() + currentBet);
        currentBet = 0;
    }
    public void resetBet(){
        player.setBalance(player.getBalance() + currentBet); //clear be
        currentBet = 0;
        System.out.println("Cleared");
    }
}