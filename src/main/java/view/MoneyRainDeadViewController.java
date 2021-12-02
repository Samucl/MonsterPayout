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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Database;
import model.Session;

public class MoneyRainDeadViewController{
	
	@FXML private Button toMenu;
	@FXML private Label pointsLabel;
	@FXML private Label coinsWonLabel;
	@FXML private Label deadLabel;
	private int points;
	
	
	public void toMenu(ActionEvent e) {
		try {
		     FXMLLoader loader = new FXMLLoader();
		     loader.setLocation(MainApplication.class.getResource("MoneyRainMenuView.fxml"));
		     AnchorPane moneyrainmenuView = (AnchorPane) loader.load();
		     Scene moneyrainmenuScene = new Scene(moneyrainmenuView);
			 Stage window = (Stage) toMenu.getScene().getWindow();
		     window.setScene(moneyrainmenuScene);
		     window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	
	public void setPoints(int pointsFromGame) {
		points = pointsFromGame;
		Database.setHighScore(points, "MoneyRain");
		
		ResourceBundle texts = Session.getLanguageBundle();
		toMenu.setText(texts.getString("return.to.menu"));
		deadLabel.setText(texts.getString("you.died"));
		pointsLabel.setText(texts.getString("points") + ": " + points);
		int coinsToGive = points/20; //Pelaajan kolikot on pelin pisteet/20, eli esim 200 pistettä pelistä on 10 kolikkoa
		if(points > 100) { //Pelaajalla on oltava ainakin 100 pistettä jotta ansaitsee kolikoita
			coinsWonLabel.setText(texts.getString("you.won") + " " + coinsToGive + " " + texts.getString("coins").substring(0,1).toLowerCase() + texts.getString("coins.partitive").substring(1) + "!");
			Database.increaseCoinBalance(coinsToGive);
		}
	}
	
	public int getPoints() {
		return points;
	}
}
