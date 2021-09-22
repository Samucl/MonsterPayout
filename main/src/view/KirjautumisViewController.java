package view;
import model.Tietokanta;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/** * Kontrolleri jolla ohjataan KirjautumisViewin toimintoja.
*/

public class KirjautumisViewController implements Initializable {
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
		}
		else {
			System.out.println("Käyttäjätunnus: " + kayttajatunnusInput.getText() + " Salasana: " + salasanaInput.getText());
			Tietokanta.login(kayttajatunnusInput.getText(), salasanaInput.getText());
			if(Tietokanta.isLogged()) {
				
				try {
		            FXMLLoader loader = new FXMLLoader();
		            loader.setLocation(MainApplication.class.getResource("HomepageView.fxml"));
		            AnchorPane homepageView = (AnchorPane) loader.load();
		            Scene homepageScene = new Scene(homepageView);
					Stage window = (Stage) kirjauduButton.getScene().getWindow();
					window.setScene(homepageScene);
		        } catch (IOException iOE) {
		            iOE.printStackTrace();
		        }
				
				System.out.println("Tervetuloa: " + User.getFirstname());
			}
		}
	}
	
	/**
	* Metodi jolla asetetaan rekisteroitniScene nykyiseen Stageen
	*/
	public void toRekisteroityminen(ActionEvent e) {	
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("RekisterointiView.fxml"));
            AnchorPane rekisterointiView = (AnchorPane) loader.load();
            Scene rekisterointiScene = new Scene(rekisterointiView);
			Stage window = (Stage) torekisteroitymisButton.getScene().getWindow();
			window.setScene(rekisterointiScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
}