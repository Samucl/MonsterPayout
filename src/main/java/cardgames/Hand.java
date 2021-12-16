package cardgames;

import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> hand = new ArrayList<>();

	public void clearHand() {
		hand = new ArrayList<>();
	}

	public void addCard(Card card) {
		hand.add(card);
	}

	public int revealOnlyOne() {
		if(hand.size()>0)
			return hand.get(0).getRank();
		return 0;
	}

	public int calculateTotalBlackjack() {
		int total = 0;

		/*
		 * Ensiksi lasketaan summa niin, että ässä (1) on arvoltaan 11
		 */
		for (Card element : hand) {
			int value = element.getRank();
			/*
			 * Kuvalliset kortit ovat arvoltaan 10
			 */
			if(value > 10)
				value = 10;
			if(value == 1) {
				value = 11;
			}
			total+=value;
		}
		/*
		 * Jos summa ylittää rajan 21 niin lasketaan summa uudestaan niin, että
		 * ässä (1) on arvoltaan 1
		 */
		if(total > 21) {
			total = 0;
			for (Card element : hand) {
				int value = element.getRank();
				/*
				 * Kuvalliset kortit ovat arvoltaan 10
				 */
				if(value > 10)
					value = 10;
				total+=value;
			}
		}

		return total;
	}

	public ArrayList<Card> getCards(){
		return hand;
	}

	public boolean isBlackjack() {
		/*
		 * Jos kortteja on vain 2 ja niiden yhteisarvo on 21 niin toteutuu blackjack
		 */
		if(calculateTotalBlackjack() == 21 && hand.size() == 2)
			return true;
		return false;
	}

}
