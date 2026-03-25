package com.github.zipcodewilmington.casino.games.slots;

import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.casino.CasinoAccount;

/**
 * Created by leon on 7/21/2020.
 */
public class SlotsPlayer implements PlayerInterface {
    private String name;
    private CasinoAccount account;

    public SlotsPlayer(String name, CasinoAccount account) {
        this.name = name;
        this.account = account;
    }

    @Override
    public CasinoAccount getArcadeAccount() {
        return account;
    }   
    @Override
    public String play() {
        return name + " is playing Slots YAYYYYY!";
    }

    public String getName() { return name; }

    public double getBalance() { return account.getBalance(); }

    public void setBalance(double newBalance) {
        double current = account.getBalance();
        if (newBalance > current) {
            account.depositToBalance(newBalance - current);
        } else if (newBalance < current) {
            account.withdrawBalance(current - newBalance);
        }
    }
}
