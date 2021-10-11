package cardgames;

import java.util.ArrayList;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.ICoinGame;
import model.Tietokanta;

public class Arcade_Blackjack_1 implements ICoinGame {
	/*
	 * Tehdään view joka kutsuu tämän luokan metodeja, sekä 
	 * visualisoi pelin kulkua
	 */

	/*
	 * Blackjackiä pelataan "deck_count" määrällisellä pakkoja
	 */
	private final int deck_count = 4;
	private DeckOfCards cardDeck;
	private int bet = 0;
	private int win = 0;
	private Hand playersHand = new Hand();
	private Hand dealersHand = new Hand();
	private boolean gameOver = true;
	private boolean playersTurn = false;
	private boolean playerWin = false;
	
	private AudioClip shuffleSound = new AudioClip("file:./src/main/resources/sounds/sekoitus1.wav");
	private AudioClip dealSound = new AudioClip("file:./src/main/resources/sounds/jako1.wav");
	
	public Arcade_Blackjack_1() {
	}
	
	public boolean startGame(int bet) {
		
		/*
		 * Jos panos on pienempi kuin 0, niin peliä ei pelata
		 */
		if(bet < 0)
			return false;
		this.bet = bet;
		
		/*
		 * Jos tietokannasta ei voida hakea käyttäjältä tarpeeksi 
		 * kolikoita pelaamiseen, niin peliä ei pelata
		 */
		if(useCoins(this.bet)<this.bet) {
			return false;
		}
		
		/*
		 * Jos korttipakkaa ei ole vielä määritetty tai siitä on jo puolet 
		 * käytetty niin tehdään uusi pakka
		 */
		if(cardDeck == null || cardDeck.size() < (deck_count * 52)/2) {
			newDeck();
		}
		/*
		 * Peli alkaa
		 */
		gameOver = false;
		win = 0;
		playerWin = false;
		dealCards();
		return true;
	}
	
	private void dealCards() {
		playersHand.clearHand();
		dealersHand.clearHand();
		shuffleSound.play();
		for(int i = 0; i < 2; i++) {
			playersHand.addCard(cardDeck.takeCard());
			dealersHand.addCard(cardDeck.takeCard());
		}
		/*
		 * Jos pelaaja saa blackjackin niin voitto on 3:2
		 */
		if(playersHand.isBlackjack()) {
			win = bet + bet * 3 / 2;
			playerWin = true;
			addCoinBalance(win);
		} else {
			if(playersHand.calculateTotalBlackjack()<21)
				playersTurn = true;
			else 
				gameOver = true;
		}
	}
	
	public void dealersTurn() {
		if(isDealersTurn()) {
			int loop = 0;
			while(dealersHand.calculateTotalBlackjack()<17) {
				dealersHand.addCard(cardDeck.takeCard());
				if(loop > 20)
					break;
				loop++;
			}
			loop = 0;
			while(!gameOver) {
				if(dealersHand.calculateTotalBlackjack()==playersHand.calculateTotalBlackjack()) {
					playerDraw();
				}
				if(dealersHand.calculateTotalBlackjack()<playersHand.calculateTotalBlackjack())
					dealersHand.addCard(cardDeck.takeCard());
				if(dealersHand.calculateTotalBlackjack()>playersHand.calculateTotalBlackjack() && dealersHand.calculateTotalBlackjack()<21) {
					playerLoose();
				}
				if(dealersHand.calculateTotalBlackjack()>21) {
					playerWin();
				}
				if(loop > 20)
					break;
				loop++;
			}
		}
	}
	
	public boolean playerHit() {
		if(playersTurn) {
			dealSound.play();
			playersHand.addCard(cardDeck.takeCard());
			ArrayList<Card> hand = playersHand.getCards();
			if(playersHand.calculateTotalBlackjack()>21) {
				playerLoose();
			} else if(playersHand.calculateTotalBlackjack() == 21) {
				playersTurn = false;
				dealersTurn();
			}
			return true;
		}
		return false;
	}
	
	private void playerDraw() {
		win = bet;
		playerWin = true;
		playersTurn = false;
		gameOver = true;
		addCoinBalance(win);
	}
	
	private void playerLoose() {
		playersTurn = false;
		gameOver = true;
	}
	
	private void playerWin() {
		win = bet * 2;
		playerWin = true;
		playersTurn = false;
		gameOver = true;
		addCoinBalance(win);
	}
	
	public void playerStay() {
		playersTurn = false;
		dealersTurn();
	}
	
	public boolean isDealersTurn() {
		return !playersTurn && !gameOver;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public boolean isPlayersTurn() {
		return playersTurn;
	}
	
	public Hand getPlayersHand(){
		return playersHand;
	}
	
	public Hand getDealersHand() {
		return dealersHand;
	}
	
	public int getWinnings() {
		return win;
	}
	
	public ArrayList<Card> getPlayersCards(){
		return playersHand.getCards();
	}
	
	public ArrayList<Card> getDealersCards(){
		return dealersHand.getCards();
	}
	
	public boolean playerBlackjack() {
		return playersHand.isBlackjack();
	}
	
	private void newDeck() {
		cardDeck = new DeckOfCards(deck_count);
	}
	
	public int playerHandTotal() {
		return playersHand.calculateTotalBlackjack();
	}
	
	public int dealersHandTotal() {
		return dealersHand.calculateTotalBlackjack();
	}
	
	public int dealersHandFirstCard() {
		return dealersHand.revealOnlyOne();
	}
	
	@Override
	public int useCoins(int bet) {
		return Tietokanta.decreaseCoinBalance(bet);
	}

	@Override
	public boolean addCoinBalance(int amount) {
		/*
		 * Peli päättyy
		 */
		gameOver = true;
		if(Tietokanta.increaseCoinBalance(amount)>0)
			return true;
		return false;
	}

}