package com.github.zipcodewilmington.casino.games.numberguess;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.PlayerInterface;
import java.util.Scanner;

public class NumberGuessPlayer implements PlayerInterface {

    private final CasinoAccount account; // Standardized name
    private final Scanner scanner;

    public NumberGuessPlayer(CasinoAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Logic for the player to input a guess.
     */
    public int makeGuess() {
        System.out.print("Enter your guess (1–100): ");
        // Check if input is an integer to avoid crashes
        while (!scanner.hasNextInt()) {
            System.out.print("That's not a number! Try again: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public CasinoAccount getAracadeAccount() {
        // Changed from 'casinoAccount' to 'account' to match the field above
        return account;
    }

    @Override
    public void play() {
        // Consolidated the two play() methods into one
        System.out.println("Starting Number Guess Game...");
        
        // Example logic:
        int userGuess = makeGuess();
        System.out.println("You guessed: " + userGuess);
        
    }
}