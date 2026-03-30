
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
        CasinoAccount currentAccount = null;
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
                } String gameSelectionInput;
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
                         
            arcadeDashBoardInput = getArcadeDashboardInput(currentAccount);
            if ("2".equals(arcadeDashBoardInput)) {
                if (currentAccount == null) {
                    String accountName = console.getStringInput("Enter your account name:");
                    String accountPassword = console.getStringInput("Enter your account password:");
                    currentAccount = casinoAccountManager.getAccount(accountName, accountPassword);
                    if (currentAccount == null) {
                        console.println("No account found with name of [ %s ] and password of [ %s ]", accountName, accountPassword);
                        continue;
                    
                    }
                }
                String gameSelectionInput;
                do {
                    gameSelectionInput = getGameSelectionInput().toUpperCase();
                    if (gameSelectionInput.equals("1")) {
                        play(new SlotsGame(), new SlotsPlayer(currentAccount.getUsername(), currentAccount));
                    } else if (gameSelectionInput.equals("2")) {
                        play(new NumberGuessGame(), new NumberGuessPlayer(currentAccount));
                    } else if (gameSelectionInput.equals("3")) {
                        play(new BlackjackGame(), new BlackjackPlayer(currentAccount));
                    } else if (gameSelectionInput.equals("4")) {
                        play(new CrapsGame(), new CrapsPlayer(currentAccount));
                    } else if (gameSelectionInput.equals("5")) {
                        play(new RouletteGame(), new RoulettePlayer(currentAccount));
                    } else if (gameSelectionInput.equals("6")) {
                        play(new HangmanGame(), new HangmanPlayer(currentAccount.getUsername(), currentAccount));
                    } else if (!gameSelectionInput.equals("7")) {
                        console.println("[ %s ] is an invalid game selection", gameSelectionInput);
                    }
                } while (!gameSelectionInput.equals("7") && currentAccount.getBalance() > 0);

                if (currentAccount.getBalance() <= 0) {
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
            } else if ("1".equals(arcadeDashBoardInput)) {
                console.println("Welcome to the account-creation screen.");
                String accountName = console.getStringInput("Enter your account name:");
                String accountPassword = console.getStringInput("Enter your account password:");
                CasinoAccount newAccount = casinoAccountManager.createAccount(accountName, accountPassword);
                casinoAccountManager.registerAccount(newAccount);
                console.println("Account created! You have been given $500.00 to start.");
            } else if ("3".equals(arcadeDashBoardInput)) {
                if (currentAccount == null) {
                    String accountName = console.getStringInput("Enter your account name:");
                    String accountPassword = console.getStringInput("Enter your account password:");
                    currentAccount = casinoAccountManager.getAccount(accountName, accountPassword);
                    if (currentAccount == null) {
                        console.println("No account found with name of [ %s ] and password of [ %s ]", accountName, accountPassword);
                        continue;
                    }
                }
                manageAccount(currentAccount);
            } else if ("4".equals(arcadeDashBoardInput)) {
                if (currentAccount != null) {
                    console.println("Logged out of [ %s ].", currentAccount.getUsername());
                    currentAccount = null;
                    arcadeDashBoardInput = ""; // don't exit, just log out
                }
            }
        } while (!"5".equals(arcadeDashBoardInput));
    }
    





    private String getArcadeDashboardInput(CasinoAccount currentAccount) {
        StringBuilder sb = new StringBuilder()
                .append("\n ╔══════════════════════════════╗")
                .append("\n║     🎰 WELCOME TO CASINO 🎰   ║");
        if (currentAccount != null) {
            sb.append(String.format("\n║  Logged in: %-17s║", currentAccount.getUsername()));
        }
        sb.append("\n╠══════════════════════════════╣")
          .append("\n║  1. Create Account           ║")
          .append("\n║  2. Select Game              ║")
          .append("\n║  3. Manage Account           ║")
          .append("\n║  4. Logout                   ║")
          .append("\n║  5. Exit                     ║")
          .append("\n╚══════════════════════════════╝")
          .append("\nEnter a number (1-5): ");
        return console.getStringInput(sb.toString());
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
                .append("\n║  7. Back                     ║")
                .append("\n╚══════════════════════════════╝")
                .append("\nEnter a number (1-7): ")
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

