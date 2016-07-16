package ru.innopolis.university.summerbootcamp2016.poker;

public class Card {

    private Suit suit;
    private Value value;


    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }


    public Card(int suitNum, int valueNum) {
        this.suit = Suit.values()[suitNum];
        this.value = Value.values()[valueNum];
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
