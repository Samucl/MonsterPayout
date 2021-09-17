package view;

import model.Tietokanta;
import model.Kayttaja;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class KirjautumisViewController {
	@FXML
	private TextField kayttajatunnusInput;
	@FXML
	private TextField salasanaInput;
	@FXML
	private Button kirjauduButton;
	
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
}