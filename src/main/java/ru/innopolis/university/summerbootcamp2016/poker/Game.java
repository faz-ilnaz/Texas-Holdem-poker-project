package ru.innopolis.university.summerbootcamp2016.poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    static int amountCardsTable;
    static long maxStake = 0; //the highest stake of the round

    static Random random = new Random();
    static int realPlayerId;
    static Scanner scanner = new Scanner(System.in);

    public static final long SMALL_BLIND = 1;
    public static final int AMOUNT_OF_PLAYERS = 3;
    public static final int DEFAULT_BALANCE = 100;


    public static void main(String[] args) {
        Deck currentDeck = new Deck();
        Table currentTable = new Table();
        Bank bank = new Bank();
        amountCardsTable = 0;

        /*
         * Testing of correct working of objects, their methods and collaboration of objects
         */

        List<Player> players = new ArrayList<>(AMOUNT_OF_PLAYERS);
        for (int i = 2; i < AMOUNT_OF_PLAYERS + 2; i++) {
            Player player = new Player();
            player.setId(i);
            player.setName("Player" + i);
            player.setBalance(DEFAULT_BALANCE);
            player.resetPlayingStatus();
            player.setStrength(0);
            player.takeCards(currentDeck);
            players.add(player);
        }


        int dealerId = random.nextInt(AMOUNT_OF_PLAYERS);
        players.get(dealerId).setDealer(true);

        //Show init info
        System.out.println("Init player's info:");
        showPlayerInfo(players);


        int smallBlindId = (dealerId + 1) % AMOUNT_OF_PLAYERS;
        int bigBlindId = (smallBlindId + 1) % AMOUNT_OF_PLAYERS;

        players.get(smallBlindId).makeStake(SMALL_BLIND);
        players.get(bigBlindId).makeStake(2 * SMALL_BLIND);

        //Show info after setting blinds
        System.out.println("After blind's info:");
        showPlayerInfo(players);

        realPlayerId = random.nextInt(AMOUNT_OF_PLAYERS);

        int theNextPlayerId = (bigBlindId + 1) % AMOUNT_OF_PLAYERS;

        for (int i = 0; i < 4; i++) {

            while (biddingStepIsGoingOn(theNextPlayerId, players)) {

                Player player = players.get(theNextPlayerId);

                playMyself(player);

//                if (theNextPlayerId == realPlayerId) {
//                    playMyself(player);
//                }
//                else {
//                    playAI(player);
//                }

                do {
                    theNextPlayerId = (theNextPlayerId + 1) % AMOUNT_OF_PLAYERS;
                }
                while (!players.get(theNextPlayerId).isPlaying());
            }

            int countOfActivePlayers = 0;
            for (Player player : players) {
                if (player.isPlaying()) {
                    countOfActivePlayers++;
                }
            }

            bank.collectStakes(players);

            if (countOfActivePlayers < 2) {
                break;
            }

            // was a preflop
            if (i == 0) {
                currentTable.takeFlop(currentDeck);
            } else if (i < 3) {
                // river, turn
                currentTable.takeCard(currentDeck);
            }

            theNextPlayerId = (dealerId + 1) % AMOUNT_OF_PLAYERS;
            while (!players.get(theNextPlayerId).isPlaying()) {
                theNextPlayerId = (theNextPlayerId + 1) % AMOUNT_OF_PLAYERS;
            }

            maxStake = 0;
            resetPlayersStatuses(players);
            currentTable.showTable();
        }

        showArrayDeck(currentDeck);

        openCards(players, currentDeck);
        List<Player> winners = RankingUtils.determineWinners(players);
        long reward = bank.getReward();

        System.out.println("--- WINNERS ---");
        for (Player winner : winners) {
            winner.setBalance(winner.getBalance() + reward / winners.size());
            System.out.println(winner.getName());
        }

//
//        //Printing player's info
//
//        //Show info after some actions
//        System.out.println("After actions info:");
//        showPlayerInfo(players);
//
//        bank.collectStakes(players);
//        System.out.println("Bank Balance after collecting Stakes: " + bank.getBankBalance());

//        currentTable.takeFlop(currentDeck);


        //Testing things
        //testCombinationChecker(3);
        //currentTable.showTable();
        //Testing things
        //testCombinationChecker(3);
//        currentTable.showTable();
        //System.out.println(amountCardsTable);
//        System.out.println("Test confidence:");
//        testConfidence(6);
    }

    static void playMyself(Player player) {
        boolean status = false;
        System.out.println(player.getName() + ": ");

        while (!status) {
            String[] commands = scanner.nextLine().trim().split(" ");
            String command = commands[0];
            long value = 0;
            if (commands.length > 1) {
                value = Long.parseLong(commands[1]);
            }

            switch (command) {
                case "call":
                    status = player.call();
                    break;
                case "raise":
                    status = player.raise(value);
                    break;
                case "check":
                    status = player.check();
                    break;
                case "fold":
                    status = player.fold();
                    break;
                default:
                    System.out.println("Incorrect command!");
            }
        }

    }

    static void playAI(Player player) {
        if (!player.call()) {
            player.check();
        }
    }


    static boolean biddingStepIsGoingOn(int theNextPlayerId, List<Player> players) {
        for (Player player : players) {
            if (player.getPlayingStatus() == 0 || (player.getPlayingStatus() != -1 && player.getStake() != Game.maxStake)) {
                return true;
            }
        }

        return false;

    }


    //This function shows deck array
    public static void showArrayDeck(Deck deck) {
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

    //count the strength of all combinations of Players still playing
    public static void openCards(List<Player> allPlayers, Deck thisDeck) {
        for (int i = 0; i < allPlayers.size(); i++) {
            if (allPlayers.get(i).isPlaying()) {
                allPlayers.get(i).setStrength(RankingUtils.combinationChecker(thisDeck, allPlayers.get(i).getId()));  //it works!
            }
        }
    }


    static void resetPlayersStatuses(List<Player> players) {
        for (Player player : players) {
            if (player.getPlayingStatus() != -1) {
                player.resetPlayingStatus();
            }
            player.setStrength(0);
        }
    }

}
