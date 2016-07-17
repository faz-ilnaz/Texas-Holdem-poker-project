package ru.innopolis.university.summerbootcamp2016.poker;

/**
 * User Interface
 */
public class UI {
    final private static String spades = "\u2660";
    final private static String hearts = "\u2665";
    final private static String diamonds = "\u2666";
    final private static String clubs = "\u2663";

    public static void displayCard(Card card){
        switch (card.getSuit()) {
            case SPADES:
                System.out.print(spades);
                break;
            case HEARTS:
                System.out.print(hearts);
                break;
            case DIAMONDS:
                System.out.print(diamonds);
                break;
            case CLUBS:
                System.out.print(clubs);
                break;
        }
        System.out.print(card.getValue());
        }

    }


