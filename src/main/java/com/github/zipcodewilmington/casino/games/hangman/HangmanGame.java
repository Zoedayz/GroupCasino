package com.github.zipcodewilmington.casino.games.hangman;

import java.util.HashSet;
import java.util.Set;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

public class HangmanGame implements GameInterface {
    private HangmanPlayer player;
    private String secretWord;
    private Set<Character> guessedLetters = new HashSet<>();
    private int maxIncorrectGuesses = 6;
    private final IOConsole console = new IOConsole(AnsiColor.GREEN);
    private String getRandomWord() {
        try {
            java.net.URL url = new java.net.URL("https://random-word-api.herokuapp.com/word?number=1");
            String response = new String(url.openStream().readAllBytes());
            return response.replaceAll("[\\[\\]\"]", ""); // Clean up the response
        } catch (Exception e) {
            String[]fallback = {"casino", "hangman", "Haha"};
            return fallback[(int)(Math.random() * fallback.length)];
        }
    }

    private String[] STAGES = {
        // 0 wrong
        "  +---+\n" +
        "  |   |\n" +
        "      |\n" +
        "      |\n" +
        "      |\n" +
        "      |\n" +
        "=========",
        // 1 wrong
        "  +---+\n" +
        "  |   |\n" +
        "  O   |\n" +
        "      |\n" +
        "      |\n" +
        "      |\n" +
        "=========",
        // 2 wrong
        "  +---+\n" +
        "  |   |\n" +
        "  O   |\n" +
        "  |   |\n" +
        "      |\n" +
        "      |\n" +
        "=========",
        // 3 wrong
        "  +---+\n" +
        "  |   |\n" +
        "  O   |\n" +
        " /|   |\n" +
        "      |\n" +
        "      |\n" +
        "=========",
        // 4 wrong
        "  +---+\n" +
        "  |   |\n" +
        "  O   |\n" +
        " /|\\  |\n" +
        "      |\n" +
        "      |\n" +
        "=========",
        // 5 wrong
        "  +---+\n" +
        "  |   |\n" +
        "  O   |\n" +
        " /|\\  |\n" +
        " /    |\n" +
        "      |\n" +
        "=========",
        // 6 wrong - dead
        "  +---+\n" +
        "  |   |\n" +
        "  O   |\n" +
        " /|\\  |\n" +
        " / \\  |\n" +
        "      |\n" +
        "========="
    };

    @Override
    public void add(PlayerInterface player) {
        this.player = (HangmanPlayer) player;
    }

    @Override
    public void remove(PlayerInterface player) {
        this.player = null;
    }

    @Override
    public void run() {
        secretWord = pickWord();
        guessedLetters.clear();
        maxIncorrectGuesses = 6;

        console.println("Welcome to Hangman, " + player.getName() + "!");

        while (!isGameOver()) {
            console.println(STAGES[6 - maxIncorrectGuesses]);
            displayWord();
            console.println("Remaining attempts: " + maxIncorrectGuesses);
            console.println("Guessed letters: " + guessedLetters);

            char guess = player.guessLetter();

            if (checkGuess(guess)) {
                console.println("Good guess!");
            } else {
                console.println("Wrong! -1 attempt");
            }
        }

        console.println(STAGES[6 - maxIncorrectGuesses]);

        if (isWon()) {
            console.println("You win! The word was: " + secretWord);
        } else {
            console.println("Game over! The word was: " + secretWord);
        }
    }

    private String pickWord() {
        return getRandomWord();
    }

    public boolean checkGuess(char c) {
        guessedLetters.add(c);
        if (secretWord.indexOf(c) >= 0) {
            return true;
        }
        maxIncorrectGuesses--;
        return false;
    }

    public boolean isGameOver() {
        return isWon() || isLost();
    }

    public boolean isLost() {
        return maxIncorrectGuesses <= 0;
    }

    public boolean isWon() {
        for (char c : secretWord.toCharArray()) {
            if (!guessedLetters.contains(c)) return false;
        }
        return true;
    }

    public void displayWord() {
        StringBuilder sb = new StringBuilder();
        for (char c : secretWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                sb.append(c).append(" ");
            } else {
                sb.append("_ ");
            }
        }
        console.println("Word: " + sb.toString().trim());
    }
}
