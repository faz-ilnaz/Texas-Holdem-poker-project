package ru.innopolis.university.summerbootcamp2016.poker;

import java.util.ArrayList;
import java.util.List;

/*
    Under you can find helping methods and combination checker methods
 */
class RankingUtils {
    private RankingUtils() {
    }


    //It returns the strength of best possible combination. 10 - Royal flush, 1 - high card
    static int combinationChecker(Deck thisDeck, int playerId) {
        int[] valueArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] suitArray = new int[]{0, 0, 0, 0};
        int[] max = combinationArray(valueArray, suitArray, thisDeck, playerId);
        int maxS = max[0];
        int maxV = max[1];

        int highStraight = olderCardStraight(valueArray);

        int strengthOfCombination = 0;
        switch (maxS) {
            case 5:
                if (highStraight == 12) {
                    strengthOfCombination = rewriteH(strengthOfCombination, 10);
                } else if (highStraight > -1) {
                    strengthOfCombination = rewriteH(strengthOfCombination, 9);
                } else {
                    strengthOfCombination = rewriteH(strengthOfCombination, 6);
                }
            default:
                switch (maxV) {
                    case 4:
                        strengthOfCombination = rewriteH(strengthOfCombination, 8);
                    case 3:
                        if (numberOfTwos(valueArray) >= 1) {
                            strengthOfCombination = rewriteH(strengthOfCombination, 7);
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
                            strengthOfCombination = rewriteH(strengthOfCombination, 5);
                        } else {
                            strengthOfCombination = rewriteH(strengthOfCombination, 1);
                        }
                }
        }
        return strengthOfCombination;
    }

    //It just rewrite abstract variable only if future value is bigger
    private static int rewriteH(int h, int valueToCompare) {
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
        int combination = RankingUtils.combinationChecker(deckTest, id);
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
        AI.makeDecision(testPlayer, deckTest);

    }


    //returns list of winners (or list with one winner) in this turn
    static List<Player> determineWinners(List<Player> allPlayers) {
        List<Player> winners = new ArrayList<Player>();
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
