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
    private String[] words = {"casino", "hangman", "player"};

    @Override
    public void add(PlayerInterface player) {
        this.player = (HangmanPlayer)player;
    }

    @Override
    public void remove(PlayerInterface player) {
        this.player = null;
    }

    @Override
    public void run() {
        secretWord = words[(int)(Math.random() * words.length)];
        guessedLetters.clear();
        maxIncorrectGuesses = 6;

        System.out.println("Welcome to Hangman, " + player.getName() + "!");

        while (maxIncorrectGuesses > 0 && !isWon()) {
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