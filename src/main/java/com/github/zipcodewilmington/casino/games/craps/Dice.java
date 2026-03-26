package com.github.zipcodewilmington.casino.games.craps;

import java.util.Random;

public class Dice {

    private static final Random random = new Random();

    private int die1;
    private int die2;
    private final int sides;

    public Dice() {
        this.sides = 6;
        this.die1 = 1;
        this.die2 = 1;
    }

    public void roll() {
        die1 = random.nextInt(sides) + 1;
        die2 = random.nextInt(sides) + 1;
    }

    public int getTotal() {
        return die1 + die2;
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
        return "[" + die1 + "] + [" + die2 + "] = " + getTotal();
    }
}
