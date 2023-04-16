package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Database;
import model.Session;
import model.User;
import slalommadness.SlalomMadnessGame;

/*
 * Slalom Madness -pelin menu-näkymän kontrolleri.
 *
 * @author Jukka Hallikainen
 */
public class SlalomMadnessMenuViewController implements Initializable {
	@FXML private Label highScoreLabel;
	@FXML private Label nameLabel;
	@FXML private Button playBtn;
	@FXML private Button instructionBtn;
	@FXML private Label coinsLabel;
	@FXML private GridPane top10GridPane;

	private Stage window;

	private SlalomMadnessGame game;

	private ResourceBundle texts;

	private void init() {
		texts = Session.getLanguageBundle();

		nameLabel.setText(User.getUsername());
		coinsLabel.setText(texts.getString("coins") + ": "+ User.getCoins());
		playBtn.setText(texts.getString("play"));
		instructionBtn.setText(texts.getString("instructions"));

		highScoreLabel.setText(String.valueOf(Database.getHighScoreTime("Slalom Madness") + " s"));
		String[] top10List = Database.getTop10("Slalom Madness");

		if (top10List == null) {
			top10List = new String[10];
		}
		else {
			for (int i = 0 ; i < top10List.length ; i++) {
				String[] parts = top10List[i].split(": ");
				String part1 = parts[0];
				String part2 = parts[1];
				Label userLabel = new Label(part1);
				Label scoreLabel = new Label(part2);
				userLabel.setFont(Font.font("Ariel Black", FontWeight.BOLD, 12));
				scoreLabel.setFont(Font.font("Ariel Black", FontWeight.BOLD, 12));
				userLabel.setTextFill(Color.WHITE);
				scoreLabel.setTextFill(Color.WHITE);
				top10GridPane.add(userLabel, 0, i);
				top10GridPane.add(scoreLabel, 1, i);
			}
		}
		
	}

	/*
	 * Avaa pelinäkymän.
	 */
	public void play(ActionEvent e) {
		window = (Stage) playBtn.getScene().getWindow();
		window.setX(300); //Koordinaatit näytöllä, mihin ikkuna aukeaa (vasemmasta yläkulmasta)
		window.setY(8);

		game = new SlalomMadnessGame(window);
	}

	/*
	 * Luo peliohje-ikkunan.
	 */
	public void showInstructions() {
		try {
        	Stage dialog = new Stage();
        	dialog.setTitle(texts.getString("slalom.instructions.header"));

            VBox vbox = new VBox(4);
            vbox.setPadding(new Insets(16, 16, 16, 16));

            vbox.setBackground(new Background(new BackgroundFill(Color.rgb(42, 80, 102), CornerRadii.EMPTY, Insets.EMPTY)));
            Label instLabel = new Label();
            Label rewardsLabel = new Label();

            instLabel.setText(texts.getString("slalom.instructions"));
            rewardsLabel.setText(texts.getString("slalom.rewards"));

            instLabel.setFont(Font.font("Ariel Black", FontWeight.NORMAL, 13));
            instLabel.setTextFill(Color.WHITE);
            rewardsLabel.setFont(Font.font("Ariel Black", FontWeight.NORMAL, 13));
            rewardsLabel.setTextFill(Color.WHITE);

            vbox.getChildren().addAll(instLabel, rewardsLabel);

            Scene dialogScene = new Scene(vbox, 400, 334);
            dialog.setScene(dialogScene);
            dialog.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}
}
