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
                    String gameSelectionInput;
                    do {
                        gameSelectionInput = getGameSelectionInput().toUpperCase();
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
                        } else if (!gameSelectionInput.equals("BACK")) {
                            console.println("[ %s ] is an invalid game selection", gameSelectionInput);
                        }
                    } while (!gameSelectionInput.equals("BACK") && casinoAccount.getBalance() > 0);

                    if (casinoAccount.getBalance() <= 0) {
                        console.println(
                "\n" +
    "  _____  _____  _____  _   _ \n" +
    " |_   _||_   _||  ___|| \\ | |\n" +
    "   | |    | |  | |_   |  \\| |\n" +
    "   | |    | |  |  _|  | |\\ |\n" +
    "   |_|    |_|  |_|    |_| \\_|\n" +
    "\n" +
    "  __  __  ___  _   _     _   ____  _____ \n" +
    "  \\ \\/ / / _ \\| | | |   / \\ |  _ \\| ____|\n" +
    "   \\  / | | | | | | |  / _ \\| |_) |  _|  \n" +
    "   / /  | |_| | |_| | / ___ \\|  _ <| |___\n" +
    "  /_/    \\___/ \\___/ /_/   \\_\\_| \\_\\_____|\n" +
    "\n" +
    "  ____  ____  ___  _  __ _____\n" +
    " | __ )|  _ \\/ _ \\| |/ /| ____|\n" +
    " |  _ \\| |_) | | | | ' /|  _|  \n" +
    " | |_) |  _ <| |_| | . \\| |___\n" +
    " |____/|_| \\_\\\\___/|_|\\_\\|_____|\n" +
    "\n" +
    "  +------------------------------------------+\n" +
    "  |  Ta Ta For Now... and your money too!    |\n" +
    "  |     The house ALWAYS wins! >:)           |\n" +
    "  +------------------------------------------+\n"
            );
                    }
                } else {
                    console.println("No account found with name of [ %s ] and password of [ %s ]", accountName, accountPassword);
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
                    console.println(String.format(errorMessage, accountName, accountPassword));
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

    private String getGameSelectionInput() {
        return console.getStringInput(new StringBuilder()
                .append("Welcome to the Game Selection Dashboard!")
                .append("\nFrom here, you can select any of the following options:")
            .append("\n\t[ SLOTS ], [ NUMBERGUESS ], [ BLACKJACK ], [ CRAPS ], [ ROULETTE ], [ HANGMAN ], [ BACK ]")
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
