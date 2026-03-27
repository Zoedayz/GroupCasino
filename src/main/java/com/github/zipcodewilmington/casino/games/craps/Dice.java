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

    private String dieFace(int value) {
        switch (value) {
            case 1: return "\u2680";  // ⚀
            case 2: return "\u2681";  // ⚁
            case 3: return "\u2682";  // ⚂
            case 4: return "\u2683";  // ⚃
            case 5: return "\u2684";  // ⚄
            case 6: return "\u2685";  // ⚅
            default: return String.valueOf(value);
        }
    }

    @Override
    public String toString() {
        return dieFace(die1) + " + " + dieFace(die2) + " = " + getTotal();  // e.g. "⚃ + ⚅ = 10"
    }
}
