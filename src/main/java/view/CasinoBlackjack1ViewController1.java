package view;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import cardgames.Card;
import cardgames.Casino_Blackjack_1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Session;
import model.User;

/**
 * Kasino blackjack. Blackjack-peli, mitä pelataan krediiteillä, eli oikealla rahalla.
 * @author R12
 *
 */
public class CasinoBlackjack1ViewController1 implements Initializable {
	@FXML
	public Button exit_button;
	@FXML
	public Button play_button;
	@FXML
	public Button hit_button;
	@FXML
	public Button stay_button;
	@FXML
	public TextField bet_field;
	@FXML
	public Label win_label;
	@FXML
	public Text player_hand_total;
	@FXML
	public Text dealer_hand_total;
	@FXML
	public ImageView pcard0;
	@FXML
	public ImageView pcard1;
	@FXML
	public ImageView pcard2;
	@FXML
	public ImageView pcard3;
	@FXML
	public ImageView pcard4;
	@FXML
	public ImageView pcard5;
	@FXML
	public ImageView pcard6;
	@FXML
	public ImageView dcard0;
	@FXML
	public ImageView dcard1;
	@FXML
	public ImageView dcard2;
	@FXML
	public ImageView dcard3;
	@FXML
	public ImageView dcard4;
	@FXML
	public ImageView dcard5;
	@FXML
	public ImageView dcard6;
	@FXML
	public Text balance;
	@FXML 
	public Label errorLabel;
	
	private ResourceBundle texts;
	private NumberFormat numberFormat;
	
	private Casino_Blackjack_1 game = new Casino_Blackjack_1();
	
	
	@Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	init();
    }
	
	private void init() {
		texts = Session.getLanguageBundle();
		numberFormat = Session.getNumberFormatter();
		setText();
		disableButtons();
		updateBalance();
	}
	
	private void setText() {
		exit_button.setText(texts.getString("exit.button"));
		play_button.setText(texts.getString("play.button"));
		hit_button.setText(texts.getString("hit.button"));
		stay_button.setText(texts.getString("stay.button"));
		
		bet_field.setPromptText(texts.getString("bet.insert"));
		win_label.setText(texts.getString("win"));
		player_hand_total.setText(texts.getString("cards.player"));
		dealer_hand_total.setText(texts.getString("cards.opponent"));
	}
	
	private void updateBalance() {
		balance.setText(texts.getString("credits")+": " + numberFormat.format(User.getCredits()));
	}
	
	public void insertBet() {
		double bet = 0;
		try {
			bet = Double.parseDouble(bet_field.getText());
			if(User.getCredits() < bet) {
				errorLabel.setText(texts.getString("bet.morethancredit")+"!");
				errorLabel.setVisible(true);
				return;
			}
			else
				errorLabel.setVisible(false);
			if(game.startGame(bet))
				gameStarted();
		} catch (NumberFormatException e) {
			errorLabel.setText(texts.getString("bet.insert"));
			errorLabel.setVisible(true);
			System.err.println(texts.getString("bet.notnumber"));
		}
	}
	
	private void disableButtons() {
		hit_button.setDisable(true);
		stay_button.setDisable(true);
		play_button.setDisable(false);
	}
	
	private void enableButtons() {
		hit_button.setDisable(false);
		stay_button.setDisable(false);
		play_button.setDisable(true);
	}
	
	public void gameStarted() {
		updateBalance();
		clearCard();
		win_label.setText("");
		player_hand_total.setText(texts.getString("cards.player")+": " + Integer.toString(game.playerHandTotal()));
		dealer_hand_total.setText(texts.getString("cards.opponent")+": " + Integer.toString(game.dealersHandFirstCard()));
		updatePlayersCards();
		showDealersBeginningHand();
		if(game.isGameOver()) {
			/*
			 * Pelaaja on tässä vaiheessa saanut blackjackin tai jotain korttien summa 
			 * on ylittänyt 21
			 */
			checkGame();
		} else
			enableButtons();
	}
	
	public void playerHit() {
		disableButtons();
		game.playerHit();
		updatePlayersCards();
		checkGame();
	}
	
	public void playerStay() {
		disableButtons();
		game.playerStay();
		updateDealersCards();
		checkGame();
	}
	
	private void updatePlayersCards() {
		ImageView[] pcard_img = {pcard0,pcard1,pcard2,pcard3,pcard4,pcard5,pcard6};
		ArrayList<Card> cards = game.getPlayersCards();
		for(int i = 0; i < cards.size(); i++) {
			//pcard0.setImage(cards.get(i).getImage());
			
			if(i<pcard_img.length)
				pcard_img[i].setImage(cards.get(i).getImage());
				
		}
	}
	
	private void updateDealersCards() {
		ImageView[] dcard_img = {dcard0,dcard1,dcard2,dcard3,dcard4,dcard5,dcard6};
		ArrayList<Card> cards = game.getDealersCards();
		for(int i = 0; i < cards.size(); i++) {
			if(i<dcard_img.length)
				dcard_img[i].setImage(cards.get(i).getImage());
		}
	}
	
	private void showDealersBeginningHand() {
		ImageView[] dcard_img = {dcard0,dcard1,dcard2,dcard3,dcard4,dcard5,dcard6};
		ArrayList<Card> cards = game.getDealersCards();
		for(int i = 0; i < cards.size()-1; i++) {
			if(i<dcard_img.length)
				dcard_img[i].setImage(cards.get(i).getImage());
		}
	}
	
	private void checkGame() {
		player_hand_total.setText(Integer.toString(game.playerHandTotal()));
		if(game.isPlayersTurn() == false)
			dealer_hand_total.setText(Integer.toString(game.dealersHandTotal()));
		if(!game.isGameOver() && game.isPlayersTurn())
			enableButtons();
		if(game.isGameOver())
			showOutcome();
	}
	
	private void clearCard() {
		ImageView[] pcard_img = {pcard0,pcard1,pcard2,pcard3,pcard4,pcard5,pcard6};
		ImageView[] dcard_img = {dcard0,dcard1,dcard2,dcard3,dcard4,dcard5,dcard6};
		for(int i=0; i < pcard_img.length;i++)
			pcard_img[i].setImage(null);
		for(int i=0; i < dcard_img.length;i++)
			dcard_img[i].setImage(null);
	}
	
	private void showOutcome() {
		win_label.setText(texts.getString("win")+": " + Double.toString(game.getWinnings()) +" "+ texts.getString("credits"));
		updateBalance();
	}
	
	public void toMainView(ActionEvent e) {
		Stage window = (Stage) exit_button.getScene().getWindow();
		Navigator.toMainView(window);
	}
	
}
