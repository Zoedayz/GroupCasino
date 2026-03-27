package com.github.zipcodewilmington.casino.games.numberguess;

import java.util.Random;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

public class NumberGuessGame implements GameInterface {

    private NumberGuessPlayer player;
    private final IOConsole console = new IOConsole(AnsiColor.GREEN);
    private final Random random = new Random();
    private int secretNumber;

    public NumberGuessGame() {}

    @Override
    public void add(PlayerInterface player) {
        this.player = (NumberGuessPlayer) player;
    }

    @Override
    public void remove(PlayerInterface player) {
        this.player = null;
    }

    @Override
    public void run() {
        console.println("=== Welcome to Number Guess Game, %s! ===",
            player.getArcadeAccount().getUsername());

        String playAgain = "yes";
        while (playAgain.equalsIgnoreCase("yes")) {
            playRound();
            playAgain = console.getStringInput("\nPlay again? (yes/no): ");
        }

        console.println("Thanks for playing Number Guess Game!");
    }

    private void playRound() {
        generateNumber();
        int guesses = 0;
        int maxGuesses = 10;

        console.println("\nI'm thinking of a number between 1 and 100. You have %d guesses.", maxGuesses);

        while (guesses < maxGuesses) {
            int guess = player.makeGuess();
            guesses++;

            int result = checkGuess(guess);

            if (result == 0) {
                console.println("CONGRATULATIONS! You guessed it in %d guess(es)!", guesses);
                return;
            }

            giveHint(guess);

            int remaining = maxGuesses - guesses;
            if (remaining > 0) {
                console.println("%d guess(es) remaining.", remaining);
            }
        }

        console.println("Out of guesses! The number was %d.", secretNumber);
    }

    private void generateNumber() {
        secretNumber = random.nextInt(100) + 1;
    }

    /**
     * Checks the guess against the secret number.
     * @return -1 if too low, 0 if correct, 1 if too high
     */
    public int checkGuess(int guess) {
        if (guess < secretNumber) return -1;
        if (guess > secretNumber) return 1;
        return 0;
    }

    public void giveHint(int guess) {
        if (guess < secretNumber) {
            console.println("Too Low!");
        } else if (guess > secretNumber) {
            console.println("Too High!");
        }
    }
}
