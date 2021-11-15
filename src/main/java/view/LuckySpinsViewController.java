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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Tietokanta;
import model.User;
import slotgames.SlotMachine;

public class LuckySpinsViewController implements Initializable{
	
	private static final String[] icons = new String[] {"strawberry", "spade", "clover", "diamond", "seven", "wild"};
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
	@FXML Rectangle rectangle;
	@FXML Label setSpinsLabel;
	@FXML Button setSpinsButton;
	@FXML Button setSpins1;
	@FXML Button setSpins2;
	@FXML Button setSpins3;
	@FXML Button setSpins4;
	@FXML Button setSpins5;
	@FXML Button betButton;
	@FXML Button turboButton;
	@FXML Label spinsLeftLabel;
	private Image spin1, spin2, spin3;
	private Image[] gifs = new Image[6];
	private boolean isSetSpin = false;
	private boolean isTurbo = false;
	private int autoSpins = 0;
	private int spinsLeft = 0;
	private int winning = 0;
	private double bet = 1;
	
	private void init() throws FileNotFoundException {
		payline1.setSelected(false);
		payline2.setSelected(true);
		payline3.setSelected(false);
		balanceLabel.setText("Saldo: " + User.getCredits());
		betButton.setText("Panos: " + (int)bet);
		spin1 = new Image(new FileInputStream("./src/main/resources/slot_icons/spin1.gif"));
		spin2 = new Image(new FileInputStream("./src/main/resources/slot_icons/spin2.gif"));
		spin3 = new Image(new FileInputStream("./src/main/resources/slot_icons/spin3.gif"));
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
		setButtonsDisable(true);
			if(isTurbo)
				manualSpin(100);
			else
				manualSpin(1000);
	}
	
	private void manualSpin(int timeInMillis) throws FileNotFoundException{
		SlotMachine game1 = new SlotMachine();
		SlotMachine game2 = new SlotMachine();
		SlotMachine game3 = new SlotMachine();
		game1.play();
		game2.play();
		game3.play();
		
		Tietokanta.decreaseCreditBalance((int)bet);
		balanceLabel.setText("Saldo: " + User.getCredits());
		winning = 0;
		if(game1.checkWin() && payline1.isSelected())
			winning += game1.payout();
		if(game2.checkWin() && payline2.isSelected())
			winning += game2.payout();
		if(game3.checkWin() && payline3.isSelected())
			winning += game3.payout();
		if(isWin(game1,game2,game3))
			Tietokanta.increaseCreditBalance(winning * bet);
		
		Timeline tl = new Timeline(new KeyFrame(Duration.millis(timeInMillis), ea -> {
				try {
					showIcons(game1.getOutcome(),game2.getOutcome(),game3.getOutcome());
					System.out.println(spinsLeft);
					if(spinsLeft>1) {
						spinsLeftLabel.setText("Pyöräytykset: " + (--spinsLeft));
					}
					else {
						setButtonsDisable(false);
						spinsLeftLabel.setVisible(false);
					}
				} catch (FileNotFoundException e1) {}
				if(isWin(game1,game2,game3)) {
					winLabel.setText("Voitit " + (winning*(int)bet) + " krediittiä");
					balanceLabel.setText("Saldo: " + User.getCredits());
				}
				else
					winLabel.setText("Ei voittoa");
		}));
		tl.setCycleCount(1);
		tl.play();
		spinAnimation();
		winLabel.setText("Onnea peliin!");
	}
	
	private void setButtonsDisable(Boolean bool) {
		spinButton.setDisable(bool);
		setSpinsButton.setDisable(bool);
		betButton.setDisable(bool);
		turboButton.setDisable(bool);
	}
	
	private boolean isWin(SlotMachine game1, SlotMachine game2, SlotMachine game3) {
			return (game1.checkWin() && payline1.isSelected())  || (game2.checkWin() && payline2.isSelected())  || (game3.checkWin() && payline3.isSelected());
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
		gif1.setImage(spin1);
		gif4.setImage(spin2);
		gif7.setImage(spin3);
		gif2.setImage(spin1);
		gif5.setImage(spin2);
		gif8.setImage(spin3);
		gif3.setImage(spin1);
		gif6.setImage(spin2);
		gif9.setImage(spin3);
	}
	
	private void showIcons(int[] outcome1, int[] outcome2 ,int[] outcome3) throws FileNotFoundException {
		gifs[0] = new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[0] + ".gif"));
		gifs[1] = new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[1] + ".gif"));
		gifs[2] = new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[2] + ".gif"));
		gifs[3] = new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[3] + ".gif"));
		gifs[4] = new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[4] + ".gif"));
		gifs[5] = new Image(new FileInputStream("./src/main/resources/slot_icons/" + icons[5] + ".gif"));
		gif1.setImage(gifs[outcome1[0]-1]);
		gif4.setImage(gifs[outcome2[0]-1]);
		gif7.setImage(gifs[outcome3[0]-1]);
		gif2.setImage(gifs[outcome1[1]-1]);
		gif5.setImage(gifs[outcome2[1]-1]);
		gif8.setImage(gifs[outcome3[1]-1]);
		gif3.setImage(gifs[outcome1[2]-1]);
		gif6.setImage(gifs[outcome2[2]-1]);
		gif9.setImage(gifs[outcome3[2]-1]);
	}
	
	public void setSpins(ActionEvent e) {
		if(!isSetSpin) {
			rectangle.setTranslateY(-50);
			setSpinsElements(true);
			isSetSpin = true;
		}
		else {
			setSpinsElements(false);
			rectangle.setTranslateY(0);
			isSetSpin = false;
		}
	}
	
	private void setSpinsElements(Boolean bool) {
		setSpins1.setVisible(bool);
		setSpins2.setVisible(bool);
		setSpins3.setVisible(bool);
		setSpins4.setVisible(bool);
		setSpins5.setVisible(bool);
		setSpinsLabel.setVisible(bool);
		winLabel.setVisible(!bool);
	}
	
	public void setSpinsButton1(ActionEvent e) {
		autoSpins += 10;
		setAutoSpins();
	}
	public void setSpinsButton2(ActionEvent e) {
		autoSpins += 20;
		setAutoSpins();
	}
	public void setSpinsButton3(ActionEvent e) {
		autoSpins += 40;
		setAutoSpins();
	}
	public void setSpinsButton4(ActionEvent e) {
		autoSpins += 70;
		setAutoSpins();
	}
	public void setSpinsButton5(ActionEvent e) {
		autoSpins += 100;
		setAutoSpins();
	}
	
	private void setAutoSpins() {
		spinsLeftLabel.setVisible(true);
		rectangle.setTranslateY(0);
		setSpinsElements(false);
		isSetSpin = false;
		setButtonsDisable(true);
		spinsLeft = autoSpins;
		try {
			for(int i = 200; autoSpins != 0; i+=1000) {
				manualSpin(i);
				autoSpins--;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void setBet(ActionEvent e) {
		bet = bet + bet/2;
		bet = Math.round(bet);
		if(bet > 140)
			bet = 1;
		betButton.setText("Panos: " + (int)bet);
	}
	
	public void setTurbo(ActionEvent e) {
		isTurbo = !isTurbo;
		if(isTurbo)
			turboButton.setStyle("-fx-background-color:  #b363db");
		else
			turboButton.setStyle("-fx-background-color:  #693b80");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			init();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}
