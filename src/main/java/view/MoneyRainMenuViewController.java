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
import model.Database;
import model.Session;
import model.User;

/*
 * Luokka on toimii kontrollerina MoneyRain arcadepelin valikkonäkymälle
 * @author Samuel Laisaar
 */

public class MoneyRainMenuViewController implements Initializable {
	@FXML private Label highscoreLabel;
	@FXML private Label nameLabel;
	@FXML private Button playButton;
	@FXML private Label coinsLabel;
	@FXML private Label top1;
	@FXML private Label top2;
	@FXML private Label top3;
	@FXML private Label top4;
	@FXML private Label top5;
	@FXML private Label top6;
	@FXML private Label top7;
	@FXML private Label top8;
	@FXML private Label top9;
	@FXML private Label top10;
	private Stage window;
	private String[] top10List;
	
	private void init() {
		ResourceBundle texts = Session.getLanguageBundle();
		nameLabel.setText(User.getUsername());
		playButton.setText(texts.getString("play.button"));
		highscoreLabel.setText("0");
		coinsLabel.setText(texts.getString("coins") + ": " + User.getCoins());
		highscoreLabel.setText("" + Database.getHighScore("MoneyRain"));
		top10List = Database.getTop10("MoneyRain");
		setTop10(top1, 0);
		setTop10(top2, 1);
		setTop10(top3, 2);
		setTop10(top4, 3);
		setTop10(top5, 4);
		setTop10(top6, 5);
		setTop10(top7, 6);
		setTop10(top8, 7);
		setTop10(top9, 8);
		setTop10(top10, 9);
	}
	
	/*
	 * Haetaan top10 lista tietokannasta asetetaan Labeliin näkyviin
	 */
	private void setTop10(Label top, int i) {
		if(top10List == null) {
			top10List = new String[10];
		}
		else if(top10List.length > i)
			top.setText(top10List[i]);
		else
			top.setText("-");
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
