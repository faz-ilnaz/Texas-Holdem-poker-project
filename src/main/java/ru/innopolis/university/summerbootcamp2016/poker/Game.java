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

    //This is a test for combinationChecker. It creates new deck and takes 7 cards from it (5 for table, 2 for player). Then checks combinations possible.
    public static void testCombinationChecker(int id) {
        Deck deckTest = new Deck();
        UI.displayCard(deckTest.getCard(1));
        UI.displayCard(deckTest.getCard(1));
        UI.displayCard(deckTest.getCard(1));
        UI.displayCard(deckTest.getCard(1));
        UI.displayCard(deckTest.getCard(1));
        UI.displayCard(deckTest.getCard(id));
        UI.displayCard(deckTest.getCard(id));
        System.out.println();
        int combination = combinationChecker(deckTest, id);
        PokerHand testHand = PokerHand.values()[10-combination];
        System.out.println(combination);
        System.out.println(testHand);
    }

    //This functiob shows deck array
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
        Bank bank = new Bank();
        amountCardsTable = 0;

        List<Player> players = new ArrayList<Player>(AMOUNT_OF_PLAYERS);
        for (int i = 2; i < AMOUNT_OF_PLAYERS + 2; i++) {
            Player player = new Player();
            player.setId(i);
            player.setName("Player" + i);
            player.setBalance(DEFALT_BALANCE);
            player.takeCards(currentDeck);
            players.add(player);
        }

        Random random = new Random();
        int realPlayerId = random.nextInt(AMOUNT_OF_PLAYERS);

        players.get(0).makeStake(SMALL_BLIND);
        players.get(1).makeStake(2 * SMALL_BLIND);

        players.get(2).call();
        players.get(0).raise(19 );
        players.get(1).call();
        players.get(2).call();
        bank.collectStakes(players);
        System.out.println("Bank Balance: " +bank.getBankBalance());
        //Printing player's info
        for (int i = 0; i < AMOUNT_OF_PLAYERS; i++) {
            System.out.println("Player "+players.get(i).getId()+" " + players.get(i).getStake() + " "+ players.get(i).getBalance());
            for(int j =0; j < 2; j++)
                System.out.println(players.get(i).getCards().get(j).getSuit()+" "+players.get(i).getCards().get(j).getValue());
        }

        currentTable.takeFlop(currentDeck);

        //Testing things
        //testCombinationChecker(3);
        //currentTable.showTable();
        //System.out.println(amountCardsTable);
    }


    //It checks Straight, and if it exists function returns the older card of Straight
    public static int olderCardStraight(int[] values){
        for(int i=12;i>=4;i--){
            if(values[i]>0 && values[i-1]>0 && values[i-2]>0 && values[i-3]>0 && values[i-4]>0){
                return i;
            }
        }
        return -1;
    }
    //It returns the quantity of pairs in combination
    public static int numberOfTwos(int[] values){
        int numberOfTwos=0;
        for(int i=12;i>=0;i--){
            if(values[i]==2){
                numberOfTwos++;
            }
        }
        return numberOfTwos;
    }

    //It returns the strenght of best possible combination. 10 - Royal flush, 1 - high card
    public static int combinationChecker(Deck thisDeck, int playerId){
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

        int strengthOfCombination=0;
        switch (maxS){
            case 5:
            if (highStraight == 12) {
                if(strengthOfCombination<10)
                    strengthOfCombination = 10;
            } else if (highStraight>-1){
                if(strengthOfCombination<9)
                    strengthOfCombination=9;
            }
            else {
                if(strengthOfCombination<6)
                    strengthOfCombination=6;
            }
            default:
            switch(maxV){
                case 4:
                    if(strengthOfCombination<8)
                        strengthOfCombination=8;
                case 3:
                    if(numberOfTwos(valueArray)>=1){
                        if(strengthOfCombination<7)
                            strengthOfCombination=7;
                    }
                    else {
                        if(strengthOfCombination<4)
                            strengthOfCombination=4;
                    }
                case 2:
                    if(numberOfTwos(valueArray)>=2){
                        if(strengthOfCombination<3)
                            strengthOfCombination=3;
                    }
                    else {
                        if(strengthOfCombination<2)
                            strengthOfCombination=2;
                    }
                case 1:
                    if(highStraight>-1){
                        if(strengthOfCombination<5)
                            strengthOfCombination=5;
                    }
                    else{
                        if(strengthOfCombination<1)
                        strengthOfCombination= 1;
                    }


            }

        }

        return strengthOfCombination;
    }

    //count the strength of all combinations of Players still playing
    public static void openCards(ArrayList<Player> allPlayers,Deck thisDeck){
        for(int i=0;i<allPlayers.size();i++) {
            if (allPlayers.get(i).isPlaying()) {
                allPlayers.get(i).setStrength(combinationChecker(thisDeck, allPlayers.get(i).getId()));
            }
        }
    }

    //returns list of winners (or list with one winner) in this turn
    public static ArrayList<Player> determineWinners(ArrayList<Player> allPlayers){
        ArrayList<Player> winners = new ArrayList<Player>();
        int maxStrength=0;
        int currentStrength;
        for(int i=0;i<allPlayers.size();i++){
            currentStrength = allPlayers.get(i).getStrength();
            if(currentStrength>maxStrength){
                maxStrength=currentStrength;
                winners.clear();
                winners.add(allPlayers.get(i));
            }
            else
                if(currentStrength==maxStrength){
                    winners.add(allPlayers.get(i));
                }
        }
        return winners;
    }

}
