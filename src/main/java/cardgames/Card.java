package cardgames;

import javafx.scene.image.Image;

/*
 * Peleissä käytettävä kortti, esim. hertta 5
 */
public class Card implements Comparable<Card>{

	private final String suit;
	private final int rank;
	private Image image;

	public Card(String suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
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

	@Override
	public int compareTo(Card card) {
		return this.rank - card.rank;
	}
}
