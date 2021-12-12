package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cardgames.Card;
import cardgames.Fast_poker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Database;
import model.Session;
import model.User;

/**
 * Luokka toimii Fastpoker kasinopelin kontrollerina
 * @author Samuel
 * @version 12.12.2021
 */
public class Fast_pokerViewController implements Initializable{
	
	ResourceBundle texts;
	@FXML private ImageView card1;
	@FXML private ImageView card2;
	@FXML private ImageView card3;
	@FXML private ImageView card4;
	@FXML private ImageView card5;
	@FXML private Button playButton;
	@FXML private Button recoverButton;
	@FXML private Button doubleButton;
	@FXML private Button betButton;
	@FXML private Button toMenu;
	@FXML private Label winLabel;
	@FXML private Label balanceLabel;
	@FXML private Label handLabel;
	@FXML private Label winLabel1, winLabel2, winLabel3, winLabel4, winLabel5, winLabel6, winLabel7, winLabel8, winLabel9;
	@FXML private Label pair, twoOfAKind, threeOfAKind, straight, flush, fullhouse, fourOfAKind, straightFlush, royalFlush;
	@FXML private Label errorLabel;
	private double win;
	private double bet = 1.00;
	private String[] winningTable = new String[9];
	private final double[] coinsTable = {2,6,10,20,30,50,100,200,300};
	Fast_poker game;
	Card[] cards = new Card[6];
	Card[] winningCards = new Card[5];
	
	public void init() throws FileNotFoundException {
		setLanguage();
		hideCards();
		recoverButton.setDisable(true);
		doubleButton.setDisable(true);
		game = new Fast_poker();
		setWinLabel();
	}
	
	private void setLanguage() {
		texts = Session.getLanguageBundle();
		setPokerCardNames(pair, 0, texts.getString("pair") + " 10-A");
		setPokerCardNames(twoOfAKind, 1, texts.getString("two.of.akind"));
		setPokerCardNames(threeOfAKind, 2, texts.getString("three.of.akind"));
		setPokerCardNames(straight, 3, texts.getString("straight"));
		setPokerCardNames(flush, 4, texts.getString("flush"));
		setPokerCardNames(fullhouse, 5, texts.getString("fullhouse"));
		setPokerCardNames(fourOfAKind, 6, texts.getString("four.of.akind"));
		setPokerCardNames(straightFlush, 7, texts.getString("straight.flush"));
		setPokerCardNames(royalFlush, 8, texts.getString("royal.flush"));
		balanceLabel.setText(texts.getString("credits") + ": " + User.getCredits());
		errorLabel.setText(texts.getString("bet.morethancredit"));
		betButton.setText(texts.getString("bet.amount") + ": " + bet);
		winLabel.setText(texts.getString("win"));
		handLabel.setText(texts.getString("hand"));
		playButton.setText(texts.getString("play.button"));
		recoverButton.setText(texts.getString("recover.profits"));
		doubleButton.setText(texts.getString("double.button"));
		toMenu.setText(texts.getString("exit.button"));
	}
	
	private void setPokerCardNames(Label pokerHand, int i, String key) {
		winningTable[i] = key;
		pokerHand.setText(key);
	}

	public void clickCard2() {
		doublesResult(1, card2);
	}
	
	public void clickCard3() {
		if(game.isPlay()) {	//Ensin asetetaan loput korteista näkyviin.
			cards[3] = null;
			card4.setImage(cards[4].getImage());
			card5.setImage(cards[5].getImage());
			int x = 0;
			for(int i = 0; i < winningCards.length; i++) {
				if(x == 3)
					x++;
				winningCards[i] = cards[x];
				x++;
			}
			showWin();
		}
		doublesResult(2, card3);
		game.setPlay(false);
		highlightCards();
	}
	public void clickCard4() {
		if(game.isPlay()) {	//Ensin asetetaan loput korteista näkyviin.
			cards[2] = null;
			card3.setImage(cards[4].getImage());
			card5.setImage(cards[5].getImage());
			int x = 0;
			for(int i = 0; i < winningCards.length; i++) {
				if(x == 2)
					x++;
				winningCards[i] = cards[x];
				x++;
			}
			Card temp = winningCards[2];
			winningCards[2] = winningCards[3];
			winningCards[3] = temp;
			showWin();
		}
		doublesResult(3, card4);
		game.setPlay(false);
		highlightCards();
	}
	
	/**
	 * Metodi tuo ruudulle tiedon mahdollisesta voitosta
	 */
	private void showWin() {
		game.getWinningCards(winningCards);
		if(game.getWinningHand() == -1) { //Jos winningHand palauttaa arvon -1 eli häviön.
			handLabel.setText("");
			winLabel.setText("");
			playButton.setDisable(false);
			betButton.setDisable(false);
		}
		else { //Jos tuloksena on voitto.
			win = coinsTable[game.getWinningHand()];
			winLabel.setText((int)(win*bet) + " " + texts.getString("credits"));
			handLabel.setText(winningTable[game.getWinningHand()]);
			doubleButton.setDisable(false);
			recoverButton.setDisable(false);
		}
	}
	
	public void clickCard5() {
		doublesResult(4, card5);
	}
	
	/**
	 * Metodi aloittaa pelin. Tarkistetaan pelaajan saldo ja vähennetään siitä panoksen verran.
	 * Luodaan korttipakka ja asetetaan kortit näkyviin pelipöydälle.
	 * @throws FileNotFoundException
	 */
	public void play(ActionEvent e) throws FileNotFoundException {
		if(User.getCredits() >= bet) {
			hideButtons();
			Database.decreaseCreditBalance((int)bet);
			winLabel.setText(texts.getString("win"));
			handLabel.setText(texts.getString("hand"));
			game.setPlay(true);
			highlightCards();
			game.makeDeck();
			cards = game.take6();
			balanceLabel.setText(texts.getString("credits") + ": " + User.getCredits());
			System.out.println(cards[0].getImage());
			card1.setImage(cards[0].getImage());
			card2.setImage(cards[1].getImage());
			card3.setImage(cards[2].getImage());
			card4.setImage(cards[3].getImage());
			card5.setImage(new Image(new FileInputStream("./src/main/resources/card_deck_1/taka.png")));
		}
		else
			errorLabel.setVisible(true);
	}
	
	/**
	 * Metodi jolla saadaan pelaajan voitot talteen, jos pelaaja ei halua tuplata enempää.
	 * Toisin sanoen metodin suorittaminen lopettaa pelikerran.
	 * @throws FileNotFoundException
	 */
	public void recover(ActionEvent e) throws FileNotFoundException {
		winLabel.setText(texts.getString("win"));
		handLabel.setText(texts.getString("hand"));
		hideCards();
		Database.increaseCreditBalance((int)win * (int)bet);
		balanceLabel.setText(texts.getString("credits") + ": " + User.getCredits());
		recoverButton.setDisable(true);
		doubleButton.setDisable(true);
		playButton.setDisable(false);
		betButton.setDisable(false);
	}
	
	/**
	 * Metodi panoksen asettamiselle.
	 */
	public void bet(ActionEvent e) {
		if(bet < User.getCredits()) {
			errorLabel.setVisible(false);
		}
		bet = bet + bet/2;
		bet = Math.round(bet);
		if(bet > 140)
			bet = 1;
		setWinLabel();
		betButton.setText(texts.getString("bet.button") + ": " + bet);
	}
	
	/**
	 * Metodi asettaa pelaajalle näkyviin kaikki mahdolliset pokerikäsien voittosummat.
	 */
	private void setWinLabel() {
		winLabel1.setText((int)coinsTable[8] * (int)bet + "");
		winLabel2.setText((int)coinsTable[7] * (int)bet + "");
		winLabel3.setText((int)coinsTable[6] * (int)bet + "");
		winLabel4.setText((int)coinsTable[5] * (int)bet + "");
		winLabel5.setText((int)coinsTable[4] * (int)bet + "");
		winLabel6.setText((int)coinsTable[3] * (int)bet + "");
		winLabel7.setText((int)coinsTable[2] * (int)bet + "");
		winLabel8.setText((int)coinsTable[1] * (int)bet + "");
		winLabel9.setText((int)coinsTable[0] * (int)bet + "");
	}
	
	/**
	 * Metodi tuplauken alottamiselle.
	 * @throws FileNotFoundException
	 */
	public void doubles(ActionEvent e) throws FileNotFoundException {
		game.setDoubles(true);
		highlightCards();
		game.makeDeck();
		cards = game.take6();
		hideCards();
		card1.setImage(cards[0].getImage());
		handLabel.setText("");
		winLabel.setText(texts.getString("win"));
		hideButtons();
	}
	
	/**
	 * Metodilla tarkistetaan tuplauksen tulos.
	 */
	private void doublesResult(int i, ImageView card) {
		if(game.isDoubles()) {
			card.setImage(cards[i].getImage());
			if(game.checkDouble(cards, i)) {
				win = win * 2;
				handLabel.setText(texts.getString("double.button") + "!");
				winLabel.setText("" + (int)win*bet);
				recoverButton.setDisable(false);
				doubleButton.setDisable(false);
			}
			else {
				winLabel.setText("");
				playButton.setDisable(false);
				betButton.setDisable(false);
				win = 0;
			}
			game.setDoubles(false);
			highlightCards();
		}
	}
	
	private void hideCards() throws FileNotFoundException {
		card1.setImage(new Image(new FileInputStream("./src/main/resources/card_deck_1/taka.png")));
		card2.setImage(new Image(new FileInputStream("./src/main/resources/card_deck_1/taka.png")));
		card3.setImage(new Image(new FileInputStream("./src/main/resources/card_deck_1/taka.png")));
		card4.setImage(new Image(new FileInputStream("./src/main/resources/card_deck_1/taka.png")));
		card5.setImage(new Image(new FileInputStream("./src/main/resources/card_deck_1/taka.png")));
	}
	
	private void hideButtons() {
		recoverButton.setDisable(true);
		doubleButton.setDisable(true);
		playButton.setDisable(true);
		betButton.setDisable(true);
	}
	
	/**
	 * Asetetaan halutuille korteille tyylejä
	 */
	private void highlightCards() {
		DropShadow ds = new DropShadow();
		ds.setColor(Color.RED);
		ds.setHeight(100);
		ds.setWidth(100);
		if(game.isDoubles()) {
			card2.setTranslateY(-20);
			card3.setTranslateY(-20);
			card4.setTranslateY(-20);
			card5.setTranslateY(-20);
			card2.setEffect(ds);
			card3.setEffect(ds);			
			card4.setEffect(ds);
			card5.setEffect(ds);
		}
		else if(game.isPlay()) {
			card3.setTranslateY(-20);
			card4.setTranslateY(-20);
			card3.setEffect(ds);			
			card4.setEffect(ds);
		}
		else {
			ds.setColor(new Color(0.2, 0, 0, 1));
			ds.setHeight(70);
			ds.setWidth(70);
			card2.setTranslateY(0);
			card3.setTranslateY(0);
			card4.setTranslateY(0);
			card5.setTranslateY(0);
			card2.setEffect(ds);
			card3.setEffect(ds);			
			card4.setEffect(ds);
			card5.setEffect(ds);
		}
			
	}
	
	public void toMenu(ActionEvent e) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("HomepageView.fxml"));
            BorderPane mainView = (BorderPane) loader.load();
            Scene mainScene = new Scene(mainView);
			Stage window = (Stage) toMenu.getScene().getWindow();
			window.setScene(mainScene);
			
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			init();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}