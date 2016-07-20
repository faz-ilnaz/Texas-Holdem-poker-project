package ru.innopolis.university.summerbootcamp2016.poker;

import java.util.Scanner;

public class Gameplay {

    static Scanner scanner = new Scanner(System.in);;

    private Gameplay() {
    }

    static void play(Player player, int currentPlayerId) {
//        if (currentPlayerId == Game.realPlayerId) {
            Gameplay.playMyself(player);
//        }
//        else {
//            Gameplay.playAI(player);
//        }
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
}
