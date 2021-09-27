package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Arcade_Blackjack_1;

public class AcradeBlackjack1ViewController implements Initializable {
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
	public TextField win_field;
	@FXML
	public Text player_hand_total;
	@FXML
	public Text dealer_hand_total;
	
	private Arcade_Blackjack_1 game = new Arcade_Blackjack_1();
	
	@Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	init();
    }
	
	private void init() {
		disableButtons();
	}
	
	public void insertBet() {
		int bet = 0;
		try {
			bet = Integer.parseInt(bet_field.getText());
			if(game.startGame(bet))
				gameStarted();
		} catch (NumberFormatException e) {
			System.err.println("Panos ei ole annettu numeroina");
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
		win_field.setText("0");
		player_hand_total.setText(Integer.toString(game.playerHandTotal()));
		dealer_hand_total.setText(Integer.toString(game.dealersHandFirstCard()));
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
		checkGame();
	}
	
	public void playerStay() {
		disableButtons();
		game.playerStay();
		checkGame();
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
	
	private void showOutcome() {
		win_field.setText(Integer.toString(game.getWinnings()));
	}
	
	public void toMainView(ActionEvent e) {
			
			try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApplication.class.getResource("HomepageView.fxml"));
	            AnchorPane mainView = (AnchorPane) loader.load();
	            Scene mainScene = new Scene(mainView);
				Stage window = (Stage) exit_button.getScene().getWindow();
				window.setScene(mainScene);
				
	        } catch (IOException iOE) {
	            iOE.printStackTrace();
	        }
		}

}
