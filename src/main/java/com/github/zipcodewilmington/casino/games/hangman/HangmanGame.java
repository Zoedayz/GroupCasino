package com.github.zipcodewilmington.casino.games.hangman;

import java.util.HashSet;
import java.util.Set;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;

public class HangmanGame implements GameInterface {
    private HangmanPlayer player;
    private String secretWord;
    private Set<Character> guessedLetters = new HashSet<>();
    private int maxIncorrectGuesses = 6;
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
        secretWord = getRandomWord();
        guessedLetters.clear();
        maxIncorrectGuesses = 6;

        System.out.println("Welcome to Hangman, " + player.getName() + "!");

        while (maxIncorrectGuesses > 0 && !isWon()) {
            System.out.println(STAGES[6 - maxIncorrectGuesses]);
            displayWord();
            System.out.println("Remaining attempts: " + maxIncorrectGuesses);
            System.out.println("Guessed letters: " + guessedLetters);

            char guess = player.guessLetter();

            if (guessLetter(guess)) {
                System.out.println("Good guess!");
            } else {
                System.out.println("Wrong! -1 attempt");
            }
        }

        System.out.println(STAGES[6 - maxIncorrectGuesses]);

        if (isWon()) {
            System.out.println("You win! The word was: " + secretWord);
        } else {
            System.out.println("Game over! The word was: " + secretWord);
        }
    }

    public boolean guessLetter(char c) {
        guessedLetters.add(c);
        if (secretWord.indexOf(c) >= 0) {
            return true;
        }
        maxIncorrectGuesses--;
        return false;
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
        System.out.println("Word: " + sb.toString().trim());
    }
}
