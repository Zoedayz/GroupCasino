package com.github.zipcodewilmington.casino.games.hangman;
import java.util.Scanner;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.PlayerInterface;

public class HangmanPlayer implements PlayerInterface 
{
    private String name;
    private CasinoAccount account;
    private Scanner scanner = new Scanner(System.in);

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
        System.out.print("Enter a letter to guess: ");
        String input = scanner.nextLine();
        return input.charAt(0);
    }
}

    