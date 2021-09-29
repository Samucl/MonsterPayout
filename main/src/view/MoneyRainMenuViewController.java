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
	@FXML private Label highscoreLabel;
	@FXML private Label nameLabel;
	@FXML private Button playButton;
	@FXML private Label coinsLabel;
	@FXML private Label top1;
	@FXML private Label top3;
	@FXML private Label top4;
	@FXML private Label top5;
	@FXML private Label top6;
	@FXML private Label top7;
	@FXML private Label top8;
	@FXML private Label top9;
	@FXML private Label top10;
	private Stage window;
	
	private void init() {
		nameLabel.setText(User.getUsername());
		highscoreLabel.setText("0");
		coinsLabel.setText("Coins: " + User.getCoins());
	}
	
	public void play(ActionEvent e) {
		window = (Stage) playButton.getScene().getWindow();
		new MoneyRain(window);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}
}
