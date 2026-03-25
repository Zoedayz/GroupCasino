package com.github.zipcodewilmington.casino;

import java.util.Random;
import java.util.Scanner;

public class NumberGuess {
    private int number;
    private int guesses;

    public NumberGuess() {
        // Create a Random object and pick a number from 1 to 100
        Random random = new Random();
        this.number = random.nextInt(100) + 1;
        // Start the guess counter at 0
        this.guesses = 0;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Prompt the user for a guess
            System.out.print("Enter a guess between 1 and 100: ");
            int guess = scanner.nextInt();

            // Count this guess
            guesses = guesses + 1;

            // Check if the guess is correct
            if (guess == number) {
                System.out.println("CONGRATULATIONS YOU WIN! You guessed it in " + guesses + " guesses.");
                break;
            }
            // Too low
            else if (guess < number) {
                System.out.println("Too Low!");
            }
            // Too high
            else if (guess > number) {
                System.out.println("Too High!");
            }

            // Check if the player has used all 10 guesses
            if (guesses >= 10) {
                System.out.println("You have run out of guesses! The number was " + number + ".");
                break;
            }
        }

        scanner.close();
    }

    
    }
