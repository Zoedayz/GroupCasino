package com.github.zipcodewilmington.casino.games.slots;
import java.util.Random;
import java.util.Scanner;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;


public class SlotsGame implements GameInterface{
    private SlotsPlayer player;
    private Random random = new Random();
    private String[] symbols = {"🍒", "🍋", "⭐", "🔔", "💎"};

    @Override
    public void add(PlayerInterface player) {
        this.player = (SlotsPlayer)player;
    }

    @Override
    public void remove(PlayerInterface player) {
        this.player = null;
    }


    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Slots, " + player.getName() + "!");

        while (player.getBalance() > 0 ) {
            System.out.println("Balance: $" + player.getBalance());
            System.out.print("Enter bet amount (or 0 to quit): ");
            double bet = scanner.nextDouble();
            if (bet == 0) {
                break;
            }
            if (bet > player.getBalance()) {
                System.out.println("Insufficient balance!");
                continue;
            }

            String result = spin();
            System.out.println("Spinning...");

            String[] reels = result.split(" \\| ");
            if (reels[0].equals(reels[1]) && reels[1].equals(reels[2])) {
                double winnings = bet * 3;
                player.setBalance(player.getBalance() + winnings);
                System.out.println("Jackpot! You win $" + winnings);
            } else {
                player.setBalance(player.getBalance() - bet);
                System.out.println("You lose $" + bet);
            }
        }
        System.out.println("Game over! Final balance: $" + player.getBalance());
        }
        
            public String spin() {
        String reel1 = symbols[random.nextInt(symbols.length)];
        String reel2 = symbols[random.nextInt(symbols.length)];
        String reel3 = symbols[random.nextInt(symbols.length)];
        return reel1 + " | " + reel2 + " | " + reel3;
    }
    
}