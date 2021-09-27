package model;

import java.util.ArrayList;
import java.util.Collections;

/*
 * Tavallisten pelikorttien pakka
 */
public class DeckOfCards {
	
	private static final String suits[] = {"risti", "pata", "hertta", "ruutu"};
	private static final int ranks[] = {1,2,3,4,5,6,7,8,9,10,11,12,13};
	private ArrayList<Card> cards;
	
	/*
	 * Yksi korttipakka
	 */
	public DeckOfCards() {
		cards = new ArrayList<Card>();
		for(int i = 0; i < suits.length; i++) {
			for(int j = 0; j < ranks.length; j++) {
				cards.add(new Card(suits[i], ranks[j]));
			}
		}
		Collections.shuffle(cards);
		for(int i = 0; i < cards.size(); i++)
			System.out.println(cards.get(i).toString());
	}
	
	/*
	 * Jos otetaan useampi kuin yksi pakka käyttöön esim. blackjackissa
	 */
	public DeckOfCards(int numberOfDecks) {
		cards = new ArrayList<Card>();
		/*
		 * Jos korttipakkojen määräksi on asetettu pienempi kuin 1 niin pakkaa ei luoda
		 */
		if(numberOfDecks<=0)
			return;
		for(int k = 0; k < numberOfDecks; k++)
			for(int i = 0; i < suits.length; i++) {
				for(int j = 0; j < ranks.length; j++) {
					cards.add(new Card(suits[i], ranks[j]));
				}
			}
		/*
		 * sekoitetaan pakka kolmesti
		 */
		Collections.shuffle(cards);
		Collections.shuffle(cards);
		Collections.shuffle(cards);
	}
	
	public int size() {
		return cards.size();
	}
	
	public Card takeCard() {
		return cards.remove(0);
	}
}
