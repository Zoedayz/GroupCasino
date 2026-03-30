package com.github.zipcodewilmington.casino.games.blackjack;

public class Card {

	private final String suit;
	private final String rank;
	private final int value;

	public Card(String suit, String rank) {
		this.suit = suit;
		this.rank = rank;
		this.value = determineValue(rank);
	}

	private int determineValue(String rank) {
		if ("Ace".equalsIgnoreCase(rank)) {
			return 11;
		}

		if ("Jack".equalsIgnoreCase(rank)
				|| "Queen".equalsIgnoreCase(rank)
				|| "King".equalsIgnoreCase(rank)) {
			return 10;
		}

		try {
			return Integer.parseInt(rank);
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException("Invalid rank: " + rank);
		}
	}

	public int getValue() {
		return value;
	}

	public String getRank() {
		return rank;
	}

	public String getSuit() {
		return suit;
	}

	private String getSuitSymbol() {
		switch (suit) {
			case "Spades":   return "\u2660";  // ♠
			case "Hearts":   return "\u2665";  // ♥
			case "Diamonds": return "\u2666";  // ♦
			case "Clubs":    return "\u2663";  // ♣
			default:         return suit;
		}
	}

	@Override
	public String toString() {
		return rank + getSuitSymbol();  // e.g. "A♠", "K♥", "10♦", "7♣"
	}
}
