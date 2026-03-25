package com.github.zipcodewilmington.casino.games.roulette;

import java.util.Scanner;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;

public class RouletteGame implements GameInterface {

    private RoulettePlayer player;
    private final Scanner scanner;
    private final RouletteWheel wheel; // Your Wheel class integrated here

    public RouletteGame() {
        this.scanner = new Scanner(System.in);
        this.wheel = new RouletteWheel(); // Initialize the wheel
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
        if (player == null) return;

        System.out.println("\n==== ROULETTE ====");
        System.out.println("Balance: $" + player.getAccount().getBalance());
        System.out.print("Enter bet amount: $");
        double betAmount = scanner.nextDouble();

        if (betAmount <= 0 || betAmount > player.getAccount().getBalance()) {
            System.out.println("Invalid bet amount. Returning to lobby...");
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

        // Process user input for the bet
        switch (choice) {
            case 1:
                System.out.print("Pick a number (0-36): ");
                betNumber = scanner.nextInt();
                if (betNumber < 0 || betNumber > 36) return;
                betType = "number";
                break;
            case 2:
                System.out.print("Type 'red' or 'black': ");
                betType = scanner.next().toLowerCase();
                break;
            case 3:
                System.out.print("Type 'even' or 'odd': ");
                betType = scanner.next().toLowerCase();
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        // 1. Spin the wheel
        System.out.println("\n--- Spinning the wheel... ---");
        wheel.spin(); 
        
        int resultNum = wheel.getLastSpinNumber();
        String resultColor = wheel.getLastSpinColor();

        System.out.println("RESULT: " + resultColor + " " + resultNum);

        // 2. Check for win using the Wheel's state
        if (checkWin(betType, betNumber)) {
            double winnings = calculateWinnings(betAmount, betType);
            player.getAccount().depositToBalance(winnings);
            System.out.println("YOU WIN! + $" + winnings);
        } else {
            player.getAccount().withdrawBalance(betAmount);
            System.out.println("You lost. - $" + betAmount);
        }

        System.out.println("New balance: $" + player.getAccount().getBalance());
    }

    private boolean checkWin(String betType, int betNumber) {
        switch (betType) {
            case "number": 
                return wheel.getLastSpinNumber() == betNumber;
            case "red":    
                return wheel.getLastSpinColor().equalsIgnoreCase("red");
            case "black":  
                return wheel.getLastSpinColor().equalsIgnoreCase("black");
            case "even":   
                return wheel.lastSpinWasEven();
            case "odd":    
                return wheel.lastSpinWasOdd();
            default:       
                return false;
        }
    }

    private double calculateWinnings(double betAmount, String betType) {
        return betType.equals("number") ? betAmount * 35 : betAmount * 2;
    }

    public void removePlayer(PlayerInterface player) {

        this.player = null; // Important for Garbage Collection requirement

        if (scanner != null) {
            scanner.close();
        }
        this.player = null;

    }
}