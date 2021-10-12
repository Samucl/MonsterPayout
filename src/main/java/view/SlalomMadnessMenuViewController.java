package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import moneyrain.MoneyRain;
import slalommadness.SlalomMadnessGame;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Tietokanta;
import model.User;

public class SlalomMadnessMenuViewController implements Initializable {
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
	
	private SlalomMadnessGame game;
	
	private void init() {
		nameLabel.setText(User.getUsername());
		highscoreLabel.setText("0");
		coinsLabel.setText("Kolikot: " + User.getCoins());
		highscoreLabel.setText("" + Tietokanta.getHighScore("Slalom Madness"));
		String[] top10List = Tietokanta.getTop10("Slalom Madness");
		
		if (game != null) {
			game = null;
		}
		
		/*
		top1.setText(top10List[0]);
		top2.setText(top10List[1]);
		top3.setText(top10List[2]);
		top4.setText(top10List[3]);
		top5.setText(top10List[4]);
		top6.setText(top10List[5]);
		top7.setText(top10List[6]);
		top8.setText(top10List[7]);
		top9.setText(top10List[8]);
		top10.setText(top10List[9]);
		}	*/
	}
	
	public void play(ActionEvent e) {
		window = (Stage) playButton.getScene().getWindow();
		window.setX(300); //Koordinaatit näytöllä, mihin ikkuna aukeaa (vasemmasta yläkulmasta)
		window.setY(8);

		game = new SlalomMadnessGame(window);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}
}
