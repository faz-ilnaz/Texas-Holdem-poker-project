package ru.innopolis.university.summerbootcamp2016.poker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RankingUtilsTest {

    private static final int AMOUNT_OF_PLAYERS = 3;
    private static final int DEFAULT_BALANCE = 100;

    @Test
    public void combinationCheckerTest() {
        Deck deck = new Deck();
        Table table = new Table();

        Integer[][] deckData = {
                {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 4, 0, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 3, 2},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2}
        };

        int playerId = 3;

        int strength = RankingUtils.combinationChecker(deck, playerId);

        assertEquals(2, strength);
    }
}
