package view;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Database;
import model.Session;

/**
 *	Kontrolleri jolla ohjataan RegisterViewin toimintoja.
*/

public class RegisterViewController implements Initializable{
	@FXML private TextField sahkopostiInput;
	@FXML private TextField etunimiInput;
	@FXML private TextField sukunimiInput;
	@FXML private TextField kayttajatunnusInput2;
	@FXML private PasswordField salasanaInput2;
	@FXML private PasswordField salasanauudelleenInput;
	@FXML private Button rekisteroidyButton;
	@FXML private Button tokirjautumisButton;
	@FXML private Label registerLabel;
	@FXML private MenuButton languageButton;
	@FXML private ImageView logoImageView;
	private ResourceBundle texts;
	
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	texts = Session.getLanguageBundle();
    	updateLanguage();
    	
    	try {
			setBackgroundLogo();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void toEnglish(ActionEvent e) throws FileNotFoundException {
    	texts = ResourceBundle.getBundle("lang.language",new Locale("en", "US"));
    	Session.setLanguageBundle(texts);
    	updateLanguage();
    	setBackgroundLogo();
    }
    
    public void toFinnish(ActionEvent e) throws FileNotFoundException {
    	texts = ResourceBundle.getBundle("lang.language",new Locale("fi", "FI"));
    	Session.setLanguageBundle(texts);
    	updateLanguage();
    	setBackgroundLogo();
    }
    
    private void setBackgroundLogo() throws FileNotFoundException {
    	if(texts.getLocale().equals(new Locale("fi", "FI")))
        	logoImageView.setImage(new Image(new FileInputStream("./src/main/resources/bigLogo2fi.png")));
    	else
    		logoImageView.setImage(new Image(new FileInputStream("./src/main/resources/bigLogo2en.png")));
    }
    
    private void updateLanguage() {
    	registerLabel.setText(texts.getString("register.button"));
    	sahkopostiInput.setPromptText(texts.getString("email"));
    	etunimiInput.setPromptText(texts.getString("firstname"));
    	sukunimiInput.setPromptText(texts.getString("lastname"));
    	kayttajatunnusInput2.setPromptText(texts.getString("username"));
    	salasanaInput2.setPromptText(texts.getString("password"));
    	salasanauudelleenInput.setPromptText(texts.getString("password.again"));
    	rekisteroidyButton.setText(texts.getString("join.button"));
    	tokirjautumisButton.setText(texts.getString("account.already"));
    	languageButton.setText(texts.getString("language"));
    }
	
	/**
	* Metodi käyttäjän rekisteröintiä varten. Lähetetään DAO:lle rekisteröintiin tarvittavat tiedot.
	*/
	public void rekisteroidy(ActionEvent e) {
		if(!salasanaInput2.getText().equals(salasanauudelleenInput.getText())) {
			insufficientInformation(texts.getString("passwords.not.match"),
					texts.getString("entered.passwords.not.match"));
			
		} else if(kayttajatunnusInput2.getText().isEmpty() || salasanaInput2.getText().isEmpty() || etunimiInput.getText().isEmpty() || sukunimiInput.getText().isEmpty() 
				|| sahkopostiInput.getText().isEmpty() || salasanauudelleenInput.getText().isEmpty()) {
			insufficientInformation(texts.getString("fill.information"),
					texts.getString("fill.information.incorrect"));
			
		} else {
			Boolean onnistuiko = Database.register(kayttajatunnusInput2.getText(), salasanaInput2.getText(), sahkopostiInput.getText(), etunimiInput.getText(), sukunimiInput.getText());
			
			if(onnistuiko)
				successfulRegistration();
			else
				insufficientInformation(texts.getString("register.error"),
						texts.getString("register.error.something"));
				
		}
	}
	

	private void insufficientInformation(String error, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Register error");
		alert.setHeaderText(error);
		alert.setContentText(message);

		alert.showAndWait();
	}
	
	private void successfulRegistration() {
		Database.login(kayttajatunnusInput2.getText(), salasanaInput2.getText());
		tokirjautumisButton.fire();
	}
	
	
	public void toLoginView(ActionEvent e) {	
		Stage window = (Stage) tokirjautumisButton.getScene().getWindow();
		Navigator.toLogin(window);
	}
	
}
