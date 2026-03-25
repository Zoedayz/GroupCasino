package com.github.zipcodewilmington.casino.games.craps;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

public class CrapsPlayer implements PlayerInterface {

    private CasinoAccount casinoAccount;
    private double[] rates;
    private double wager;
    private final IOConsole console = new IOConsole(AnsiColor.YELLOW);

    public CrapsPlayer(CasinoAccount casinoAccount) {
        this.casinoAccount = casinoAccount;
        this.wager = 0;
        this.rates = new double[]{};  // TODO: define payout rates for each bet type
    }

    public void placeBet(double amount, double min, double max) {
        // TODO: validate amount against min/max and balance, then deduct from account
    }

    public void rollDice() {
        // TODO: prompt player to roll (press enter), used as a trigger in the game loop
    }

    public double[] getRates() {
        return rates;
    }

    public double getBalance() {
        return casinoAccount.getBalance();
    }

    public double getWager() {
        return wager;
    }

    public CasinoAccount fetchCasinoAccount() {
        return casinoAccount;
    }

    @Override
    public CasinoAccount getArcadeAccount() {
        return casinoAccount;
    }

    @Override
    public <SomeReturnType> SomeReturnType play() {
        return null; // turn logic handled in CrapsGame
    }
}
