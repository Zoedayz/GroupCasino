package com.github.zipcodewilmington.casino.games.roulette;

import java.util.Random;
import java.util.Scanner;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;

public class RouletteGame implements GameInterface {

    private static final int[] RED_NUMBERS = {
        1, 3, 5, 7, 9, 12, 14, 16, 18, 19,
        21, 23, 25, 27, 30, 32, 34, 36
    };

    private RoulettePlayer player;
    private Scanner scanner;

    public RouletteGame() {
        this.scanner = new Scanner(System.in);
    }

    // ── GameInterface bridge methods ──────────────────────────────────────
    @Override
    public void add(PlayerInterface player) {
        this.player = (RoulettePlayer) player;
    }

    @Override
    public void remove(PlayerInterface player) {
        removePlayer(player);
    }

    @Override
    public void run() {
        play();
    }
    // ─────────────────────────────────────────────────────────────────────

    public void addPlayer(CasinoAccount account) {
        this.player = new RoulettePlayer(account);
    }

    public void play() {
        System.out.println("\n==== ROULETTE ====");
        System.out.println("Balance: $" + player.getAccount().getBalance());
        System.out.print("Enter bet amount: $");
        double betAmount = scanner.nextDouble();

        if (betAmount <= 0) {
            System.out.println("Bet must be greater than $0. Returning to lobby...");
            return;
        }

        if (betAmount > player.getAccount().getBalance()) {
            System.out.println("Not enough funds! Returning to lobby...");
            return;
        }

        System.out.println("\nBet type:");
        System.out.println("  1. Specific number (0-36)  --  pays 35x");
        System.out.println("  2. Red / Black             --  pays 2x");
        System.out.println("  3. Even / Odd              --  pays 2x");
        System.out.print("Choose (1-3): ");
        int choice = scanner.nextInt();

        String betType = "";
        int betNumber = -1;

        switch (choice) {
            case 1:
                System.out.print("Pick a number (0-36): ");
                betNumber = scanner.nextInt();
                if (betNumber < 0 || betNumber > 36) {
                    System.out.println("Invalid number! Returning to lobby...");
                    return;
                }
                betType = "number";
                break;
            case 2:
                System.out.print("Type red or black: ");
                betType = scanner.next().toLowerCase();
                if (!betType.equals("red") && !betType.equals("black")) {
                    System.out.println("Invalid choice! Returning to lobby...");
                    return;
                }
                break;
            case 3:
                System.out.print("Type even or odd: ");
                betType = scanner.next().toLowerCase();
                if (!betType.equals("even") && !betType.equals("odd")) {
                    System.out.println("Invalid choice! Returning to lobby...");
                    return;
                }
                break;
            default:
                System.out.println("Invalid choice! Returning to lobby...");
                return;
        }

        player.setBetAmount(betAmount);
        player.setBetType(betType);
        player.setBetNumber(betNumber);

        int result = spinWheel();
        String resultColor = isRed(result) ? "Red" : (result == 0 ? "Green" : "Black");

        System.out.println("\n--- Spinning the wheel... ---");
        System.out.println("Result: " + result + " (" + resultColor + ")");

        if (isWin(result, betType, betNumber)) {
            double winnings = calculateWinnings(betAmount, betType);
            player.getAccount().depositToBalance(winnings);
            System.out.println("YOU WIN! + $" + winnings);
        } else {
            player.getAccount().withdrawBalance(betAmount);
            System.out.println("You lost. - $" + betAmount);
        }

        System.out.println("New balance: $" + player.getAccount().getBalance());
    }

    public int spinWheel() {
        return new Random().nextInt(37);
    }

    public boolean isWin(int result, String betType, int betNumber) {
        switch (betType) {
            case "number": return result == betNumber;
            case "red":    return isRed(result);
            case "black":  return !isRed(result) && result != 0;
            case "even":   return result != 0 && result % 2 == 0;
            case "odd":    return result % 2 == 1;
            default:       return false;
        }
    }

    public double calculateWinnings(double betAmount, String betType) {
        if (betType.equals("number")) {
            return betAmount * 35;
        } else {
            return betAmount * 2;
        }
    }

    public boolean isRed(int number) {
        for (int red : RED_NUMBERS) {
            if (number == red) return true;
        }
        return false;
    }

    public void removePlayer(PlayerInterface player) {
        if (scanner != null) {
            scanner.close();
        }
        this.player = null;
    }
}