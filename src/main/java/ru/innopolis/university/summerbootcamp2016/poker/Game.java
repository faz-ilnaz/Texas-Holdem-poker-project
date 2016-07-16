package ru.innopolis.university.summerbootcamp2016.poker;

public class Game {

    static int amountCardsTable;

    public void showArrayDeck(Deck deck) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                System.out.print(deck.deck[i][j] + " ");
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        Deck currentDeck = new Deck();
        Table currentTable = new Table();
        amountCardsTable = 0;
        currentTable.takeFlop(currentDeck);
        currentTable.showTable();
        System.out.println(amountCardsTable);


    }

    public int CombinationChecker(Deck thisDeck, int playerId){
        int[] valueArray = new int[13];
        int[] suitArray = new int[4];
        return 1;
    }
}
