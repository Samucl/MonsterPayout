package view;

import javafx.geometry.Insets;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import moneyrain.MoneyRain;
import slalommadness.SlalomMadnessGame;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Database;
import model.User;

public class SlalomMadnessMenuViewController implements Initializable {
	@FXML private Label highScoreLabel;
	@FXML private Label nameLabel;
	@FXML private Button playButton;
	@FXML private Button instructionBtn;
	@FXML private Label coinsLabel;
	@FXML private GridPane top10GridPane;
	
	private Stage window;
	
	private SlalomMadnessGame game;
	
	private void init() {
		nameLabel.setText(User.getUsername());
		coinsLabel.setText("Kolikot: " + User.getCoins());
		highScoreLabel.setText(String.valueOf(Database.getHighScoreTime("Slalom Madness") + " s"));
		String[] top10List = Database.getTop10("Slalom Madness");
		
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
			userLabel.setTextFill(Color.WHITE);
			scoreLabel.setTextFill(Color.WHITE);
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
	
	//Peliohje-ikkunan luonti
	public void showInstruction() {
		try {
        	Stage dialog = new Stage();
        	dialog.setTitle("Slalom Madness - ohje");
        //    dialog.initModality(Modality.APPLICATION_MODAL); //Taustalla olevaa ikkunaa ei voi klikkailla, ennenkuin tämä suljetaan
            
            VBox vbox = new VBox(4);
            vbox.setPadding(new Insets(16, 16, 16, 16));
            
            vbox.setBackground(new Background(new BackgroundFill(Color.rgb(42, 80, 102), CornerRadii.EMPTY, Insets.EMPTY)));
            Label instLabel = new Label();
            Label rewardsLabel = new Label();
            
            instLabel.setText("Liiku nuolinäppäimillä. Väistele esteitä ja yritä päästä maaliin.\n"
            		+ "Kierrä punaiset kepit vasemmalta ja siniset oikealta.\n"
            		+ "Jokaisesta ohilasketusta kepistä saat kaksi sekuntia sanktiota.\n\n");
            
            
            rewardsLabel.setText("Palkinnot: \n"
            		+ "> 60 s : 1 kolikko \n"
            		+ "> 55 s : 2 kolikkoa \n"
            		+ "> 50 s : 3 kolikkoa \n"
            		+ "> 45 s : 4 kolikkoa \n"
            		+ "> 44 s : 5 kolikkoa \n"
            		+ "> 43 s : 6 kolikkoa \n"
            		+ "> 42 s : 7 kolikkoa \n"
            		+ "> 41 s : 8 kolikkoa \n"
            		+ "< 40 s : 10 kolikkoa \n");
            
            instLabel.setFont(Font.font("Ariel Black", FontWeight.NORMAL, 13));
            instLabel.setTextFill(Color.WHITE);
            rewardsLabel.setFont(Font.font("Ariel Black", FontWeight.NORMAL, 13));
            rewardsLabel.setTextFill(Color.WHITE);
            
            vbox.getChildren().addAll(instLabel, rewardsLabel);
            
            Scene dialogScene = new Scene(vbox, 400, 315);
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
