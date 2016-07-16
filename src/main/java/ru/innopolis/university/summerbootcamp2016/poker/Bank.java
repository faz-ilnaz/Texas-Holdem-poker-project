package ru.innopolis.university.summerbootcamp2016.poker;

public class Bank {
    private long bankBalance;

    public Bank(){
        this.bankBalance = 0;
    }

    public void addBankBalance(long bankBalance) {
        this.bankBalance += bankBalance;
    }
    public long getReward(){
        int bal = this.bankBalance;
        this.bankBalance = 0;
        return bal;
    }
    public void collectStakes(List<Player> playersList){
        for (int i = 0; i < playersList.length; i++){
            this.bankBalance += playersList[i].getStake();
        }

    }
}