package ru.innopolis.university.summerbootcamp2016.poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    static int amountCardsTable;
    static long maxStake=0; //the highest stake of the round

    public static final long SMALL_BLIND = 1;
    public static final int AMOUNT_OF_PLAYERS = 3;
    public static final int DEFALT_BALANCE = 100;

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

        List<Player> players = new ArrayList<Player>(AMOUNT_OF_PLAYERS);
        for (int i = 2; i < AMOUNT_OF_PLAYERS + 2; i++) {
            Player player = new Player();
            player.setId(i);
            player.setName("Player" + i);
            player.setBalance(DEFALT_BALANCE);
            players.add(player);
        }

        Random random = new Random();
        int realPlayerId = random.nextInt(AMOUNT_OF_PLAYERS);

        players.get(0).makeStake(SMALL_BLIND);
        players.get(1).makeStake(2 * SMALL_BLIND);




        currentTable.takeFlop(currentDeck);

        currentTable.showTable();
        System.out.println(amountCardsTable);


    }


    public int olderCardStraight(int[] values){
        for(int i=12;i>=4;i--){
            if(values[i]>0 && values[i-1]>0 && values[i-2]>0 && values[i-3]>0 && values[i-4]>0){
                return i;
            }
        }
        return -1;
    }
    public int numberOfTwos(int[] values){
        int numberOfTwos=0;
        for(int i=12;i>=0;i--){
            if(values[i]==2){
                numberOfTwos++;
            }
        }
        return numberOfTwos;
    }

    public int combinationChecker(Deck thisDeck, int playerId){
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

        int highStraight = olderCardStraight(valueArray);

        if(maxS>=5) {
            if (highStraight == 12) {
                return 10;
            } else if (highStraight>-1){
                return 9;
            }
            else {
                return 6;
            }
        }
        else{
            switch(maxV){
                case 4:
                    return 8;
                case 3:
                    if(numberOfTwos(valueArray)>=1){
                        return 7;
                    }
                    else {
                        return 4;
                    }
                case 2:
                    if(numberOfTwos(valueArray)>=2){
                        return 3;
                    }
                    else {
                        return 2;
                    }
                case 1:
                    if(highStraight>-1){
                        return 5;
                    }
                    else{
                        return 1;
                    }


            }
        }
        return 1;
    }
}
