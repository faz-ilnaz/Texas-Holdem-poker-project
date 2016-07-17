package ru.innopolis.university.summerbootcamp2016.poker;

public class Game {

    static int amountCardsTable;
    static long maxStake=0; //the highest stake of the round

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
        int[] valueArray = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] suitArray = new int[]{0,0,0,0};
        int maxS=0,maxV=0;
        for(int i=0; i<4; i++){
            for(int j=0; j<13; j++){
                if(thisDeck.deck[i][j]==1||thisDeck.deck[i][j]==playerId){
                    suitArray[i]++;
                    valueArray[j]++;
                    if(maxS<suitArray[i]){
                        maxS=suitArray[i];
                    }
                    if(maxV<valueArray[j]){
                        maxV=valueArray[j];
                    }
                }
            }
        }

        return 1;
    }
}
