package com.github.zipcodewilmington.casino.games.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

	private static final String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
	private static final String[] RANKS = {
			"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"
	};

	private final List<Card> cards;
	private final int numberOfDecks;

	public Deck() {
		this(1);
	}

	public Deck(int numberOfDecks) {
		if (numberOfDecks < 1) {
			throw new IllegalArgumentException("numberOfDecks must be at least 1.");
		}
		this.numberOfDecks = numberOfDecks;
		this.cards = new ArrayList<>();
		initializeNewShuffledDeck();
	}

	public void reset() {
		initializeNewShuffledDeck();
	}

	private void initializeNewShuffledDeck() {
		cards.clear();
		for (int deck = 0; deck < numberOfDecks; deck++) {
			for (String suit : SUITS) {
				for (String rank : RANKS) {
					cards.add(new Card(suit, rank));
				}
			}
		}
		shuffle();
	}

	public int getNumberOfDecks() {
		return numberOfDecks;
	}

	public void shuffle() {
		Collections.shuffle(cards);
	}

	public Card drawCard() {
		if (cards.isEmpty()) {
			throw new IllegalStateException("Cannot draw from an empty deck.");
		}
		return cards.remove(0);
	}

	public int size() {
		return cards.size();
	}

	public boolean isEmpty() {
		return cards.isEmpty();
	}
}
