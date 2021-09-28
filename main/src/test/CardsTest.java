package test;

import cardgames.DeckOfCards;

public class CardsTest {

	public static void main(String[] args) {
		DeckOfCards cardDeck = new DeckOfCards(4);
		int size = cardDeck.size();
		for(int i = 0; i < size; i++) {
			System.out.println(cardDeck.takeCard().toString()+" "+cardDeck.size());
		}
	}
}
