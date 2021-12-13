
package view;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import moneyrain.MoneyRain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Database;
import model.Session;
import model.User;


public class HomepageViewController implements Initializable {
	@FXML Label nameLabel;
	@FXML Text kolikotLabel;
	@FXML Text krediititLabel;
	@FXML Button logoutButton;
	@FXML Button toStoreButton;
	@FXML Button toUserInfoButton;
	@FXML Label casinoLabel;
	@FXML Label arcadeLabel;
	
	private void init() {
		useLanguageBundle();
	}
	
	private void useLanguageBundle() {
		ResourceBundle texts = Session.getLanguageBundle();
		nameLabel.setText(texts.getString("welcomeback") + " " + User.getUsername() + "!");
		kolikotLabel.setText(texts.getString("coins") + ": " + User.getCoins());
		krediititLabel.setText(texts.getString("credits") + ": " + User.getCredits());
		casinoLabel.setText(texts.getString("casino.games"));
		arcadeLabel.setText(texts.getString("arcade.games"));
		logoutButton.setText(texts.getString("logout.button"));
		toStoreButton.setText(texts.getString("store"));
		toUserInfoButton.setText(texts.getString("user.details"));
	}
	
	public void toArcadeBlackjack1() {
		Stage window = (Stage) nameLabel.getScene().getWindow();
		Navigator.toArcadeBlackjack1(window);
	}
	
	public void toCasinoBlackjack1() {
		Stage window = (Stage) nameLabel.getScene().getWindow();
		Navigator.toCasinoBlackjack1(window);
	}
	
	public void toMoneyRain() {
		Navigator.toMoneyRain();
	}
	
	public void toSlalomMadness() {
		Navigator.toSlalomMadness();
	}
	
	public void toFastPoker() {
		Stage window = (Stage) nameLabel.getScene().getWindow();
		Navigator.toFastPoker(window);
	}
	
	public void toLuckySpins() {
		Stage window = (Stage) nameLabel.getScene().getWindow();
		Navigator.toLuckySpins(window);
	}
	
	public void toSpookySpins() {
		Stage window = (Stage) nameLabel.getScene().getWindow();
		Navigator.toSpookySpins(window);
	}
	
	public void toUserInfo(ActionEvent e) {
		Stage window = (Stage) nameLabel.getScene().getWindow();
		Navigator.toUserInfo(window);
	} 
	
	public void toStore(ActionEvent e) {
		Stage window = (Stage) nameLabel.getScene().getWindow();
		Navigator.toStore(window);
	}
	
	public void logout(ActionEvent e) {
		Stage window = (Stage) nameLabel.getScene().getWindow();
		Navigator.logout(window);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}
}

