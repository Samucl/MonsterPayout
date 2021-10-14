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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Tietokanta;
import model.User;

public class SlalomMadnessMenuViewController implements Initializable {
	@FXML private Label highScoreLabel;
	@FXML private Label nameLabel;
	@FXML private Button playButton;
	@FXML private Label coinsLabel;
	@FXML private GridPane top10GridPane;
	
	private Stage window;
	
	private SlalomMadnessGame game;
	
	private void init() {
		nameLabel.setText(User.getUsername());
		coinsLabel.setText("Kolikot: " + User.getCoins());
		highScoreLabel.setText(String.valueOf(Tietokanta.getHighScoreTime("Slalom Madness") + " s"));
		String[] top10List = Tietokanta.getTop10("Slalom Madness");
		
		if (top10List == null) {
			top10List = new String[10];
		}
		
		for (int i = 0 ; i < top10List.length ; i++) {
			String[] parts = top10List[i].split(": ");
			String part1 = parts[0];
			String part2 = parts[1]; 
			Label userLabel = new Label(part1);
			Label scoreLabel = new Label(part2);
			userLabel.setFont(Font.font("Ariel Black", FontWeight.BOLD, 12));
			scoreLabel.setFont(Font.font("Ariel Black", FontWeight.BOLD, 12));
			top10GridPane.add(userLabel, 0, i);
			top10GridPane.add(scoreLabel, 1, i);
		}	
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
