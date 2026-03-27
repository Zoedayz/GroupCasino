package com.github.zipcodewilmington.casino.games.slots;

import java.util.Random;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

public class SlotsGame implements GameInterface {

    private SlotsPlayer player;
    private final Random random = new Random();
    private final IOConsole console = new IOConsole(AnsiColor.PURPLE);
    private final String[] symbols = {"🍒", "🍋", "⭐", "🔔", "💎"};

    @Override
    public void add(PlayerInterface player) {
        this.player = (SlotsPlayer) player;
    }

    @Override
    public void remove(PlayerInterface player) {
        this.player = null;
    }

    @Override
    public void run() {
        console.println("Welcome to Slots, " + player.getName() + "!");

        while (player.getBalance() > 0) {
            console.println("Balance: $%.2f", player.getBalance());
            double bet = console.getDoubleInput("Enter bet amount (or 0 to quit): ");

            if (bet == 0) {
                break;
            }
            if (bet > player.getBalance()) {
                console.println("Insufficient balance!");
                continue;
            }

            // Spin and reveal reels one at a time
            String[] reels = spin();
            console.println("Spinning...");

            try {
                Thread.sleep(600);
                console.println("  [ " + reels[0] + " ] | [ ? ] | [ ? ]");
                Thread.sleep(600);
                console.println("  [ " + reels[0] + " ] | [ " + reels[1] + " ] | [ ? ]");
                Thread.sleep(600);
                console.println("  [ " + reels[0] + " ] | [ " + reels[1] + " ] | [ " + reels[2] + " ]");
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (reels[0].equals(reels[1]) && reels[1].equals(reels[2])) {
                double winnings = bet * 3;
                player.setBalance(player.getBalance() + winnings);
                console.println("Jackpot! You win $%.2f", winnings);
            } else {
                player.setBalance(player.getBalance() - bet);
                console.println("You lose $%.2f", bet);
            }
        }

        console.println("Game over! Final balance: $%.2f", player.getBalance());
    }

    public String[] spin() {
        return new String[]{
            symbols[random.nextInt(symbols.length)],
            symbols[random.nextInt(symbols.length)],
            symbols[random.nextInt(symbols.length)]
        };
    }
}
