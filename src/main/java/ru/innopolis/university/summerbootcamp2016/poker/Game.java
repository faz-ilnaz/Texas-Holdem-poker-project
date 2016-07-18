package ru.innopolis.university.summerbootcamp2016.poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    static int amountCardsTable;
    static long maxStake = 0; //the highest stake of the round

    public static final long SMALL_BLIND = 1;
    public static final int AMOUNT_OF_PLAYERS = 3;
    public static final int DEFALT_BALANCE = 100;


    public static void main(String[] args) {
        Deck currentDeck = new Deck();
        Table currentTable = new Table();
        Bank bank = new Bank();
        amountCardsTable = 0;

        /*
         * Testing of correct working of objects, their methods and collaboration of objects
         */

        List<Player> players = new ArrayList<Player>(AMOUNT_OF_PLAYERS);
        for (int i = 2; i < AMOUNT_OF_PLAYERS + 2; i++) {
            Player player = new Player();
            player.setId(i);
            player.setName("Player" + i);
            player.setBalance(DEFALT_BALANCE);
            player.takeCards(currentDeck);
            players.add(player);
        }

        //Show init info
        System.out.println("Init player's info:");
        showPlayerInfo(players);

        Random random = new Random();
        int realPlayerId = random.nextInt(AMOUNT_OF_PLAYERS);

        players.get(0).makeStake(SMALL_BLIND);
        players.get(1).makeStake(2 * SMALL_BLIND);

        //Show info after setting blinds
        System.out.println("After blind's info:");
        showPlayerInfo(players);

        players.get(2).call();
        players.get(0).raise(20);
        players.get(1).call();
        players.get(2).call();
        players.get(0).check();

        //Printing player's info

        //Show info after some actions
        System.out.println("After actions info:");
        showPlayerInfo(players);

        bank.collectStakes(players);
        System.out.println("Bank Balance after collecting Stakes: " + bank.getBankBalance());

        currentTable.takeFlop(currentDeck);


        //Testing things
        //testCombinationChecker(3);
        //currentTable.showTable();
        //Testing things
        //testCombinationChecker(3);
        currentTable.showTable();
        //System.out.println(amountCardsTable);
        System.out.println("Test confidence:");
        testConfidence(6);
    }

    /*
        Under you can find helping methods and combination checker methods
     */

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
        PokerHand testHand = PokerHand.values()[10 - combination];
        System.out.println(combination);
        System.out.println(testHand);
    }

    //This is a test for calculating confidence of AI. It creates new deck and takes 7 cards from it (3-5 for table, 2 for player). Then calculates confidence.
    public static void testConfidence(int id) {
        Deck deckTest = new Deck();
        Player testPlayer = new Player();
        testPlayer.setId(id);
        UI.displayCard(deckTest.getCard(1));
        UI.displayCard(deckTest.getCard(1));
        UI.displayCard(deckTest.getCard(1));
        UI.displayCard(deckTest.getCard(1));
        UI.displayCard(deckTest.getCard(1));
        UI.displayCard(deckTest.getCard(id));
        UI.displayCard(deckTest.getCard(id));
        System.out.println();
        AI.makeDecision(testPlayer,deckTest);

    }

    //This function shows deck array
    public void showArrayDeck(Deck deck) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                System.out.print(deck.deck[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void showPlayerInfo(List<Player> players) {
        for (int i = 0; i < AMOUNT_OF_PLAYERS; i++) {
            players.get(i).printInfo();
            players.get(i).printCards();
        }
    }


    //It checks Straight, and if it exists function returns the older card of Straight
    public static int olderCardStraight(int[] values) {
        for (int i = 12; i >= 4; i--) {
            if (values[i] > 0 && values[i - 1] > 0 && values[i - 2] > 0 && values[i - 3] > 0 && values[i - 4] > 0) {
                return i;
            }
        }
        return -1;
    }

    //It returns the quantity of pairs in combination
    public static int numberOfTwos(int[] values) {
        int numberOfTwos = 0;
        for (int i = 12; i >= 0; i--) {
            if (values[i] == 2) {
                numberOfTwos++;
            }
        }
        return numberOfTwos;
    }

    // It counts and marks cards that are used in combination checker. Returns max cards of one suit and one value
    public static int[] combinationArray(int[] vArray, int[] sArray, Deck thisDeck,int playerId) {
        int maxS = 0, maxV = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                if (thisDeck.deck[i][j] == 1 || thisDeck.deck[i][j] == playerId) {
                    sArray[i]++;
                    vArray[j]++;
                    if (maxS < sArray[i]) {
                        maxS = sArray[i];
                    }
                    if (maxV < vArray[j]) {
                        maxV = vArray[j];
                    }
                }
            }
        }
        int[] max = new int[]{maxS,maxV};
        return max;
    }

    //It just rewrite abstract variable only if future value is bigger
    public static int rewriteH(int h, int valueToCompare){
        if(h<valueToCompare){
            return valueToCompare;
        }
        else
            return h;
    }

    //It returns the strength of best possible combination. 10 - Royal flush, 1 - high card
    public static int combinationChecker(Deck thisDeck, int playerId) {
        int[] valueArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] suitArray = new int[]{0, 0, 0, 0};
        int[] max = combinationArray(valueArray,suitArray,thisDeck,playerId);
        int maxS=max[0];
        int maxV=max[1];

        int highStraight = olderCardStraight(valueArray);

        int strengthOfCombination = 0;
        switch (maxS) {
            case 5:
                if (highStraight == 12) {
                    strengthOfCombination = rewriteH(strengthOfCombination,10);
                } else if (highStraight > -1) {
                    strengthOfCombination = rewriteH(strengthOfCombination, 9);
                } else {
                    strengthOfCombination = rewriteH(strengthOfCombination,6);
                }
            default:
                switch (maxV) {
                    case 4:
                        strengthOfCombination = rewriteH(strengthOfCombination, 8);
                    case 3:
                        if (numberOfTwos(valueArray) >= 1) {
                            strengthOfCombination = rewriteH(strengthOfCombination,7);
                        } else {
                            strengthOfCombination = rewriteH(strengthOfCombination, 4);
                        }
                    case 2:
                        if (numberOfTwos(valueArray) >= 2) {
                            strengthOfCombination = rewriteH(strengthOfCombination, 3);
                        } else {
                            strengthOfCombination = rewriteH(strengthOfCombination, 2);
                        }
                    case 1:
                        if (highStraight > -1) {
                            strengthOfCombination = rewriteH(strengthOfCombination,5);
                        } else {
                            strengthOfCombination = rewriteH(strengthOfCombination, 1);
                        }
                }
        }
        return strengthOfCombination;
    }

    //count the strength of all combinations of Players still playing
    public static void openCards(ArrayList<Player> allPlayers, Deck thisDeck) {
        for (int i = 0; i < allPlayers.size(); i++) {
            if (allPlayers.get(i).isPlaying()) {
                allPlayers.get(i).setStrength(combinationChecker(thisDeck, allPlayers.get(i).getId()));  //it works!
            }
        }
    }

    //returns list of winners (or list with one winner) in this turn
    public static ArrayList<Player> determineWinners(ArrayList<Player> allPlayers) {
        ArrayList<Player> winners = new ArrayList<Player>();
        int maxStrength = 0;
        int currentStrength;
        for (int i = 0; i < allPlayers.size(); i++) {
            currentStrength = allPlayers.get(i).getStrength();
            if (currentStrength > maxStrength) {
                maxStrength = currentStrength;
                winners.clear();
                winners.add(allPlayers.get(i));
            } else if (currentStrength == maxStrength) {
                winners.add(allPlayers.get(i));
            }
        }
        return winners;
    }

}
