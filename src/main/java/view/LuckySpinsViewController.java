package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Database;
import model.ICreditGame;
import model.Session;
import model.User;
import slotgames.SlotMachine;

/**
 * Luokka toimii LuckySpins slottipelin kontrollerina
 * @author Samuel Laisaar
 * @version 12.12.2021
 */
public class LuckySpinsViewController implements Initializable, ICreditGame{

	private static final String[] icons = new String[] {"strawberry", "spade", "clover", "diamond", "seven", "wild"};
	@FXML Button toMenu;
	@FXML ImageView gif1, gif2, gif3, gif4, gif5, gif6, gif7, gif8, gif9;
	@FXML Button spinButton;
	@FXML Label winLabel;
	@FXML ToggleButton payline1, payline2, payline3;
	@FXML Rectangle rectangle1, rectangle2, rectangle3;
	@FXML Label balanceLabel;
	@FXML Rectangle rectangle;
	@FXML Label setSpinsLabel;
	@FXML Button setSpinsButton;
	@FXML Button setSpins1, setSpins2, setSpins3, setSpins4, setSpins5;
	@FXML Button betButton;
	@FXML Button turboButton;
	@FXML Label spinsLeftLabel, paylinesLabel;
	private Image spin1, spin2, spin3;
	private Image[] gifs = new Image[6];
	private boolean isSetSpin = false;
	private boolean isTurbo = false;
	private int autoSpins = 0;
	private int spinsLeft = 0;
	private int winning = 0;
	private double bet = 1;
	private SlotMachine game1 = new SlotMachine();
	private SlotMachine game2 = new SlotMachine();
	private SlotMachine game3 = new SlotMachine();
	private int paylinesSelected = 1;
	private ResourceBundle texts;
	private NumberFormat numberFormat;
	private String path = "slot_icons/";

	private void init() throws FileNotFoundException {
		numberFormat = Session.getNumberFormatter();
		setLanguage();
		payline1.setSelected(false);
		payline2.setSelected(true);
		payline3.setSelected(false);
		spin1 = new Image(Session.getFile(path+"spin1.gif"));
		spin2 = new Image(Session.getFile(path+"spin2.gif"));
		spin3 = new Image(Session.getFile(path+"spin3.gif"));
	}

	private void setLanguage() {
		texts = Session.getLanguageBundle();
		balanceLabel.setText(texts.getString("credits") + ": " + numberFormat.format(User.getCredits()));
		betButton.setText(texts.getString("bet.button") + ": " + (int)bet);
		paylinesLabel.setText(texts.getString("choose.paylines"));
		toMenu.setText(texts.getString("exit.button"));
		turboButton.setText("Turbo");
		setSpinsButton.setText(texts.getString("set.spins.button"));
		setSpinsLabel.setText(texts.getString("set.spins.button"));
		spinButton.setText(texts.getString("spin.button"));
		payline1.setText(texts.getString("payline") + " 1");
		payline2.setText(texts.getString("payline") + " 2");
		payline3.setText(texts.getString("payline") + " 3");
	}

	public void toMainView(ActionEvent e) {
		autoSpins = 0;
        Stage window = (Stage) toMenu.getScene().getWindow();
		Navigator.toMainView(window);
	}

	/**
	 * Aloittaa slottipelin ja pyörittää rullia
	 * @throws FileNotFoundException
	 */
	public void spin(ActionEvent e) throws FileNotFoundException{
		if(User.getCredits() < bet) {
			winLabel.setText(texts.getString("not.enough.credits"));
			return;
		}
		setButtonsDisable(true);
			if(isTurbo)
				manualSpin(100);
			else
				manualSpin(1000);
	}

	/**
	 * Metodi mahdollistaa manuaalisen pyöräytyksen esimerkiksi, jos asettaa pelin pyörittämään peliä istenäisesti
	 * @param timeInMillis
	 * @throws FileNotFoundException
	 */
	private void manualSpin(int timeInMillis) throws FileNotFoundException{
		if(User.getCredits() < bet) {
			winLabel.setText(texts.getString("not.enough.credits"));
			return;
		}

		Timeline tl = new Timeline(new KeyFrame(Duration.millis(timeInMillis), ea -> {
				try {
					game1.play();
					game2.play();
					game3.play();
					useCredits(bet * paylinesSelected);
					balanceLabel.setText(texts.getString("credits") + ": " + numberFormat.format(User.getCredits()));
					winning = 0;
					if(game1.checkWin() && payline1.isSelected())
						winning += game1.payout();
					if(game2.checkWin() && payline2.isSelected())
						winning += game2.payout();
					if(game3.checkWin() && payline3.isSelected())
						winning += game3.payout();
					if(isWin(game1,game2,game3))
						addCreditBalance(winning * bet);
					showIcons(game1.getOutcome(),game2.getOutcome(),game3.getOutcome());
					if(spinsLeft>1) {
						spinsLeftLabel.setText(texts.getString("spins") + ": " + (--spinsLeft));
					}
					else {
						setButtonsDisable(false);
						spinsLeftLabel.setVisible(false);
					}
				} catch (FileNotFoundException e1) {}
				if(isWin(game1,game2,game3)) {
					winLabel.setText(texts.getString("you.won") + " " + numberFormat.format((winning*(int)bet)) + " " + texts.getString("coins.partitive").toLowerCase());
					balanceLabel.setText(texts.getString("credits") + ": " + numberFormat.format(User.getCredits()));
				}
				else
					winLabel.setText(texts.getString("no.win"));
		}));
		tl.setCycleCount(1);
		tl.play();
		spinAnimation();
		winLabel.setText(texts.getString("good.luck") + "!");
	}

	private void setButtonsDisable(Boolean bool) {
		spinButton.setDisable(bool);
		setSpinsButton.setDisable(bool);
		betButton.setDisable(bool);
		turboButton.setDisable(bool);
	}

	/**
	 * Tarkistetaan voitto jokaisesta pyöritetystä pelistä
	 * @param game1
	 * @param game2
	 * @param game3
	 * @return
	 */
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
	public void showPaylineRect1() {
		rectangle1.setVisible(true);
		payline1.setOnMouseExited(m->{rectangle1.setVisible(false);});
	}
	public void showPaylineRect2() {
		rectangle2.setVisible(true);
		payline2.setOnMouseExited(m->{rectangle2.setVisible(false);});
	}
	public void showPaylineRect3() {
		rectangle3.setVisible(true);
		payline3.setOnMouseExited(m->{rectangle3.setVisible(false);});
	}

	/**
	 * Metodi mahdollistaa voittorivien tyylien vaihtelun
	 * @param payline
	 */
	private void paylineColor(ToggleButton payline) {
		if(payline.isSelected()) {
			payline.setStyle("-fx-background-color: linear-gradient(to right,#9960ba, #4a8bbf)");
			payline.setOpacity(1);
			paylinesSelected++;
		}
		else {
			payline.setStyle("-fx-background-color: linear-gradient(to right,#734090, #2c6491)");
			payline.setOpacity(0.2);
			paylinesSelected--;
		}
		betButton.setText(texts.getString("bet.button") + ": " + ((int)bet*paylinesSelected));
		if(paylinesSelected == 0) {
			setButtonsDisable(true);
			winLabel.setText(texts.getString("choose.paylines") + "!");
		}
		else {
			setButtonsDisable(false);
			winLabel.setText("");
		}
	}

	/**
	 * Asettaa jokaiseen rullaan pyörimisanimaation.
	 * @throws FileNotFoundException
	 */
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

	/**
	 * Pysäyttää pyörivät rullat ja asettaa iconit paikoilleen.
	 * @param outcome1
	 * @param outcome2
	 * @param outcome3
	 * @throws FileNotFoundException
	 */
	private void showIcons(int[] outcome1, int[] outcome2 ,int[] outcome3) throws FileNotFoundException {
		gifs[0] = new Image(Session.getFile(path+ icons[0] + ".gif"));
		gifs[1] = new Image(Session.getFile(path+ icons[1] + ".gif"));
		gifs[2] = new Image(Session.getFile(path+ icons[2] + ".gif"));
		gifs[3] = new Image(Session.getFile(path+ icons[3] + ".gif"));
		gifs[4] = new Image(Session.getFile(path+ icons[4] + ".gif"));
		gifs[5] = new Image(Session.getFile(path+ icons[5] + ".gif"));
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

	/**
	 * Metodi mahdollistaa autospin toiminnon.
	 * (Peli pyörii itsenäisesti tietyn määrän).
	 */
	private void setAutoSpins() {
		spinsLeftLabel.setVisible(true);
		rectangle.setTranslateY(0);
		setSpinsElements(false);
		isSetSpin = false;
		setButtonsDisable(true);
		spinsLeft = autoSpins;
		try {
			int timeInMillis = 600;
			if(User.getCredits() < bet) {
				winLabel.setText(texts.getString("not.enough.credits"));
				autoSpins = 0;
				setButtonsDisable(false);
				spinsLeftLabel.setVisible(false);
				return;
			}
			Timeline tl = new Timeline(new KeyFrame(Duration.millis(timeInMillis), ea -> {
					try {
						game1.play();
						game2.play();
						game3.play();
						useCredits(bet * paylinesSelected);
						balanceLabel.setText(texts.getString("credits") + ": " + numberFormat.format(User.getCredits()));
						winning = 0;
						spinAnimation();
						winLabel.setText(texts.getString("good.luck") + "!");
						if(game1.checkWin() && payline1.isSelected())
							winning += game1.payout();
						if(game2.checkWin() && payline2.isSelected())
							winning += game2.payout();
						if(game3.checkWin() && payline3.isSelected())
							winning += game3.payout();
						if(isWin(game1,game2,game3))
							addCreditBalance(winning * bet);
						showIcons(game1.getOutcome(),game2.getOutcome(),game3.getOutcome());
						if(spinsLeft>1) {
							spinsLeftLabel.setText(texts.getString("spins") + ": " + (--spinsLeft));
						}
						else {
							setButtonsDisable(false);
							spinsLeftLabel.setVisible(false);
						}
						autoSpins--;
						if(autoSpins>0)
							setAutoSpins();
					} catch (FileNotFoundException e1) {}
					if(isWin(game1,game2,game3)) {
						winLabel.setText(texts.getString("you.won") + " " + numberFormat.format((winning*(int)bet)) + " " + texts.getString("coins.partitive").toLowerCase());
						balanceLabel.setText(texts.getString("credits") + ": " + numberFormat.format(User.getCredits()));
					}
					else
						winLabel.setText(texts.getString("no.win"));
			}));
			tl.setCycleCount(1);
			tl.play();
		} catch (Exception e1) {}
	}

	public void setBet(ActionEvent e) {
		bet = bet + bet/2;
		bet = Math.round(bet);
		if(bet > 140)
			bet = 1;
		betButton.setText(texts.getString("bet.button") + ": " + ((int)bet*paylinesSelected));
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

	@Override
	public double useCredits(double amount) {
		return Database.decreaseCreditBalance(amount);
	}

	@Override
	public boolean addCreditBalance(double amount) {
		if(Database.increaseCreditBalance(amount)>0)
			return true;
		return false;
	}

}