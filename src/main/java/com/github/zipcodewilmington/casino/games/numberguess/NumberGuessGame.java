package com.github.zipcodewilmington.casino.games.numberguess;

import java.util.Random;
import java.util.Scanner;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;


public class NumberGuessGame implements GameInterface {

    private final NumberGuessPlayer player;
    private final Random random;
    private final Scanner scanner;
    
    private int number;
    private int guesses;
    private int guess;
    private boolean playAgain;
    

    public NumberGuessGame(NumberGuessPlayer player, Random random, Scanner scanner) {
        this.player = player;
        this.random = random;
        this.scanner = scanner;
    }

    public void play() {
        System.out.println("Welcome to Number Guess Game!===");
        
        boolean playAgain = true;

        while (playAgain) {
            playRound();
        } 

        System.out.print("Do you want to play again? (yes/no): ");
        String response = scanner.next();
        playAgain = response.equalsIgnoreCase("yes");
      
        
        System.out.println("Thanks for playing Number Guess Game!"); 
        }
    
        private void playRound() {
            number = random.nextInt(100) + 1; // Random number between 1 and 100
            guesses = 0;    
        
        while (true) {
            // Prompt the user for a guess
            System.out.print("Enter a guess between 1 and 100: ");
            int guess = scanner.nextInt();

            // Count this guess
            guesses ++;

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


    @Override
    public void add(PlayerInterface player) {
        // TODO: store the player
    }

    @Override
    public void remove(PlayerInterface player) {
        // TODO: remove the player
    }

    @Override
    public void run() {
    if (guess == number) {
        System.out.println("CONGRATULATIONS YOU WIN! You guessed it in " + guesses + " guesses.");
    }
            // Too low
    else if (guess < number) {
        System.out.println("Too Low!");
    }
            // Too high
    else if (guess > number) {
        System.out.println("Too High!");
    }   // TODO: implement the number guessing game loop
    }
}