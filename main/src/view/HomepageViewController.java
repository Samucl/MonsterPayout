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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Tietokanta;
import model.User;

public class HomepageViewController implements Initializable{
	@FXML Label nameLabel;
	@FXML Text kolikotLabel;
	@FXML Text krediititLabel;
	@FXML Button logoutButton;
	@FXML Button toStoreButton;
	
	private void init() {
		nameLabel.setText(User.getUsername());
		kolikotLabel.setText("Kolikot: " + User.getCoins());
		krediititLabel.setText("Krediitit: " + User.getCredits());
	}
	
	public void toStore(ActionEvent e) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("StoreView.fxml"));
            GridPane storeView = (GridPane) loader.load();
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
            loader.setLocation(MainApplication.class.getResource("KirjautumisView.fxml"));
            AnchorPane kirjautumisView = (AnchorPane) loader.load();
            Scene loginScene = new Scene(kirjautumisView);
			Stage window = (Stage) toStoreButton.getScene().getWindow();
			Tietokanta.logout();
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
