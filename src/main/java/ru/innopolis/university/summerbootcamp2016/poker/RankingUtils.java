package ru.innopolis.university.summerbootcamp2016.poker;

import java.util.ArrayList;
import java.util.List;

/*
    Under you can find helping methods and combination checker methods
 */
class RankingUtils {
    private RankingUtils() {
    }

    //count the strength of all combinations of Players still playing
    public static void openCards(List<Player> allPlayers, Deck thisDeck) {
        for (int i = 0; i < allPlayers.size(); i++) {
            if (allPlayers.get(i).isPlaying()) {
                allPlayers.get(i).setStrength(RankingUtils.combinationChecker(thisDeck, allPlayers.get(i).getId()));  //it works!
            }
        }
    }


    //Adds strength according to higher card on hand
    public static float getHighCard(Deck deck, int playerId, int combination){
        float extraStrength=0f;
        switch(combination){
            case 6:
                for(int i=12;i>=0;i--) {
                    for (int k = 0; k < 4; k++) {
                        if(deck.deck[k][i]==playerId){
                            int numberOfCards=0;
                            for(int j=0;j<13;j++){
                                if(deck.deck[k][j]==1||deck.deck[k][j]==playerId){
                                    numberOfCards++;
                                }
                            }
                            if(numberOfCards>=5){
                                if(extraStrength==0) {
                                    extraStrength = extraStrength + i * 0.01f;
                                }
                                else{
                                    extraStrength = extraStrength + i * 0.0001f;
                                }
                            }
                        }
                    }
                }
                break;
            case 8:
                for(int i=12;i>=0;i--) {
                    for (int k = 0; k < 4; k++) {
                        if(deck.deck[k][i]==playerId){
                            int numberOfCards=0;
                            for(int j=0;j<4;j++){
                                if(deck.deck[j][i]==1||deck.deck[j][i]==playerId){
                                    numberOfCards++;
                                }
                            }
                            if(numberOfCards==4){
                                if(extraStrength==0) {
                                    extraStrength = extraStrength + i * 0.01f;
                                }
                                else{
                                    extraStrength = extraStrength + i * 0.0001f;
                                }
                            }
                        }
                    }
                }
                break;
            case 7:
            case 4:
            case 3:
            case 2:
                for(int i=12;i>=0;i--) {
                    for (int k = 0; k < 4; k++) {
                        if(deck.deck[k][i]==playerId){
                            int numberOfCards=0;
                            for(int j=0;j<4;j++){
                                if(deck.deck[j][i]==1||deck.deck[j][i]==playerId){
                                    numberOfCards++;
                                }
                            }
                            if(numberOfCards==3) {
                                if(extraStrength==0)
                                    extraStrength = extraStrength + i * 0.01f;
                                else
                                    extraStrength = extraStrength*0.01f + i*0.01f;
                            }
                            else
                                if(numberOfCards==2){
                                    if(extraStrength==0f ) {
                                        extraStrength = extraStrength + i * 0.0001f;
                                    }
                                    else{
                                        extraStrength = extraStrength + i * 0.000001f;
                                    }
                                }
                        }
                    }
                }
                break;
            case 1:
                for(int i=12;i>=0;i--) {
                    for (int k = 0; k < 4; k++) {
                        if (deck.deck[k][i] == playerId) {
                            if(extraStrength==0)
                            {
                                extraStrength=extraStrength+i*0.01f;
                            }
                            else{
                                extraStrength=extraStrength+i*0.0001f;
                            }
                        }
                    }
                }
            default:
        }
        return extraStrength;
    }

    //It returns the strength of best possible combination. 10 - Royal flush, 1 - high card
    static float combinationChecker(Deck thisDeck, int playerId) {
        int[] valueArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] suitArray = new int[]{0, 0, 0, 0};
        int[] max = combinationArray(valueArray, suitArray, thisDeck, playerId);
        int maxS = max[0];
        int maxV = max[1];

        int highStraight = olderCardStraight(valueArray);

        float strengthOfCombination = 0f;
        switch (maxS) {
            case 5:
                if (highStraight == 12) {
                    strengthOfCombination = rewriteH(strengthOfCombination, 10);
                } else if (highStraight > -1) {
                    strengthOfCombination = rewriteH(strengthOfCombination, 9);
                    strengthOfCombination = strengthOfCombination + highStraight*0.01f;
                } else {
                    strengthOfCombination = rewriteH(strengthOfCombination, 6)+getHighCard(thisDeck,playerId,6);
                }
            default:
                switch (maxV) {
                    case 4:
                        strengthOfCombination = rewriteH(strengthOfCombination, 8)+getHighCard(thisDeck,playerId,8);
                    case 3:
                        if (numberOfTwos(valueArray) >= 1) {
                            strengthOfCombination = rewriteH(strengthOfCombination, 7)+getHighCard(thisDeck,playerId,7);
                        } else {
                            strengthOfCombination = rewriteH(strengthOfCombination, 4)+getHighCard(thisDeck,playerId,4);
                        }
                    case 2:
                        if (numberOfTwos(valueArray) >= 2) {
                            strengthOfCombination = rewriteH(strengthOfCombination, 3)+getHighCard(thisDeck,playerId,3);
                        } else {
                            strengthOfCombination = rewriteH(strengthOfCombination, 2)+getHighCard(thisDeck,playerId,2);
                        }
                    case 1:
                        if (highStraight > -1) {
                            strengthOfCombination = rewriteH(strengthOfCombination, 5);
                            strengthOfCombination = strengthOfCombination + highStraight*0.01f;
                        } else {
                            strengthOfCombination = rewriteH(strengthOfCombination, 1)+getHighCard(thisDeck,playerId,1);
                        }
                }
        }

        return strengthOfCombination;
    }

    //It just rewrite abstract variable only if future value is bigger
    private static float rewriteH(float h, float valueToCompare) {
        if (h < valueToCompare) {
            return valueToCompare;
        } else
            return h;
    }

    //It returns the quantity of pairs in combination
    private static int numberOfTwos(int[] values) {
        int numberOfTwos = 0;
        for (int i = 12; i >= 0; i--) {
            if (values[i] == 2) {
                numberOfTwos++;
            }
        }
        return numberOfTwos;
    }

    // It counts and marks cards that are used in combination checker. Returns max cards of one suit and one value
    public static int[] combinationArray(int[] vArray, int[] sArray, Deck thisDeck, int playerId) {
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
        int[] max = new int[]{maxS, maxV};
        return max;
    }

    //It checks Straight, and if it exists function returns the older card of Straight
    private static int olderCardStraight(int[] values) {
        for (int i = 12; i >= 4; i--) {
            if (values[i] > 0 && values[i - 1] > 0 && values[i - 2] > 0 && values[i - 3] > 0 && values[i - 4] > 0) {
                return i;
            }
        }
        return -1;
    }

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
        float combination = RankingUtils.combinationChecker(deckTest, id);
        PokerHand testHand = PokerHand.values()[10 - (int)combination];
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
        AI.makeDecision(testPlayer, deckTest);

    }


    //returns list of winners (or list with one winner) in this turn
    static List<Player> determineWinners(List<Player> allPlayers) {
        List<Player> winners = new ArrayList<Player>();
        float maxStrength = 0;
        float currentStrength;
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
