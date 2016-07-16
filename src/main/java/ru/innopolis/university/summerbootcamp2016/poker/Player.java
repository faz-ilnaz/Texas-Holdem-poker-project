package ru.innopolis.university.summerbootcamp2016.poker;

import java.util.List;

public class Player {

    private String name;
    private long stake;
    private long balance;
    private List<Card> cards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
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

    public long collectPlayerStake(){
        this.balance -= stake;
        return this.stake;
    }

    public boolean setPlayerStake(long stake){
        if (stake > this.balance){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", cards=" + cards +
                '}';
    }
}
