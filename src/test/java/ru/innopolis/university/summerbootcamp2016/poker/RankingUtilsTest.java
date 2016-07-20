package ru.innopolis.university.summerbootcamp2016.poker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static ru.innopolis.university.summerbootcamp2016.poker.PokerHand.PAIR;
import static ru.innopolis.university.summerbootcamp2016.poker.PokerHand.THREE_OF_A_KIND;
import static ru.innopolis.university.summerbootcamp2016.poker.PokerHand.TWO_PAIR;

public class RankingUtilsTest {

    private static final int AMOUNT_OF_PLAYERS = 3;
    private static final int DEFAULT_BALANCE = 100;

    @Test
    public void combinationCheckerTest() {
        Deck currentDeck = new Deck();

        Integer[][] deckData = {
                {0, 0, 0, 0, 1, 0, 1, 0, 3, 0, 4, 0, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 0, 2},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 1, 0, 2}
        };

        currentDeck.deck = deckData;

        assertEquals(PAIR.getStrength(), RankingUtils.combinationChecker(currentDeck, 2));
        assertEquals(THREE_OF_A_KIND.getStrength(), RankingUtils.combinationChecker(currentDeck, 3));
        assertEquals(TWO_PAIR.getStrength(), RankingUtils.combinationChecker(currentDeck, 4));


    }
}
