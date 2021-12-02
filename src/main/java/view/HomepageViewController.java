
package view;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.prism.paint.Stop;

import moneyrain.MoneyRain;
import slalommadness.SlalomMadnessGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
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
	@FXML Button toArcadeBlackjack1;
	@FXML Button toCasinoBlackjack1;
	@FXML Button toMoneyRain;
	@FXML Button toSlalomMadness;
	@FXML Button toFastPoker;
	@FXML Label casinoLabel;
	@FXML Label arcadeLabel;
	
	private void init() {
		useLanguageBundle();

	}
	
	private void useLanguageBundle() {
		ResourceBundle texts = Session.getLanguageBundle();
		nameLabel.setText(uppercase(texts,"welcome") + " " + User.getUsername() + "!");
		kolikotLabel.setText(uppercase(texts,"coins") + ": " + User.getCoins());
		//krediititLabel.setText("Krediitit: " + User.getCredits());
	}
	
    private String uppercase(ResourceBundle rb, String string) {
    	return rb.getString(string).substring(0, 1).toUpperCase() + rb.getString(string).substring(1).toLowerCase();
    }
	
	public void toArcadeBlackjack1() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("ArcadeBlackjack1View2.fxml"));
            BorderPane blackjackView = (BorderPane) loader.load();
            Scene blackjackScene = new Scene(blackjackView);
			Stage window = (Stage) nameLabel.getScene().getWindow();
			window.setScene(blackjackScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void toCasinoBlackjack1() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("CasinoBlackjack1View.fxml"));
            BorderPane blackjackView = (BorderPane) loader.load();
            Scene blackjackScene = new Scene(blackjackView);
			Stage window = (Stage) nameLabel.getScene().getWindow();
			window.setScene(blackjackScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void toMoneyRain() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("MoneyRainMenuView.fxml"));
            AnchorPane moneyrainmenuView = (AnchorPane) loader.load();
            Scene moneyrainmenuScene = new Scene(moneyrainmenuView);
			Stage window = new Stage();
			window.setOnCloseRequest(evt -> {
				if(MoneyRain.getTl() != null) {
					MoneyRain.getTl().stop();//Pysäyttää pelin timelinen jos suljetaan ikkuna kesken pelin.
				}
			});
			window.setScene(moneyrainmenuScene);
			window.setResizable(false);
			window.show();
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void toSlalomMadness() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("SlalomMadnessMenuView.fxml"));
            AnchorPane slalomMenuView = (AnchorPane) loader.load();
            Scene slalomMenuScene = new Scene(slalomMenuView);
			Stage window = new Stage();
		/*	window.setOnCloseRequest(evt -> {
				if(SlalomMadnessGame.getTl() != null) {
					SlalomMadnessGame.getTl().stop();//Pysäyttää pelin timelinen jos suljetaan ikkuna kesken pelin.
				}
			}); */
			window.setScene(slalomMenuScene);
			window.setResizable(false);
			window.show();
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void toFastPoker() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("Fast_pokerView.fxml"));
            BorderPane fastPokerView = (BorderPane) loader.load();
            Scene fastPokerScene = new Scene(fastPokerView);
			Stage window = (Stage) nameLabel.getScene().getWindow();
			window.setScene(fastPokerScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void toLuckySpins() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("LuckySpinsView.fxml"));
            BorderPane luckySpinsView = (BorderPane) loader.load();
            Scene luckySpinsScene = new Scene(luckySpinsView);
			Stage window = (Stage) nameLabel.getScene().getWindow();
			window.setScene(luckySpinsScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void toUserInfo(ActionEvent e) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("UserInfoView.fxml"));
            AnchorPane userInfoView = (AnchorPane) loader.load();
            Scene userInfoScene = new Scene(userInfoView);
			Stage window = (Stage) toUserInfoButton.getScene().getWindow();
			window.setScene(userInfoScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void toStore(ActionEvent e) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("StoreView.fxml"));
            BorderPane storeView = (BorderPane) loader.load();
            Scene storeScene = new Scene(storeView);
            
  
			Stage window = (Stage) toStoreButton.getScene().getWindow();
			window.setScene(storeScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void logout(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("LoginView.fxml"));
            BorderPane loginView = (BorderPane) loader.load();
            Scene loginScene = new Scene(loginView);
			Stage window = (Stage) toStoreButton.getScene().getWindow();
			Database.logout();
			window.setScene(loginScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}
}

