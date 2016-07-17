package ru.innopolis.university.summerbootcamp2016.poker;

import java.util.List;

public class Bank {


    private long bankBalance;

    public Bank() {
        this.bankBalance = 0;
    }

    public void addBankBalance(long bankBalance) {
        this.bankBalance += bankBalance;
    }

    public long getReward() {
        long bal = this.bankBalance;
        this.bankBalance = 0;
        return bal;
    }

    public void collectStakes(List<Player> playersList) {

        for (Player player : playersList) {
            this.bankBalance += player.collectPlayerStake();
        }

    }

    public long getBankBalance() {
        return bankBalance;
    }
}