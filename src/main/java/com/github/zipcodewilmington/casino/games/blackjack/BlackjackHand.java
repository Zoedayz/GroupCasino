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
            total += card.getValue();
            if (card.getRank().equals("Ace")) {
                aceCount++;
            }
        }

        // reduce each ace from 11 to 1 if we're bust
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
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