package cardgames;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.image.Image;
import model.Session;

/*
 * Tavallisten pelikorttien pakka
 */
public class DeckOfCards {

	private static final String suits[] = {"risti", "pata", "hertta", "ruutu"};
	private static final int ranks[] = {1,2,3,4,5,6,7,8,9,10,11,12,13};
	private ArrayList<Card> cards;
	private static String deck_name = "card_deck_1";

	public static void setDeckName(String name) {
		deck_name = name;
	}

	/*
	 * Yksi korttipakka
	 */
	public DeckOfCards() {
		cards = new ArrayList<>();
		String filepath = deck_name;

		for (String suit : suits) {
			for (int rank : ranks) {
				Card newCard = new Card(suit, rank);
				InputStream image = Session.getFile(filepath+"/"+suit+rank+".png");
				System.out.println(filepath+"/"+suit+rank);
				try {
					newCard.setImage(new Image(image));
				} catch (Exception e) {
					e.printStackTrace();
				}
				cards.add(newCard);
			}
		}
		Collections.shuffle(cards);
		for (Card card : cards)
			System.out.println(card.toString());
	}

	/*
	 * Jos otetaan useampi kuin yksi pakka käyttöön esim. blackjackissa
	 */
	public DeckOfCards(int numberOfDecks) {
		cards = new ArrayList<>();
		/*
		 * Jos korttipakkojen määräksi on asetettu pienempi kuin 1 niin pakkaa ei luoda
		 */
		if(numberOfDecks<=0)
			return;
		for(int k = 0; k < numberOfDecks; k++) {
			String filepath = deck_name;

			for (String suit : suits) {
				for (int rank : ranks) {
					Card newCard = new Card(suit, rank);
					//File image = new File(filepath+"/"+suit+rank+".png");
					InputStream image = Session.getFile(filepath+"/"+suit+rank+".png");
					System.out.println(filepath+"/"+suit+rank);
					try {
						newCard.setImage(new Image(image));
					} catch (Exception e) {
						e.printStackTrace();
					}
					cards.add(newCard);
				}
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
