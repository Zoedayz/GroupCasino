package com.github.zipcodewilmington;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.CasinoAccountManager;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.casino.games.blackjack.BlackjackGame;
import com.github.zipcodewilmington.casino.games.blackjack.BlackjackPlayer;
import com.github.zipcodewilmington.casino.games.craps.CrapsGame;
import com.github.zipcodewilmington.casino.games.craps.CrapsPlayer;
import com.github.zipcodewilmington.casino.games.hangman.HangmanGame;
import com.github.zipcodewilmington.casino.games.hangman.HangmanPlayer;
import com.github.zipcodewilmington.casino.games.numberguess.NumberGuessGame;
import com.github.zipcodewilmington.casino.games.numberguess.NumberGuessPlayer;
import com.github.zipcodewilmington.casino.games.roulette.RouletteGame;
import com.github.zipcodewilmington.casino.games.roulette.RoulettePlayer;
import com.github.zipcodewilmington.casino.games.slots.SlotsGame;
import com.github.zipcodewilmington.casino.games.slots.SlotsPlayer;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

/**
 * Created by leon on 7/21/2020.
 */
public class Casino implements Runnable {
    private final IOConsole console = new IOConsole(AnsiColor.BLUE);

    @Override
    public void run() {
        String arcadeDashBoardInput;
        CasinoAccountManager casinoAccountManager = new CasinoAccountManager();
        do {
            arcadeDashBoardInput = getArcadeDashboardInput();
            if ("1".equals(arcadeDashBoardInput)) {
                String accountName = console.getStringInput("Enter your account name:");
                String accountPassword = console.getStringInput("Enter your account password:");
                CasinoAccount casinoAccount = casinoAccountManager.getAccount(accountName, accountPassword);
                boolean isValidLogin = casinoAccount != null;
                if (isValidLogin) {
                    String gameSelectionInput = getGameSelectionInput().toUpperCase();
                    if (gameSelectionInput.equals("1")) {
                        play(new SlotsGame(), new SlotsPlayer(casinoAccount.getUsername(), casinoAccount));
                    } else if (gameSelectionInput.equals("NUMBERGUESS")) {
                        play(new NumberGuessGame(), new NumberGuessPlayer(casinoAccount));
                    } else if (gameSelectionInput.equals("BLACKJACK")) {
                        play(new BlackjackGame(), new BlackjackPlayer(casinoAccount));
                    } else if (gameSelectionInput.equals("CRAPS")) {
                        play(new CrapsGame(), new CrapsPlayer(casinoAccount));
                    } else if (gameSelectionInput.equals("ROULETTE")) {
                        play(new RouletteGame(), new RoulettePlayer(casinoAccount));
                    } else if (gameSelectionInput.equals("HANGMAN")) {
                        play(new HangmanGame(), new HangmanPlayer(casinoAccount.getUsername(), casinoAccount));
                    } else {
                        String errorMessage = "[ %s ] is an invalid game selection";
                        throw new RuntimeException(String.format(errorMessage, gameSelectionInput));
                    }
                } else {
                    // TODO - implement better exception handling
                    String errorMessage = "No account found with name of [ %s ] and password of [ %s ]";
                    throw new RuntimeException(String.format(errorMessage, accountPassword, accountName));
                }
            } else if ("2".equals(arcadeDashBoardInput)) {
                console.println("Welcome to the account-creation screen.");
                String accountName = console.getStringInput("Enter your account name:");
                String accountPassword = console.getStringInput("Enter your account password:");
                CasinoAccount newAccount = casinoAccountManager.createAccount(accountName, accountPassword);
                casinoAccountManager.registerAccount(newAccount);
                console.println("Account created! You have been given $500.00 to start.");
            } else if ("3".equals(arcadeDashBoardInput)) {
                String accountName = console.getStringInput("Enter your account name:");
                String accountPassword = console.getStringInput("Enter your account password:");
                CasinoAccount casinoAccount = casinoAccountManager.getAccount(accountName, accountPassword);
                if (casinoAccount != null) {
                    manageAccount(casinoAccount);
                } else {
                    String errorMessage = "No account found with name of [ %s ] and password of [ %s ]";
                    throw new RuntimeException(String.format(errorMessage, accountName, accountPassword));
                }
            }
        } while (!"4".equals(arcadeDashBoardInput));
    }

    private String getArcadeDashboardInput() {
        return console.getStringInput(new StringBuilder()
                .append("\n ╔══════════════════════════════╗")
                .append("\n║     🎰 WELCOME TO CASINO 🎰   ║")
        .append("\n╠══════════════════════════════╣")
        .append("\n║  1. Create Account           ║")
        .append("\n║  2. Select Game              ║")
        .append("\n║  3. Manage Account           ║")
        .append("\n║  4. Logout                   ║")
        .append("\n╚══════════════════════════════╝")
        .append("\nEnter a number (1-4): ")
        .toString());
    }

    private String getGameSelectionInput() {
        return console.getStringInput(new StringBuilder()
                .append("\n ╔══════════════════════════════╗")
                .append("\n║  GAME SELECTION DASHBOARD    ║")
                .append("\n╠══════════════════════════════╣")
                .append("\n║  1. SLOTS                    ║")
                .append("\n║  2. NUMBER GUESS             ║")
                .append("\n║  3. BLACKJACK                ║")
                .append("\n║  4. CRAPS                    ║")
                .append("\n║  5. ROULETTE                 ║")
                .append("\n║  6. HANGMAN                  ║")
                .append("\n╚══════════════════════════════╝")
                .append("\nEnter a number (1-6): ")
                .toString());
    }

    private void manageAccount(CasinoAccount account) {
        String input;
        do {
            input = console.getStringInput(new StringBuilder()
                    .append("\n=== Account: " + account.getUsername() + " ===")
                    .append("\n\t[ view ], [ deposit ], [ withdraw ], [ back ]")
                    .toString());

            if ("view".equals(input)) {
                console.println("Username: %s", account.getUsername());
                account.displayBalance();
            } else if ("deposit".equals(input)) {
                double amount = console.getDoubleInput("Enter deposit amount: $");
                account.depositToBalance(amount);
            } else if ("withdraw".equals(input)) {
                double amount = console.getDoubleInput("Enter withdrawal amount: $");
                account.withdrawBalance(amount);
            }
        } while (!"back".equals(input));
    }

    private void play(GameInterface game, PlayerInterface player) {
        game.add(player);
        game.run();
    }
}
