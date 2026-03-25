package com.github.zipcodewilmington.casino.games;

import java.util.Random;

public class RouletteWheel {
    private final int[] numbers;
    private final String[] colors;
    private int lastSpinNumber;
    private String lastSpinColor;
    private final Random random;

    public RouletteWheel() {
        this.numbers = new int[37]; // 0 to 36
        this.colors = new String[37];
        this.random = new Random();
        initializeWheel();
    }

    private void initializeWheel() {
        for (int i = 0; i <= 36; i++) {
            numbers[i] = i;
            if (i == 0) {
                colors[i] = "Green";
            } else if (isRedNumber(i)) {
                colors[i] = "Red";
            } else {
                colors[i] = "Black";
            }
        }
    }

    // Official Roulette logic for Red numbers
    private boolean isRedNumber(int n) {
        if ((n >= 1 && n <= 10) || (n >= 19 && n <= 28)) {
            return n % 2 != 0; // Odd is Red
        } else {
            return n % 2 == 0; // Even is Red
        }
    }

    public void spin() {
        int index = random.nextInt(37);
        this.lastSpinNumber = numbers[index];
        this.lastSpinColor = colors[index];
    }

    public int getLastSpinNumber() {
        return lastSpinNumber;
    }

    public String getLastSpinColor() {
        return lastSpinColor;
    }

    // Logic for your Game teammate to use for payouts
    public boolean lastSpinWasEven() {
        return lastSpinNumber != 0 && lastSpinNumber % 2 == 0;
    }

    public boolean lastSpinWasOdd() {
        return lastSpinNumber != 0 && lastSpinNumber % 2 != 0;
    }
}