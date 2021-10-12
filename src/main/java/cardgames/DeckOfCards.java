package cardgames;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.image.Image;

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
		cards = new ArrayList<Card>();
		String filepath = "./src/main/resources/"+deck_name;
		
		for(int i = 0; i < suits.length; i++) {
			for(int j = 0; j < ranks.length; j++) {
				Card newCard = new Card(suits[i], ranks[j]);
				File image = new File(filepath+"/"+suits[i]+ranks[j]+".png");
				System.out.println(filepath+"/"+suits[i]+ranks[j]);
				if(image.exists())
					try {
						newCard.setImage(new Image(new FileInputStream(image)));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				cards.add(newCard);
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
		for(int k = 0; k < numberOfDecks; k++) {
			String filepath = "./src/main/resources/"+deck_name;
			
			for(int i = 0; i < suits.length; i++) {
				for(int j = 0; j < ranks.length; j++) {
					Card newCard = new Card(suits[i], ranks[j]);
					File image = new File(filepath+"/"+suits[i]+ranks[j]+".png");
					if(image.exists())
						try {
							newCard.setImage(new Image(new FileInputStream(image)));
						} catch (FileNotFoundException e) {
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
