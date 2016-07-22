package ru.innopolis.university.summerbootcamp2016.poker;

import java.util.Random;

public class Deck {

    public Integer[][] deck = new Integer[4][13];
    public static int maxSuit = 4;
    public static int maxValue = 13;

    public Deck() {
        resetDeck();
    }

    /**
     * Delete any assigns of every card
     */
    public void resetDeck() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 13; j++) {
                deck[i][j] = 0;
            }
    }

    /**
     * Get random card from deck.
     * Matrix cell contains an id of the owner
     * @param holder
     * @return
     */
    public Card getCard(int holder){
        Random r = new Random();
        int suit = r.nextInt(maxSuit);
        int value = r.nextInt(maxValue);
        while(deck[suit][value]!=0){
            suit = r.nextInt(maxSuit);
            value = r.nextInt(maxValue);
        }
        deck[suit][value]=holder;
        return new Card(suit,value);
    }

}
