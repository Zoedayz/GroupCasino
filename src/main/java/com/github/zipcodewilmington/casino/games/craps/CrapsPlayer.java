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
        this.rates = new double[]{ 1.0, 2.0 }; // index 0 = pass line (1:1), index 1 = come-out natural (2:1)
    }

    public void placeBet(double amount, double min, double max) {
        if (amount < min) {
            console.println("Minimum bet is $%.2f.", min);
            return;
        }
        if (amount > max) {
            console.println("Maximum bet is $%.2f.", max);
            return;
        }
        if (amount > casinoAccount.getBalance()) {
            console.println("Not enough funds. Your balance is $%.2f", casinoAccount.getBalance());
            return;
        }
        this.wager = amount;
        casinoAccount.withdrawBalance(amount);
        console.println("Bet placed: $%.2f", wager);
    }

    public void rollDice() {
        console.getStringInput("Press ENTER to roll the dice...");
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
