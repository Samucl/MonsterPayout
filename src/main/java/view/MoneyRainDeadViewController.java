package view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Tietokanta;

public class MoneyRainDeadViewController{
	
	@FXML private Button toMenu;
	@FXML private Label pointsLabel;
	@FXML private Label coinsWonLabel;
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
		Tietokanta.setHighScore(points, "MoneyRain");
		pointsLabel.setText("Pisteet: " + points);
		int coinsToGive = points/20; //Pelaajan kolikot on pelin pisteet/20, eli esim 200 pistettä pelistä on 10 kolikkoa
		if(points > 100) { //Pelaajalla on oltava ainakin 100 pistettä jotta ansaitsee kolikoita
			coinsWonLabel.setText("Voitit " + coinsToGive + " kolikkoa!");
			Tietokanta.increaseCoinBalance(coinsToGive);
		}
	}
	
	public int getPoints() {
		return points;
	}
}
