package com.github.zipcodewilmington.casino.games.craps;

import java.util.ArrayList;
import java.util.List;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

public class CrapsGame implements GameInterface {

    private Dice dice;
    private List<PlayerInterface> players;
    private final IOConsole console = new IOConsole(AnsiColor.YELLOW);

    public CrapsGame() {
        this.dice = new Dice();
        this.players = new ArrayList<>();
    }

    @Override
    public void add(PlayerInterface player) {
        players.add(player);
    }

    @Override
    public void remove(PlayerInterface player) {
        players.remove(player);
    }

    @Override
    public void run() {
        console.println("=== Welcome to Craps ===");

        String playAgain = "y";
        while (playAgain.equalsIgnoreCase("y")) {
            playRound();
            playAgain = console.getStringInput("\nPlay another round? (y/n): ");
        }

        console.println("Thanks for playing Craps!");
    }

    private void playRound() {
        for (PlayerInterface p : players) {
            CrapsPlayer cp = (CrapsPlayer) p;
            console.println("\n%s, your balance is $%.2f",
                cp.fetchCasinoAccount().getUsername(), cp.getBalance());
            double bet = console.getDoubleInput("Place your bet (min $1): $");
            cp.placeBet(bet, 1, cp.getBalance());
        }

        // come-out roll
        for (PlayerInterface p : players) {
            CrapsPlayer cp = (CrapsPlayer) p;
            cp.rollDice();
            rollDice();

            int total = dice.getTotal();
            int point = evaluateRoll(total, 0); // 0 = no point yet

            if (point == -1) {
                // natural win (7 or 11)
                settleResult(cp, true);
            } else if (point == 0) {
                // craps (2, 3, 12) — lose
                settleResult(cp, false);
            } else {
                // point established — keep rolling
                console.println("Point is set to %d. Roll again to match it before rolling a 7!", point);
                boolean roundOver = false;
                while (!roundOver) {
                    cp.rollDice();
                    rollDice();
                    int roll = dice.getTotal();
                    int result = evaluateRoll(roll, point);
                    if (result == -1) {
                        // matched the point
                        settleResult(cp, true);
                        roundOver = true;
                    } else if (result == 0) {
                        // rolled a 7 — lose
                        settleResult(cp, false);
                        roundOver = true;
                    }
                    // any other roll — keep going
                }
            }
        }
    }

    public void rollDice() {
        dice.roll();
        console.println("Rolled: " + dice.toString());
    }

    /**
     * Evaluates the dice roll against craps rules.
     * @param total  the dice total
     * @param point  the established point (0 if come-out roll)
     * @return -1 = win, 0 = lose, any other int = new/existing point
     */
    public int evaluateRoll(int total, int point) {
        if (point == 0) {
            // come-out roll
            if (total == 7 || total == 11) return -1;       // natural — win
            if (total == 2 || total == 3 || total == 12) return 0; // craps — lose
            return total;                                     // set the point
        } else {
            // point phase
            if (total == point) return -1;  // matched point — win
            if (total == 7)     return 0;   // 7-out — lose
            return point;                   // no change, keep rolling
        }
    }

    public void settleResult(CrapsPlayer player, boolean won) {
        String name = player.fetchCasinoAccount().getUsername();
        if (won) {
            double payout = player.getWager() * 2;
            player.fetchCasinoAccount().depositToBalance(payout);
            console.println("%s wins! Payout: $%.2f | New balance: $%.2f",
                name, payout, player.getBalance());
        } else {
            console.println("%s loses. Balance: $%.2f", name, player.getBalance());
        }
    }
}
