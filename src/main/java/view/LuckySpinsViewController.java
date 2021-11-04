package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Tietokanta;
import model.User;
import slotgames.SlotMachine;

public class LuckySpinsViewController implements Initializable{
	
	private static final String[] icons = new String[] {"strawberry", "spade", "clover", "diamond", "seven"};
	@FXML Button toMenu;
	@FXML ImageView gif1;
	@FXML ImageView gif2;
	@FXML ImageView gif3;
	@FXML ImageView gif4;
	@FXML ImageView gif5;
	@FXML ImageView gif6;
	@FXML ImageView gif7;
	@FXML ImageView gif8;
	@FXML ImageView gif9;
	@FXML Button spinButton;
	@FXML Label winLabel;
	@FXML ToggleButton payline1;
	@FXML ToggleButton payline2;
	@FXML ToggleButton payline3;
	@FXML Label balanceLabel;
	
	private void init() {
		payline1.setSelected(false);
		payline2.setSelected(true);
		payline3.setSelected(false);
		Label balanceLabel = new Label();
		balanceLabel.setText("Saldo: " + User.getCredits());
	}
	
	public void toMenu(ActionEvent e) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("HomepageView.fxml"));
            BorderPane mainView = (BorderPane) loader.load();
            Scene mainScene = new Scene(mainView);
			Stage window = (Stage) toMenu.getScene().getWindow();
			window.setScene(mainScene);
			
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void spin(ActionEvent e) throws FileNotFoundException{
		SlotMachine game1 = new SlotMachine();
		SlotMachine game2 = new SlotMachine();
		SlotMachine game3 = new SlotMachine();
		game1.play();
		game2.play();
		game3.play();
		Timeline tl = new Timeline(new KeyFrame(Duration.millis(2000), ea -> {
				try {showIcons(game1.getOutcome(),game2.getOutcome(),game3.getOutcome());} catch (FileNotFoundException e1) {}
				spinButton.setDisable(false);
				int winning = 0;
				if(game1.checkWin() && payline1.isSelected()) {
					winning += game1.payout();
				}
				if(game2.checkWin() && payline2.isSelected()) {
					winning += game2.payout();
				}
				if(game3.checkWin() && payline3.isSelected()) {
					winning += game3.payout();
				}
				if((game1.checkWin() && payline1.isSelected())  || (game2.checkWin() && payline2.isSelected())  || (game3.checkWin() && payline3.isSelected())) {
					winLabel.setText("Voitit " + winning + " krediittiä");
					Tietokanta.increaseCreditBalance(winning);
					balanceLabel.setText("Saldo: " + User.getCredits());
				}
				else
					winLabel.setText("Ei voittoa");
		}));

		tl.setCycleCount(1);
		tl.play();
		spinAnimation();
		spinButton.setDisable(true);
		winLabel.setText("Onnea peliin!");
	}
	
	public void paylinePress1(ActionEvent e) {
		paylineColor(payline1);
	}
	public void paylinePress2(ActionEvent e) {
		paylineColor(payline2);
	}
	public void paylinePress3(ActionEvent e) {
		paylineColor(payline3);
	}
	
	private void paylineColor(ToggleButton payline) {
		if(payline.isSelected()) {
			payline.setStyle("-fx-background-color:  #6bd600");
		}
		else {
			payline.setStyle("-fx-background-color:  #132402");
		}
	}
	private void spinAnimation() throws FileNotFoundException {
		gif1.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/spin1.gif")));
		gif4.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/spin2.gif")));
		gif7.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/spin3.gif")));
		gif2.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/spin1.gif")));
		gif5.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/spin2.gif")));
		gif8.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/spin3.gif")));
		gif3.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/spin1.gif")));
		gif6.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/spin2.gif")));
		gif9.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/spin3.gif")));
	}
	
	private void showIcons(int[] outcome1, int[] outcome2 ,int[] outcome3) throws FileNotFoundException {
		gif1.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[outcome1[0]-1] + ".gif")));
		gif4.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[outcome2[0]-1] + ".gif")));
		gif7.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[outcome3[0]-1] + ".gif")));
		gif2.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[outcome1[1]-1] + ".gif")));
		gif5.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[outcome2[1]-1] + ".gif")));
		gif8.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[outcome3[1]-1] + ".gif")));
		gif3.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[outcome1[2]-1] + ".gif")));
		gif6.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[outcome2[2]-1] + ".gif")));
		gif9.setImage(new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[outcome3[2]-1] + ".gif")));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}
	
	
}
