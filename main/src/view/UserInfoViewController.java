package view;

import java.awt.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.User;

public class UserInfoViewController implements Initializable {
	@FXML private TextField username;
	@FXML private TextField firstname;
	@FXML private TextField lastname;
	@FXML private TextField email;
	@FXML private TextField account_number;
	@FXML private TextField login_streak;
	//@FXML private ListView purchase_history;
	
	@Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	init();
    }
	
	private void init() {
		username.setText(User.getUsername());
		firstname.setText(User.getFirstname());
		lastname.setText(User.getLastname());
		email.setText(User.getEmail());
		account_number.setText(User.getAccountNumber());
		login_streak.setText(Integer.toString(User.getLoginStreak()));
	}

}
