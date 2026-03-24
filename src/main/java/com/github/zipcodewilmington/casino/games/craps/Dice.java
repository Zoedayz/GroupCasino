package com.github.zipcodewilmington.casino.games.craps;

public class Dice {

    private int die1;
    private int die2;
    private final int sides;

    public Dice() {
        this.sides = 6;
        this.die1 = 1;
        this.die2 = 1;
    }

    public void roll() {
        // TODO: roll both dice using random values between 1 and sides
    }

    public int getTotal() {
        // TODO: return the sum of die1 + die2
        return 0;
    }

    public int getSides() {
        return sides;
    }

    public int getDie1() {
        return die1;
    }

    public int getDie2() {
        return die2;
    }

    @Override
    public String toString() {
        // TODO: return a readable string e.g. "[3] + [4] = 7"
        return "";
    }
}
