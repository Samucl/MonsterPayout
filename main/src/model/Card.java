package model;

/*
 * Peleissä käytettävä kortti, esim. hertta 5
 */
public class Card {

	private final String suit;
	private final int rank;
	
	public Card(String suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	/*
	 * Kortin maa
	 */
	public String getSuit() {
		return suit;
		}
	
	/*
	 * Kortin arvo
	 */
	public int getRank() {
		return rank;
	}
	
	@Override
	public String toString() {
		return suit + " " + rank;
	}
	
}
