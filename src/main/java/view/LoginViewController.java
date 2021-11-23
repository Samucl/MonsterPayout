package view;
import model.Database;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/** * Kontrolleri jolla ohjataan LoginViewin toimintoja.
*/

public class LoginViewController implements Initializable {
	@FXML private TextField kayttajatunnusInput;
	@FXML private PasswordField salasanaInput;
	@FXML private Button kirjauduButton;
	@FXML private Button torekisteroitymisButton;
	
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	kirjauduButton.setDefaultButton(true);
    }
	
	/**
	* Metodi käyttäjän kirjautumista varten. Lähetetään DAO:lle kirjautumiseen tarvittavat tiedot.
	*/
	public void kirjaudu(ActionEvent e) {
		if(kayttajatunnusInput.getText().isEmpty() || salasanaInput.getText().isEmpty()) {
			System.out.println("Kirjoita käyttäjätunnus ja salasana");
			insufficientInformation("Tyhjiä kenttiä","Syötä käyttäjänimi ja salasana.");
		}
		else {
			System.out.println("Käyttäjätunnus: " + kayttajatunnusInput.getText() + " Salasana: " + salasanaInput.getText());
			Database.login(kayttajatunnusInput.getText(), salasanaInput.getText());
			if(Database.isLogged() && User.isAdmin() == 0) {
				
				try {
		            FXMLLoader loader = new FXMLLoader();
		            loader.setLocation(MainApplication.class.getResource("HomepageView.fxml"));
		            BorderPane homepageView = (BorderPane) loader.load();
		            Scene homepageScene = new Scene(homepageView);
					Stage window = (Stage) kirjauduButton.getScene().getWindow();
					window.setScene(homepageScene);
		        } catch (IOException iOE) {
		            iOE.printStackTrace();
		        }
				
				System.out.println("Tervetuloa: " + User.getFirstname());
			}
			
			else if (Database.isLogged() && User.isAdmin() == 1) {
				toSetProductsView();
			} else {
				insufficientInformation("Virheelliset tunnukset","Käyttäjää ei löydy tai salasana on väärä.");
			}
		}
	}
	
	private void insufficientInformation(String error, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Register error");
		alert.setHeaderText(error);
		alert.setContentText(message);

		alert.showAndWait();
	}
	
	/**
	* Metodi jolla asetetaan rekisteroitniScene nykyiseen Stageen
	*/
	public void toRekisteroityminen(ActionEvent e) {	
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("RegisterView.fxml"));
            BorderPane registerView = (BorderPane) loader.load();
            Scene registerScene = new Scene(registerView);
			Stage window = (Stage) torekisteroitymisButton.getScene().getWindow();
			window.setScene(registerScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void toSetProductsView() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("SetProductsView.fxml"));
            GridPane spView = (GridPane) loader.load();
            Scene spScene = new Scene(spView);
			Stage window = (Stage) kirjauduButton.getScene().getWindow();
			window.setScene(spScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
}