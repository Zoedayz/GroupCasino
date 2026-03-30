package com.github.zipcodewilmington.casino.games.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

public class BlackjackGame implements GameInterface {
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";
    private Deck deck;
    private BlackjackHand dealerHand;
    private List<BlackjackPlayer> players;
    private final IOConsole console = new IOConsole(AnsiColor.BLUE);

    public BlackjackGame() {
        this.deck = new Deck();
        this.dealerHand = new BlackjackHand();
        this.players = new ArrayList<>();
    }
        public void displayCard(String rank, String suit) {
        String symbol;
        switch (suit.toLowerCase()) {
            case "hearts":
                symbol = RED + "\u2665" + RESET;
                break;
            case "diamonds":
                symbol = RED + "\u2666" + RESET;
                break;
            case "spades":
                symbol = "\u2660";
                break;
            case "clubs":
                symbol = "\u2663";
                break;
            default:
                symbol = "?";
                break;
        }
    
        System.out.println("┌─────────┐");
        System.out.printf("│ %-2s      │\n", rank);
        System.out.printf("│    %s    │\n", symbol);
        System.out.printf("│      %-2s │\n", rank);
        System.out.println("└─────────┘");
    }
    @Override
    public void add(PlayerInterface player) {
        players.add((BlackjackPlayer) player);
    }

    @Override
    public void remove(PlayerInterface player) {
        players.remove((BlackjackPlayer) player);
    }

    @Override
    public void run() {
        console.println("=== Welcome to Blackjack ===");

        String playAgain = "y";
        while (playAgain.equalsIgnoreCase("y")) {
            playRound();
            playAgain = console.getStringInput("\nPlay another round? (y/n): ");
        }

        console.println("Thanks for playing!");
    }

    private void playRound() {
        // reset everything
        deck = new Deck();
        deck.shuffle();
        dealerHand.clear();

        for (BlackjackPlayer bp : players) {
            bp.resetHand();
        }

        // take bets
        for (BlackjackPlayer bp : players) {
            console.println("\n%s, your balance is $%.2f",
                bp.fetchCasinoAccount().getUsername(),
                bp.fetchCasinoAccount().getBalance());
            double bet = console.getDoubleInput("Place your bet: $");
            bp.placeBet(bet, 1, bp.getBalance());
        }

        dealInitialCards();

        // show hands — dealer hides second card
        console.println("\nDealer shows: %s [hidden]",
            dealerHand.toString().split(",")[0]);
        for (BlackjackPlayer bp : players) {
            console.println("%s's hand: %s",
                bp.fetchCasinoAccount().getUsername(),
                bp.getHand().toString());
        }

        List<BlackjackPlayer> activePlayers = checkBlackjack();

        if (activePlayers == null || activePlayers.isEmpty()) {
            return;
        }

        // each active player takes their turn
        for (BlackjackPlayer bp : activePlayers) {
            bp.takeTurn(deck);

            if (bp.getHand().isBust()) {
                console.println("%s busted and loses their bet.",
                    bp.fetchCasinoAccount().getUsername());
            }
        }

        dealerTurn(activePlayers);

        // determine winners
        for (BlackjackPlayer bp : activePlayers) {
            if (!bp.getHand().isBust()) {
                settleResult(bp);
            }
        }
    }

    private void dealInitialCards() {
        for (BlackjackPlayer bp : players) {
            bp.getHand().addCard(deck.deal());
            bp.getHand().addCard(deck.deal());
        }
        dealerHand.addCard(deck.deal());
        dealerHand.addCard(deck.deal());
    }

    private List<BlackjackPlayer> checkBlackjack() {
        boolean dealerBJ = dealerHand.isBlackjack();
        List<BlackjackPlayer> activePlayers = new ArrayList<>();

        if (dealerBJ) {
            console.println("\nDealer reveals: %s", dealerHand.toString());
        }

        for (BlackjackPlayer bp : players) {
            boolean playerBJ = bp.getHand().isBlackjack();

            if (playerBJ || dealerBJ) {
                if (playerBJ && dealerBJ) {
                    console.println("Both have blackjack — push! Wager returned.");
                    bp.fetchCasinoAccount().depositToBalance(bp.getWager());
                } else if (playerBJ) {
                    console.println("Blackjack! %s wins!",
                        bp.fetchCasinoAccount().getUsername());
                    bp.fetchCasinoAccount().depositToBalance(bp.getWager() * 2.5);
                } else {
                    console.println("Dealer has blackjack. %s loses.",
                        bp.fetchCasinoAccount().getUsername());
                }
                continue;
            }

            activePlayers.add(bp);
        }

        if (dealerBJ) {
            return null;
        }

        return activePlayers;
    }

    private void dealerTurn(List<BlackjackPlayer> activePlayers) {
        boolean hasNonBustedPlayer = false;
        for (BlackjackPlayer bp : activePlayers) {
            if (!bp.getHand().isBust()) {
                hasNonBustedPlayer = true;
                break;
            }
        }

        if (hasNonBustedPlayer) {
            console.println("\nDealer reveals: %s", dealerHand.toString());
            while (dealerHand.getTotal() < 17) {
                Card drawn = deck.deal();
                dealerHand.addCard(drawn);
                console.println("Dealer hits: %s", drawn.toString());
                console.println("Dealer hand: %s", dealerHand.toString());
            }
        }
    }

    private void settleResult(BlackjackPlayer player) {
        int playerTotal = player.getHand().getTotal();
        int dealerTotal = dealerHand.getTotal();
        String name = player.fetchCasinoAccount().getUsername();

        console.println("\n--- Result ---");
        console.println("%s: %d  |  Dealer: %d", name, playerTotal, dealerTotal);

        if (dealerHand.isBust()) {
            console.println("Dealer busts! %s wins!", name);
            player.fetchCasinoAccount().depositToBalance(player.getWager() * 2);
        } else if (playerTotal > dealerTotal) {
            console.println("%s wins!", name);
            player.fetchCasinoAccount().depositToBalance(player.getWager() * 2);
        } else if (playerTotal == dealerTotal) {
            console.println("Push — tie! Wager returned.");
            player.fetchCasinoAccount().depositToBalance(player.getWager());
        } else {
            console.println("Dealer wins. %s loses their bet.", name);
        }

        console.println("%s's new balance: $%.2f",
            name, player.fetchCasinoAccount().getBalance());
    }
}