package com.github.zipcodewilmington.casino.games.blackjack;

import java.util.ArrayList;
import java.util.List;

public class BlackjackHand {
    private List<Card> cards;

    public BlackjackHand() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getTotal() {
        int total = 0;
        int aceCount = 0;

        for (Card card : cards) {
            // Assuming Card class has a getValue() and getRank()
            total += card.getValue();
            if ("Ace".equalsIgnoreCase(card.getRank())) {
                aceCount++;
            }
        }

        // Reduce Ace from 11 to 1 if we're bust
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
    }

    /**
     * Displays all cards in the current hand side-by-side using Unicode.
     */
    public void displayHand() {
        if (cards.isEmpty()) {
            System.out.println("[ Empty Hand ]");
            return;
        }

        // Initialize 5 rows for the ASCII/Unicode cards
        String[] rows = {"", "", "", "", ""};

        for (Card card : cards) {
            String rawRank = card.getRank();
            // Convert "Ace" to "A", "King" to "K", etc., for better fit
            String rank = getShortRank(rawRank);
            String symbol = getSymbol(card.getSuit());

            rows[0] += "┌─────────┐  ";
            rows[1] += String.format("│ %-2s      │  ", rank); // Left-aligned rank
            rows[2] += String.format("│    %s    │  ", symbol); // Center symbol
            rows[3] += String.format("│      %2s │  ", rank); // Right-aligned rank
            rows[4] += "└─────────┘  ";
        }

        // Print the constructed rows
        for (String row : rows) {
            System.out.println(row);
        }
        System.out.println("Hand Total: " + getTotal());
    }

    private String getSymbol(String suit) {
        switch (suit.toLowerCase()) {
            case "hearts":   return "\u2665";
            case "diamonds": return "\u2666";
            case "spades":   return "\u2660";
            case "clubs":    return "\u2663";
            default:         return "?";
        }
    }

    private String getShortRank(String rank) {
        switch (rank.toLowerCase()) {
            case "ace":   return "A";
            case "king":  return "K";
            case "queen": return "Q";
            case "jack":  return "J";
            case "ten":   return "10";
            default:      return rank; // Returns numbers like "2", "3", etc.
        }
    }

    public boolean isBust() {
        return getTotal() > 21;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && getTotal() == 21;
    }

    public void clear() {
        cards.clear();
    }

    public int size() {
        return cards.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toString()).append(", ");
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString() + " (total: " + getTotal() + ")";
    }
}