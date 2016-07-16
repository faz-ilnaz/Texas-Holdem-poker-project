package ru.innopolis.university.summerbootcamp2016.poker;

import java.util.List;

public class Player {

    private String name;
    private long stake;
    private long balance;
    private long id;//starts from 2, 1 is a Table
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

    //Method returns stake and resets it
    public long collectPlayerStake(){
        int s = stake;
        stake=0;
        return s;
    }
    //Function set stake and subtract balance if stake < balance
    public boolean setPlayerStake(long stake){
        if (stake > this.balance){
            return false;
        }
        this.stake = stake;
        this.balance-=stake;
        if(this.stake>Game.maxStake){
            Game.maxStake = this.stake;
        }
        return true;
    }

    public void call(){
        long diff = maxStake - stake;
        if(setPlayerStake(diff)){
            System.out.println("Call is correct");
        }
        else{
            System.out.println("You can not call");
        }
    }

    public void raise(){

    }

    public void check(){

    }

    public void fold(){

    }
    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", cards=" + cards +
                '}';
    }
}
