package ru.innopolis.university.summerbootcamp2016.poker;

import java.util.ArrayList;
import java.util.List;

/**
 * The Table class consists of cards on the table and methods
 * that get cards from the deck
 */
public class Table {
    List<Card> cardsOnTable = new ArrayList<Card>();
    public final int id = 1;

    public void takeCard(Deck thisDeck) {
        Card nextCard = thisDeck.getCard(id);
        cardsOnTable.add(nextCard);
        Game.amountCardsTable++;
    }

    public void takeFlop(Deck thisDeck) {
        takeCard(thisDeck);
        takeCard(thisDeck);
        takeCard(thisDeck);
    }

    public void showTable() {
        System.out.println("Table:");
        for (int i = 0; i < cardsOnTable.size(); i++) {
            UI.displayCard(cardsOnTable.get(i));
        }
        System.out.println();
    }

    void reset() {
        cardsOnTable.clear();
    }



}
