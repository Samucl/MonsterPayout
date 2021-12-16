package view;
import java.net.URL;
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
import javafx.stage.Stage;
import model.Database;
import model.Session;
import model.User;

/**
 * Kirjautumis näkymän ohjain.
 * @author R12
 *
 */
public class LoginViewController implements Initializable {
	@FXML private TextField usernameInput;
	@FXML private PasswordField passwordInput;
	@FXML private Button loginButton;
	@FXML private Button signUpButton;
	@FXML private Label loginLabel;
	@FXML private MenuButton languageButton;
	private ResourceBundle texts;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	loginButton.setDefaultButton(true);
    	texts = Session.getLanguageBundle();
    	updateLanguage();
    }

    public void toEnglish(ActionEvent e) {
    	texts = Session.changeToLanguage("en", "US");
    	updateLanguage();
    }

    public void toFinnish(ActionEvent e) {
    	texts = Session.changeToLanguage("fi", "FI");
    	updateLanguage();
    }

    private void updateLanguage() {
    	loginLabel.setText(texts.getString("welcomeback"));
    	loginButton.setText(texts.getString("login"));
    	usernameInput.setPromptText(texts.getString("username"));
    	passwordInput.setPromptText(texts.getString("password"));
    	signUpButton.setText(texts.getString("join.button"));
    	languageButton.setText(texts.getString("language"));
    }

	/**
	* Metodi käyttäjän kirjautumista varten. Lähetetään DAO:lle kirjautumiseen tarvittavat tiedot.
	*/
	public void login(ActionEvent e) {
		if(usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty()) {
			insufficientInformation(texts.getString("empty.fields"), texts.getString("enter.username.password"));
		}
		else {
			Database.login(usernameInput.getText(), passwordInput.getText());
			if(Database.isLogged() && User.isAdmin() == 0) {
				toMainView();
			} else if (Database.isLogged() && User.isAdmin() == 1) {
				toStoreManagement();
			} else {
				insufficientInformation(texts.getString("incorrect.id"),texts.getString("user.not.found"));
			}
		}
	}

	private void insufficientInformation(String error, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error in registering");
		alert.setHeaderText(error);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void toMainView() {
		Stage window = (Stage) loginButton.getScene().getWindow();
		Navigator.toMainView(window);
	}

	public void toSignUp(ActionEvent e) {
		Stage window = (Stage) signUpButton.getScene().getWindow();
		Navigator.toSignUp(window);
	}

	public void toStoreManagement() {
		Stage window = (Stage) loginButton.getScene().getWindow();
		Navigator.toStoreManagement(window);
	}

}