package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import moneyrain.MoneyRain;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
	@FXML private Label top1, top2, top3, top4, top5, top6, top7, top8, top9, top10;
	@FXML private Button instructionButton;
	private Stage window;
	private String[] top10List;
	private ResourceBundle texts;
	
	private void init() {
		texts = Session.getLanguageBundle();
		nameLabel.setText(User.getUsername());
		playButton.setText(texts.getString("play.button"));
		highscoreLabel.setText("0");
		coinsLabel.setText(texts.getString("coins") + ": " + User.getCoins());
		highscoreLabel.setText("" + Database.getHighScore("MoneyRain"));
		top10List = Database.getTop10("MoneyRain");
		instructionButton.setText(texts.getString("instructions"));
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
	
	//Peliohje-ikkunan luonti
	public void showInstruction() {
		try {
        	Stage dialog = new Stage();
        	dialog.setTitle("MoneyRain - " + texts.getString("instructions"));
            
            VBox vbox = new VBox(4);
            vbox.setPadding(new Insets(16, 16, 16, 16));
            
            vbox.setBackground(new Background(new BackgroundFill(Color.rgb(82, 52, 34), CornerRadii.EMPTY, Insets.EMPTY)));
            Label instLabel = new Label();
            Label rewardsLabel = new Label();
            
            instLabel.setText(texts.getString("MoneyRain.inst"));
            
            rewardsLabel.setText(texts.getString("MoneyRain.rewards"));
            
            instLabel.setFont(Font.font("Ariel Black", FontWeight.NORMAL, 13));
            instLabel.setTextFill(Color.WHITE);
            rewardsLabel.setFont(Font.font("Ariel Black", FontWeight.NORMAL, 13));
            rewardsLabel.setTextFill(Color.WHITE);
            
            vbox.getChildren().addAll(instLabel, rewardsLabel);
            
            Scene dialogScene = new Scene(vbox, 460, 220);
            dialog.setScene(dialogScene);
            dialog.showAndWait();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
