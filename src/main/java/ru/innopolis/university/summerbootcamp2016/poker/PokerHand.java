package ru.innopolis.university.summerbootcamp2016.poker;

public enum PokerHand {

    /** Royal flush (Ace-high Straight Flush). */
    ROYAL_FLUSH(10),

    /** Straight Flush (a Straight and a Flush, less than Ace-high). */
    STRAIGHT_FLUSH(9),

    /** Four of a Kind (four cards of the same rank). */
    FOUR_OF_A_KIND(8),

    /** Full House (a Three of a Kind and Two Pairs). */
    FULL_HOUSE(7),

    /** Flush (five cards of the same suit). */
    FLUSH(6),

    /** Straight (five cards in sequential order). */
    STRAIGHT(5),

    /** Three of a Kind (three cards of the same rank). */
    THREE_OF_A_KIND(4),

    /** Two Pairs (two pairs). */
    TWO_PAIR(3),

    /** One Pair (two cards of the same rank). */
    PAIR(2),

    /** Highest Card (the card with the highest rank). */
    HIGH_CARD(1);

    private int strength;

    PokerHand(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
