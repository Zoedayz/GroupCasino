package com.github.zipcodewilmington.casino.games.numberguess;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

public class NumberGuessPlayer implements PlayerInterface {

    private final CasinoAccount account;
    private final IOConsole console = new IOConsole(AnsiColor.GREEN);

    public NumberGuessPlayer(CasinoAccount account) {
        this.account = account;
    }

    public int makeGuess() {
        return console.getIntegerInput("Enter your guess (1-100): ");
    }

    @Override
    public CasinoAccount getArcadeAccount() {
        return account;
    }

    @Override
    public <SomeReturnType> SomeReturnType play() {
        return null; // turn logic handled in NumberGuessGame
    }
}
