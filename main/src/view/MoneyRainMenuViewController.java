package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import moneyrain.MoneyRain;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;

public class MoneyRainMenuViewController implements Initializable {
	@FXML Label highscoreLabel;
	@FXML Label nameLabel;
	@FXML Button playButton;
	
	private void init() {
		nameLabel.setText(User.getUsername());
		highscoreLabel.setText("Hae tietokannasta!!");
	}
	
	public void play(ActionEvent e) {
		Stage window = (Stage) playButton.getScene().getWindow();
		MoneyRain moneyRain = new MoneyRain(window);
	}
	
	public void exit(ActionEvent e) {
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}
}
