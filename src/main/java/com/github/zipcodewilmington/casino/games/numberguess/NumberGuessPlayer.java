package com.github.zipcodewilmington.casino.games.numberguess;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

public class NumberGuessPlayer implements PlayerInterface {
    private CasinoAccount casinoAccount;
    private int lastGuess;
    private int totalGuesses;
    private final IOConsole console = new IOConsole(AnsiColor.GREEN);

    public NumberGuessPlayer(CasinoAccount casinoAccount) {
        this.casinoAccount = casinoAccount;
        this.lastGuess = 0;
        this.totalGuesses = 0;
    }

    // play() drives the player's guessing turn
    // returns the player's integer guess
    public <SomeReturnType> SomeReturnType play() {
        return null; // turn logic handled in NumberGuessGame
    }

    public void takeTurn() {
        lastGuess = makeGuess();
        totalGuesses++;
        console.println("You guessed: %d", lastGuess);
    }

    public int makeGuess() {
        return console.getIntInput("Enter your guess (1-100): ");
    }

    public void onCorrectGuess() {
        console.println("CONGRATULATIONS %s! You guessed it in %d guesses.",
            casinoAccount.getUsername(), totalGuesses);
    }

    public void onTooLow() {
        console.println("Too Low!");
    }

    public void onTooHigh() {
        console.println("Too High!");
    }

    public void onOutOfGuesses(int number) {
        console.println("Out of guesses, %s! The number was %d.",
            casinoAccount.getUsername(), number);
    }

    public String askPlayAgain() {
        return console.getStringInput("Play another round? (y/n): ");
    }

    @Override
    public CasinoAccount getArcadeAccount() {
        return casinoAccount;
    }

    public CasinoAccount fetchCasinoAccount() {
        return casinoAccount;
    }

    public int getLastGuess()       { return lastGuess; }
    public int getTotalGuesses()    { return totalGuesses; }

    public void resetGuesses() {
        lastGuess = 0;
        totalGuesses = 0;
    }
}