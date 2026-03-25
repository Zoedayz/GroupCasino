package com.github.zipcodewilmington.casino.games;
import com.github.zipcodewilmington.casino.CasinoAccount;

//lofi

public class RoulettePlayer {
    private CasinoAccount account;
    private int betNumber;
    private String betType;
    private double betAmount;

    public RoulettePlayer(CasinoAccount account) {
        this.account = account;
    }

    public CasinoAccount geAccount() {
        return account;
    }

    public int getBetNumber() {
        return betNumber;
    }

    public void setBetNumber(int betNumber) {
        this.betNumber = betNumber;
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }

}
