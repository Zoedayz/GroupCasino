package com.github.zipcodewilmington.casino.games.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

public class BlackjackGame implements GameInterface {
    private Deck deck;
    private BlackjackHand dealerHand;
    private List<PlayerInterface> players;
    private final IOConsole console = new IOConsole(AnsiColor.BLUE);

    public BlackjackGame() {
        this.deck = new Deck();
        this.dealerHand = new BlackjackHand();
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

        for (PlayerInterface p : players) {
            BlackjackPlayer bp = (BlackjackPlayer) p;
            bp.resetHand();
        }

        // take bets
        for (PlayerInterface p : players) {
            BlackjackPlayer bp = (BlackjackPlayer) p;
            console.println("\n%s, your balance is $%.2f",
                bp.getArcadeAccount().getUsername(),
                bp.getArcadeAccount().getBalance());
            double bet = console.getDoubleInput("Place your bet: $");
            bp.placeBet(bet);
        }

        // deal two cards each
        for (PlayerInterface p : players) {
            ((BlackjackPlayer) p).getHand().addCard(deck.drawCard());
            ((BlackjackPlayer) p).getHand().addCard(deck.drawCard());
        }
        dealerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        // show hands — dealer hides second card
        console.println("\nDealer shows: %s [hidden]",
            dealerHand.toString().split(",")[0]);
        for (PlayerInterface p : players) {
            BlackjackPlayer bp = (BlackjackPlayer) p;
            console.println("%s's hand: %s",
                bp.getArcadeAccount().getUsername(),
                bp.getHand().toString());
        }

        // check for natural blackjack
        boolean dealerBJ = dealerHand.isBlackjack();
        List<BlackjackPlayer> activePlayers = new ArrayList<>();

        if (dealerBJ) {
            console.println("\nDealer reveals: %s", dealerHand.toString());
        }

        for (PlayerInterface p : players) {
            BlackjackPlayer bp = (BlackjackPlayer) p;
            boolean playerBJ = bp.getHand().isBlackjack();

            if (playerBJ || dealerBJ) {
                if (playerBJ && dealerBJ) {
                    console.println("Both have blackjack — push! Wager returned.");
                    bp.getArcadeAccount().depositToBalance(bp.getWager());
                } else if (playerBJ) {
                    console.println("Blackjack! %s wins!",
                        bp.getArcadeAccount().getUsername());
                    bp.getArcadeAccount().depositToBalance(bp.getWager() * 2.5);
                } else {
                    console.println("Dealer has blackjack. %s loses.",
                        bp.getArcadeAccount().getUsername());
                }
                continue;
            }

            activePlayers.add(bp);
        }

        if (dealerBJ || activePlayers.isEmpty()) {
            return;
        }

        // each active player takes their turn
        for (BlackjackPlayer bp : activePlayers) {
            bp.takeTurn(deck);

            if (bp.getHand().isBust()) {
                console.println("%s busted and loses their bet.",
                    bp.getArcadeAccount().getUsername());
            }
        }

        // dealer draws once for the round if any player is still in
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
                Card drawn = deck.drawCard();
                dealerHand.addCard(drawn);
                console.println("Dealer hits: %s", drawn.toString());
                console.println("Dealer hand: %s", dealerHand.toString());
            }
        }

        // determine winners
        for (BlackjackPlayer bp : activePlayers) {
            if (!bp.getHand().isBust()) {
                settleResult(bp);
            }
        }
    }

    private void settleResult(BlackjackPlayer player) {
        int playerTotal = player.getHand().getTotal();
        int dealerTotal = dealerHand.getTotal();
        String name = player.getArcadeAccount().getUsername();

        console.println("\n--- Result ---");
        console.println("%s: %d  |  Dealer: %d", name, playerTotal, dealerTotal);

        if (dealerHand.isBust()) {
            console.println("Dealer busts! %s wins!", name);
            player.getArcadeAccount().depositToBalance(player.getWager() * 2);
        } else if (playerTotal > dealerTotal) {
            console.println("%s wins!", name);
            player.getArcadeAccount().depositToBalance(player.getWager() * 2);
        } else if (playerTotal == dealerTotal) {
            console.println("Push — tie! Wager returned.");
            player.getArcadeAccount().depositToBalance(player.getWager());
        } else {
            console.println("Dealer wins. %s loses their bet.", name);
        }

        console.println("%s's new balance: $%.2f",
            name, player.getArcadeAccount().getBalance());
    }
}