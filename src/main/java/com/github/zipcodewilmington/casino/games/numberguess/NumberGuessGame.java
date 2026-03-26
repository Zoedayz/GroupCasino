package com.github.zipcodewilmington.casino.games.numberguess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

public class NumberGuessGame implements GameInterface {
    private final Random random;
    private List<PlayerInterface> players;
    private final IOConsole console = new IOConsole(AnsiColor.BLUE);

    public NumberGuessGame() {
        this.random = new Random();
        this.players = new ArrayList<>();
    }

    @Override
    public void add(PlayerInterface player) {
        players.add(player);
    }

    @Override
    public void remove(PlayerInterface player) {
        players.remove(player);
    }

    @Override
    public void run() {
        console.println("=== Welcome to Number Guess Game ===");

        String playAgain = "y";
        while (playAgain.equalsIgnoreCase("y")) {
            playRound();
            playAgain = console.getStringInput("\nPlay another round? (y/n): ");
        }

        console.println("Thanks for playing!");
    }

    private void playRound() {
        int number = random.nextInt(100) + 1;
        int guesses = 0;

        console.println("\nI've picked a number between 1 and 100. You have 10 guesses.");

        while (guesses < 10) {
            int guess = console.getIntInput("Enter your guess: ");
            guesses++;

            if (guess == number) {
                console.println("CONGRATULATIONS YOU WIN! You guessed it in %d guesses.", guesses);
                return;
            } else if (guess < number) {
                console.println("Too Low!");
            } else {
                console.println("Too High!");
            }

            console.println("Guesses remaining: %d", 10 - guesses);
        }

        console.println("You have run out of guesses! The number was %d.", number);
    }
}