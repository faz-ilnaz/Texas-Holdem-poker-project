package ru.innopolis.university.summerbootcamp2016.poker;

import java.util.*;

public class Game {

    static int amountCardsTable;
    static long maxStake = 0; //the highest stake of the round

    static Random random = new Random();
    static int realPlayerId;

    public static final long SMALL_BLIND = 1;
    public static int amountOfPlayers = 3;
    public static final int DEFAULT_BALANCE = 100;


    public static void main(String[] args) {
        Deck currentDeck = new Deck();
        Table currentTable = new Table();
        Bank bank = new Bank();
        amountCardsTable = 0;

        List<Player> players = new ArrayList<>(amountOfPlayers);
        for (int i = 2; i < amountOfPlayers + 2; i++) {
            Player player = new Player();
            player.setId(i);
            player.setName("Player" + (i - 2));
            player.setBalance(DEFAULT_BALANCE);
            player.resetPlayingStatus();
            player.setStrength(0);
            players.add(player);
        }


        int dealerId = random.nextInt(amountOfPlayers);
        realPlayerId = random.nextInt(amountOfPlayers);

        while (true) {

            System.out.println("You: " + players.get(realPlayerId).getName());
            System.out.println("Dealer:" + players.get(dealerId).getName());
            System.out.println();

            for (Player player : players) {
                player.takeCards(currentDeck);
            }

            players.get(dealerId).setDealer(true);

            //Show init info
            System.out.println("Player's info:");
//            showPlayerInfo(players);
            showPlayerInfo(players.get(realPlayerId));

            int smallBlindId = (dealerId + 1) % amountOfPlayers;
            int bigBlindId = (smallBlindId + 1) % amountOfPlayers;

            players.get(smallBlindId).makeStake(SMALL_BLIND);
            players.get(bigBlindId).makeStake(2 * SMALL_BLIND);

            //Show info after setting blinds
            System.out.println("After blind's info:");
//            showPlayerInfo(players);
            showPlayerInfo(players.get(realPlayerId));


            int theNextPlayerId = (bigBlindId + 1) % amountOfPlayers;

            for (int i = 0; i < 4; i++) {

                while (biddingStepIsGoingOn(players)) {

                    Player player = players.get(theNextPlayerId);

                    Gameplay.play(player, theNextPlayerId, currentDeck);

                    do {
                        theNextPlayerId = (theNextPlayerId + 1) % amountOfPlayers;
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

                theNextPlayerId = (dealerId + 1) % amountOfPlayers;
                while (!players.get(theNextPlayerId).isPlaying()) {
                    theNextPlayerId = (theNextPlayerId + 1) % amountOfPlayers;
                }

                maxStake = 0;
                resetPlayersStatusesAfterStage(players);
                players.get(realPlayerId).printCards();
                currentTable.showTable();
            }

//            showArrayDeck(currentDeck);

            RankingUtils.openCards(players, currentDeck);
            List<Player> winners = RankingUtils.determineWinners(players);
            long reward = bank.getReward();

            System.out.println("--- WINNERS ---");
            for (Player winner : winners) {
                winner.setBalance(winner.getBalance() + reward / winners.size());
                System.out.println(winner.getName() + ", balance: " + winner.getBalance() + ", " +
                        PokerHand.values()[(int) winner.getStrength()]);
            }


            players.get(realPlayerId).printInfo();
            System.out.println();

            pauseProg();

            checkPlayersBalance(players);

            if (amountOfPlayers < 2) {
                break;
            }
            dealerId = (dealerId + 1) % amountOfPlayers;

            currentDeck.resetDeck();
            currentTable.reset();
            resetPlayers(players);
            amountCardsTable = 0;

        }

    }


    /**
     * Returns true if there are any player who didn't made a decision in the current step
     * or there is at least one player who's stake != Game.maxStake
     *
     * @param players
     * @return
     */
    static boolean biddingStepIsGoingOn(List<Player> players) {
        int countOfActivePlayers = 0;
        for (Player player : players) {
            if (player.isPlaying()) {
                countOfActivePlayers++;
            }
        }

        if (countOfActivePlayers < 2) {
            return false;
        }

        for (Player player : players) {
            if (player.getPlayingStatus() == 0 ||
                    (player.getPlayingStatus() != -1 && player.getStake() != Game.maxStake)) {
                return true;
            }
        }

        return false;

    }

    /**
     * Remove players with zero balance
     *
     * @param players
     */
    static void checkPlayersBalance(List<Player> players) {
        for (Iterator<Player> iterator = players.iterator(); iterator.hasNext(); ) {
            Player player = iterator.next();
            if (player.getBalance() <= 0) {
                // Remove the current element from the iterator and the list.
                iterator.remove();
                amountOfPlayers--;
            }
        }
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
        for (int i = 0; i < amountOfPlayers; i++) {
            players.get(i).printInfo();
            players.get(i).printCards();
        }
    }

    public static void pauseProg() {
        System.out.println("Press enter to continue...");
        Scanner keyboard = new Scanner(System.in);
        keyboard.nextLine();
    }

    public static void showPlayerInfo(Player player) {
        player.printInfo();
        player.printCards();
        System.out.println();
    }

    static void resetPlayersStatusesAfterStage(List<Player> players) {
        for (Player player : players) {
            if (player.getPlayingStatus() != -1) {
                player.resetPlayingStatus();
            }
            player.setStrength(0);
        }
    }

    static void resetPlayers(List<Player> players) {
        for (Player player : players) {
            player.resetPlayingStatus();
            player.setStrength(0);
            player.getCards().clear();
            player.setDealer(false);
        }
    }

}
