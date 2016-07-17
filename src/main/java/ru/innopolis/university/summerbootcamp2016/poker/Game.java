package ru.innopolis.university.summerbootcamp2016.poker;

public class Game {

    static int amountCardsTable;
    static int maxStake=0; //the highest stake of the round

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


    public int OlderCardStraight(int[] values){
        for(int i=12;i>=4;i--){
            if(values[i]>0 && values[i-1]>0 && values[i-2]>0 && values[i-3]>0 && values[i-4]>0){
                return i;
            }
        }
        return -1;
    }
    public int CombinationChecker(Deck thisDeck, int playerId){


        int[] valueArray = new int[13];
        int[] suitArray = new int[4];
        return 1;
    }
}
