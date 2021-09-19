package view;
import model.Tietokanta;
import model.Kayttaja;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
* Kontrolleri jolla ohjataan KirjautumisViewin toimintoja.
*/

public class KirjautumisViewController {
	@FXML private TextField kayttajatunnusInput;
	@FXML private TextField salasanaInput;
	@FXML private Button kirjauduButton;
	@FXML private Button torekisteroitymisButton;
	
	/**
	* Metodi käyttäjän kirjautumista varten. Lähetetään DAO:lle kirjautumiseen tarvittavat tiedot.
	*/
	public void kirjaudu(ActionEvent e) {
		if(kayttajatunnusInput.getText().isEmpty() || salasanaInput.getText().isEmpty()) {
			System.out.println("Kirjoita käyttäjätunnus ja salasana");
		}
		else {
			System.out.println("Käyttäjätunnus: " + kayttajatunnusInput.getText() + " Salasana: " + salasanaInput.getText());
			Kayttaja kayttaja = Tietokanta.login(kayttajatunnusInput.getText(), salasanaInput.getText());
			if(kayttaja != null) {
				System.out.println("Tervetuloa: " + kayttaja.getFirstname());
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