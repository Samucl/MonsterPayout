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

public class MoneyRainDeadViewController implements Initializable {
	
	@FXML private Button toMenu;
	@FXML private Label pointsLabel;
	private int points;
	
	private void init() {
		pointsLabel.setText("" + points);
	}
	
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}
	
	public void setPoints(int pointsFromGame) {
		points = pointsFromGame;
	}
}
