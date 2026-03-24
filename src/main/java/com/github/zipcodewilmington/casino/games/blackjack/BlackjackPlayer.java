package com.github.zipcodewilmington.casino.games.blackjack;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

public class BlackjackPlayer implements PlayerInterface {
    private CasinoAccount casinoAccount;
    private BlackjackHand hand;
    private double wager;
    private final IOConsole console = new IOConsole(AnsiColor.GREEN);

    public BlackjackPlayer(CasinoAccount casinoAccount) {
        this.casinoAccount = casinoAccount;
        this.hand = new BlackjackHand();
        this.wager = 0;
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
            console.println("Not enough funds. Your balance is $%.2f",
                casinoAccount.getBalance());
            return;
        }
        this.wager = amount;
        casinoAccount.withdrawBalance(amount);
        console.println("Bet placed: $%.2f", wager);
    }

    // play() drives the player's hit-or-stand turn
    // it receives a deck so it can deal cards to itself
    public <SomeReturnType> SomeReturnType play() {
        return null; // turn logic handled in BlackjackGame
    }

    public void takeTurn(Deck deck) {
        while (true) {
            console.println("\nYour hand: %s", hand.toString());
            String choice = console.getStringInput("Hit or stand? (h/s): ");
            if (choice.equalsIgnoreCase("s")) {
                stay();
                break;
            } else if (choice.equalsIgnoreCase("h")) {
                hit(deck);
                if (hand.isBust()) {
                    console.println("Your hand: %s", hand.toString());
                    console.println("Bust! You went over 21.");
                    break;
                }
            } else {
                console.println("Please enter h or s.");
            }
        }
    }

    public void hit(Deck deck) {
        hand.addCard(deck.deal());
    }

    public void stay() {
        console.println("%s stands with %s", casinoAccount.getUsername(), hand.toString());
    }

    public void doubleDown(Deck deck) {
        double extra = Math.min(wager, casinoAccount.getBalance());
        casinoAccount.withdrawBalance(extra);
        wager += extra;
        console.println("Doubled down! New wager: $%.2f", wager);
        hit(deck);
    }

    public String askWhichOne() {
        return console.getStringInput("Hit or stand? (h/s): ");
    }

    @Override
    public CasinoAccount getArcadeAccount() {
        return casinoAccount;
    }

    public CasinoAccount fetchCasinoAccount() {
        return casinoAccount;
    }

    public double getBalance() {
        return casinoAccount.getBalance();
    }

    public BlackjackHand getHand()  { return hand; }
    public double getWager()        { return wager; }

    public void resetHand() {
        hand.clear();
        wager = 0;
    }
}