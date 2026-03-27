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
            if ("select-game".equals(arcadeDashBoardInput)) {
                String accountName = console.getStringInput("Enter your account name:");
                String accountPassword = console.getStringInput("Enter your account password:");
                CasinoAccount casinoAccount = casinoAccountManager.getAccount(accountName, accountPassword);
                boolean isValidLogin = casinoAccount != null;
                if (isValidLogin) {
                    console.println("\nWelcome back, %s! | Balance: $%.2f",
                        casinoAccount.getUsername(), casinoAccount.getBalance());
                    String gameSelectionInput = getGameSelectionInput(casinoAccount).toUpperCase();
                    if (gameSelectionInput.equals("SLOTS")) {
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
            } else if ("create-account".equals(arcadeDashBoardInput)) {
                console.println("Welcome to the account-creation screen.");
                String accountName = console.getStringInput("Enter your account name:");
                String accountPassword = console.getStringInput("Enter your account password:");
                CasinoAccount newAccount = casinoAccountManager.createAccount(accountName, accountPassword);
                casinoAccountManager.registerAccount(newAccount);
                console.println("Account created! You have been given $500.00 to start.");
            } else if ("manage-account".equals(arcadeDashBoardInput)) {
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
        } while (!"logout".equals(arcadeDashBoardInput));
    }

    private String getArcadeDashboardInput() {
        return console.getStringInput(new StringBuilder()
                .append("Welcome to the Arcade Dashboard!")
                .append("\nFrom here, you can select any of the following options:")
                .append("\n\t[ create-account ], [ select-game ], [ manage-account ], [ logout ]")
                .toString());
    }

    private String getGameSelectionInput(CasinoAccount account) {
        return console.getStringInput(new StringBuilder()
                .append("Welcome to the Game Selection Dashboard!")
                .append(String.format("\n  Balance: $%.2f", account.getBalance()))
                .append("\nFrom here, you can select any of the following options:")
                .append("\n\t[ SLOTS ], [ NUMBERGUESS ], [ BLACKJACK ], [ CRAPS ], [ ROULETTE ], [ HANGMAN ]")
                .toString());
    }

    private void manageAccount(CasinoAccount account) {
        String input;
        do {
            input = console.getStringInput(new StringBuilder()
                    .append(String.format("\n=== Account: %s | Balance: $%.2f ===",
                        account.getUsername(), account.getBalance()))
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
        console.println("\nGame over! Your balance: $%.2f",
            player.getArcadeAccount().getBalance());
    }
}
