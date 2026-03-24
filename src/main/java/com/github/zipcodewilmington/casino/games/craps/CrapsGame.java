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

        // TODO: implement the main game loop
        //   - take bets from each player
        //   - run come-out roll via rollDice()
        //   - evaluate come-out result via evaluateRoll()
        //   - if point is established, loop until point or 7-out
        //   - settle results via settleResult()
        //   - ask to play again
    }

    public void rollDice() {
        // TODO: roll the dice and print the result
    }

    public void evaluateRoll(int point) {
        // TODO: check the dice total against craps rules
        //   Come-out roll: 7 or 11 = win, 2/3/12 = lose, anything else = sets the point
        //   Point phase:   roll the point again = win, roll 7 = lose
    }

    public void settleResult(CrapsPlayer player, boolean won) {
        // TODO: pay out or deduct based on win/loss
        //   win  → depositToBalance(wager * 2)
        //   loss → nothing (wager was already deducted on placeBet)
    }
}
