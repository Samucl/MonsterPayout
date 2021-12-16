package view;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Database;
import model.Session;

/**
 *	Kontrolleri jolla ohjataan RegisterViewin toimintoja.
*/

public class RegisterViewController implements Initializable{
	@FXML private TextField email;
	@FXML private TextField firstname;
	@FXML private TextField lastname;
	@FXML private TextField username;
	@FXML private PasswordField password;
	@FXML private PasswordField passwordAgain;
	@FXML private Button signUpButton;
	@FXML private Button toLoginButton;
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
    	texts = Session.changeToLanguage("en", "US");
    	updateLanguage();
    	setBackgroundLogo();
    }

    public void toFinnish(ActionEvent e) throws FileNotFoundException {
    	texts = Session.changeToLanguage("fi", "FI");
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
    	email.setPromptText(texts.getString("email"));
    	firstname.setPromptText(texts.getString("firstname"));
    	lastname.setPromptText(texts.getString("lastname"));
    	username.setPromptText(texts.getString("username"));
    	password.setPromptText(texts.getString("password"));
    	passwordAgain.setPromptText(texts.getString("password.again"));
    	signUpButton.setText(texts.getString("join.button"));
    	toLoginButton.setText(texts.getString("account.already"));
    	languageButton.setText(texts.getString("language"));
    }

	/**
	* Metodi käyttäjän rekisteröintiä varten. Lähetetään DAO:lle rekisteröintiin tarvittavat tiedot.
	*/
	public void signUp(ActionEvent e) {
		if(!password.getText().equals(passwordAgain.getText())) {
			insufficientInformation(texts.getString("passwords.not.match"),
					texts.getString("entered.passwords.not.match"));

		} else if(username.getText().isEmpty() || password.getText().isEmpty() || firstname.getText().isEmpty() || lastname.getText().isEmpty()
				|| email.getText().isEmpty() || passwordAgain.getText().isEmpty()) {
			insufficientInformation(texts.getString("fill.information"),
					texts.getString("fill.information.incorrect"));

		} else {
			Boolean success = Database.register(username.getText(), password.getText(), email.getText(), firstname.getText(), lastname.getText());

			if(success)
				successfulRegistration();
			else
				insufficientInformation(texts.getString("register.error"),
						texts.getString("register.error.something"));

		}
	}


	private void insufficientInformation(String error, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error in registering");
		alert.setHeaderText(error);
		alert.setContentText(message);

		alert.showAndWait();
	}

	private void successfulRegistration() {
		Database.login(username.getText(), password.getText());
		toLoginButton.fire();
	}


	public void toLoginView(ActionEvent e) {
		Stage window = (Stage) toLoginButton.getScene().getWindow();
		Navigator.toLogin(window);
	}

}
