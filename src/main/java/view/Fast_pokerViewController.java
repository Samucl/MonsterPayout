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
import model.Tietokanta;
import model.User;

public class Fast_pokerViewController implements Initializable{
	
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
	@FXML private Label winLabel1;
	@FXML private Label winLabel2;
	@FXML private Label winLabel3;
	@FXML private Label winLabel4;
	@FXML private Label winLabel5;
	@FXML private Label winLabel6;
	@FXML private Label winLabel7;
	@FXML private Label winLabel8;
	@FXML private Label winLabel9;
	private double win;
	private double bet = 1.00;
	private final String[] winningTable = {"Pari", "Kaksi paria", "Kolmoset", "Suora", "Väri","Täyskäsi", "Neljä samaa", "Värisuora", "Kuningasvärisuora"};
	private final double[] coinsTable = {2,6,10,20,30,50,100,200,300};
	Fast_poker game;
	Card[] cards = new Card[6];
	Card[] winningCards = new Card[5];
	
	public void init() throws FileNotFoundException {
		hideCards();
		recoverButton.setDisable(true);
		doubleButton.setDisable(true);
		betButton.setText("Panos: " + bet);
		game = new Fast_poker();
		balanceLabel.setText("Krediitit: " + User.getCredits());
		setWinLabel();
	}

	public void clickCard2() {
		doublesResult(1, card2);
	}
	
	public void clickCard3() {
		if(game.isPlay()) {
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
			game.getWinningCards(winningCards);
			if(game.getWinningHand() == -1) {
				handLabel.setText("");
				winLabel.setText("Ei voittoa");
				playButton.setDisable(false);
				betButton.setDisable(false);
			}
			else {
				win = coinsTable[game.getWinningHand()];
				winLabel.setText((int)(win*bet) + " Krediittiä");
				handLabel.setText(winningTable[game.getWinningHand()]);
				doubleButton.setDisable(false);
				recoverButton.setDisable(false);
			}
		}
		doublesResult(2, card3);
		game.setPlay(false);
		highlightCards();
	}
	public void clickCard4() {
		if(game.isPlay()) {
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
			game.getWinningCards(winningCards);
			if(game.getWinningHand() == -1) {
				handLabel.setText("");
				winLabel.setText("Ei voittoa");
				playButton.setDisable(false);
				betButton.setDisable(false);
			}
			else {
				win = coinsTable[game.getWinningHand()];
				winLabel.setText((int)(win*bet) + " Krediittiä");
				handLabel.setText(winningTable[game.getWinningHand()]);
				doubleButton.setDisable(false);
				recoverButton.setDisable(false);
			}
		}
		doublesResult(3, card4);
		game.setPlay(false);
		highlightCards();
	}
	
	public void clickCard5() {
		doublesResult(4, card5);
	}
	
	public void play(ActionEvent e) throws FileNotFoundException {
		if(User.getCredits() >= bet) {
			hideButtons();
			Tietokanta.decreaseCreditBalance((int)bet);
			winLabel.setText("Voitto");
			handLabel.setText("Käsi");
			game.setPlay(true);
			highlightCards();
			game.makeDeck();
			cards = game.take6();
			balanceLabel.setText("Krediitit: " + User.getCredits());
			System.out.println(cards[0].getImage());
			card1.setImage(cards[0].getImage());
			card2.setImage(cards[1].getImage());
			card3.setImage(cards[2].getImage());
			card4.setImage(cards[3].getImage());
			card5.setImage(new Image(new FileInputStream("./src/main/resources/card_deck_1/taka.png")));
		}
	}
	
	public void recover(ActionEvent e) throws FileNotFoundException {
		winLabel.setText("Voitto");
		handLabel.setText("Käsi");
		hideCards();
		Tietokanta.increaseCreditBalance((int)win * (int)bet);
		balanceLabel.setText("Krediitit: " + User.getCredits());
		recoverButton.setDisable(true);
		doubleButton.setDisable(true);
		playButton.setDisable(false);
		betButton.setDisable(false);
	}
	
	public void bet(ActionEvent e) {
		bet = bet + bet/2;
		bet = Math.round(bet);
		if(bet > 140)
			bet = 1;
		setWinLabel();
		betButton.setText("Panos: " + bet);
	}
	
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
	
	public void doubles(ActionEvent e) throws FileNotFoundException {
		game.setDoubles(true);
		highlightCards();
		game.makeDeck();
		cards = game.take6();
		hideCards();
		card1.setImage(cards[0].getImage());
		handLabel.setText("");
		winLabel.setText("Voitto");
		hideButtons();
	}
	
	private void doublesResult(int i, ImageView card) {
		if(game.isDoubles()) {
			card.setImage(cards[i].getImage());
			if(game.checkDouble(cards, i)) {
				win = win * 2;
				handLabel.setText("Tuplaus!");
				winLabel.setText("" + (int)win*bet);
				recoverButton.setDisable(false);
				doubleButton.setDisable(false);
			}
			else {
				winLabel.setText("Ei voittoa");
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