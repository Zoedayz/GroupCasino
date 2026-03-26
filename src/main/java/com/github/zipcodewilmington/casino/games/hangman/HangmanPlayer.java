package com.github.zipcodewilmington.casino.games.hangman;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

public class HangmanPlayer implements PlayerInterface {
    private String name;
    private CasinoAccount account;
    private final IOConsole console = new IOConsole(AnsiColor.GREEN);

    public HangmanPlayer(String name, CasinoAccount account) {
        this.name = name;
        this.account = account;
    }

    @Override
    public CasinoAccount getArcadeAccount() {
        return account;
    }

    @Override
    public String play() {
        return name + " is playing Hangman YAYYYYY!";
    }

    public String getName() { return name; }

    public char guessLetter() {
        String input = console.getStringInput("Enter a letter to guess: ");
        return input.charAt(0);
    }
}

    