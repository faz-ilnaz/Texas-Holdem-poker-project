package ru.innopolis.university.summerbootcamp2016.poker;

import java.util.ArrayList;
import java.util.List;

/*
 * The Player class. Contains not using methods cause they had been used before.
 * Contains all main functions of Player like call(), raise(), check(), fold()
 * Also there are many helping methods
 */

public class Player {

    private String name;
    private long stake;
    private long balance;
    private int id;//starts from 2, 1 is a Table
    private boolean dealer = false;
    private List<Card> cards;
    private float strengthOfHand;

    public int getStrengthOfHighestCard() {
        return strengthOfHighestCard;
    }

    public void setStrengthOfHighestCard(int strengthOfHighestCard) {
        this.strengthOfHighestCard = strengthOfHighestCard;
    }

    private int strengthOfHighestCard;

    /**
     * 0 - step was not made in current stage
     * 1 - step was made in current stage
     * -1 - player folded his cards
     */
    private int playingStatus = 0;


    boolean isPlaying() {
        return playingStatus != -1;
    }

    public int getPlayingStatus() {
        return playingStatus;
    }

    public void setPlayingStatus(int playingStatus) {
        this.playingStatus = playingStatus;
    }

    public void resetPlayingStatus() {
        this.playingStatus = 0;
    }

    public String getName() {
        return name;
    }

    public void setDealer(boolean dealer) {
        this.dealer = dealer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setStrength(float strength) {
        this.strengthOfHand = strength;
    }

    public float getStrength() {
        return strengthOfHand;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public long getStake() {
        return stake;
    }

    public void setStake(long stake) {
        this.stake = stake;
    }

    //Method returns stake and resets it
    public long collectPlayerStake() {
        long s = stake;
        stake = 0;
        return s;
    }

    //Function set stake and subtract balance if stake < balance
    public boolean makeStake(long stake) {
        if (stake > this.balance) {
            return false;
        }
        this.stake += stake;
        this.balance -= stake;
        if (this.stake > Game.maxStake) {
            Game.maxStake = this.stake;
        }
        return true;
    }

    public boolean isDealer() {
        if (dealer)
            return true;
        else
            return false;
    }

    public void takeCards(Deck deck) {
        cards = new ArrayList<Card>();
        cards.add(deck.getCard(id));
        cards.add(deck.getCard(id));
    }

    public boolean raise(long raise) {
        if (raise > Game.maxStake - this.stake) {
            if (makeStake(raise)) {
                playingStatus = 1;
                System.out.println("Raise is correct! Maximal stake is now "+Game.maxStake);
                return true;
            } else {
                System.out.println("You can not make a raise! Not enough balance!");
                return false;
            }
        } else {
            System.out.println("Raise can not be less than previous raise! (" + (Game.maxStake - this.stake) + ")");
            return false;
        }
    }

    public boolean call() {
        long diff = Game.maxStake - stake;
        if (diff != 0) {
            if (makeStake(diff)) {
                playingStatus = 1;
                System.out.println("Call is correct. Maximal stake is now "+Game.maxStake);
                return true;
            } else {
                System.out.println("You can not call");
                return false;
            }
        } else {
            System.out.println("You can not make a call! Your stake is the same!");
            return false;
        }
    }

    // check() method returns true if you can make a check
    public boolean check() {
        if (this.stake == Game.maxStake) {
            playingStatus = 1;
            System.out.println("Checked");
            return true;
        } else {
            System.out.println("You can not make a check! Your stake is not equal to max value!");
            return false;
        }
    }

    public boolean fold() {
        this.playingStatus = -1;
        this.strengthOfHand = 0;
        System.out.println("Fold is correct");
        return true;
    }

    public void printCards() {
        for (int i = 0; i < cards.size(); i++) {
            UI.displayCard(cards.get(i));
        }
    }

    public void printInfo() {
        System.out.println("\n" + this.getName() + " Stake:" + this.getStake() + " Balance:" + this.getBalance() +
        " IsDealer: " + dealer);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", cards=" + cards +
                '}';
    }
}
