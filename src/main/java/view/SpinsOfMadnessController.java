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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Session;
import model.User;
import slotgames.AbstractSlotgame1;
import slotgames.Slotgame1_Spooky_Spins;

public class SpinsOfMadnessController implements Initializable {
	@FXML ImageView sym00,sym01,sym02,sym03,sym04;
	@FXML ImageView sym10,sym11,sym12,sym13,sym14;
	@FXML ImageView sym20,sym21,sym22,sym23,sym24;
	ImageView[] imageViews = null;
	@FXML Button spinButton,toMenu,betButton,turboButton;
	@FXML Text multiplier;
	@FXML Label winLabel;
	@FXML BorderPane rootPane;
	private Image spin1, spin2, spin3;
	ResourceBundle texts;
	int bet;
	
	private AbstractSlotgame1 game = new Slotgame1_Spooky_Spins();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			init();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void init() throws FileNotFoundException {
		texts = Session.getLanguageBundle();
		setLanguage();
		imageViews = new ImageView[] {
				sym00, sym01, sym02, sym03, sym04,
				sym10, sym11, sym12, sym13, sym14,
				sym20, sym21, sym22, sym23, sym24
		};
		spin1 = new Image(new FileInputStream("./src/main/resources/slot_icons/SpookySpins/spin1.gif"));
		spin2 = new Image(new FileInputStream("./src/main/resources/slot_icons/SpookySpins/spin2.gif"));
		spin3 = new Image(new FileInputStream("./src/main/resources/slot_icons/SpookySpins/spin3.gif"));
		setBackground();
	}
	
	private void setBackground() throws FileNotFoundException {
		rootPane = new BorderPane();
		Image image = new Image(new FileInputStream("./src/main/resources/slot_icons/SpookySpins/bg.jpg"));
		BackgroundImage myBI= new BackgroundImage(new Image(new FileInputStream("./src/main/resources/slot_icons/SpookySpins/bg.jpg"),0,1000,true,true),
		        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		rootPane.setBackground(new Background(myBI));
	}
	
	private void setLanguage() {
		betButton.setText(texts.getString("bet.button") + ": " + (int)bet);
		toMenu = new Button(texts.getString("exit.button"));
		turboButton.setText("Turbo");
		spinButton.setText(texts.getString("spin.button"));
	}
	
	public void fireSpin(ActionEvent e) throws FileNotFoundException {
		spinAnimation();
		try {
			int timeInMillis = 600;
			if(User.getCredits() < bet) {
				winLabel.setText(texts.getString("not.enough.credits"));
				return;
			}
		Timeline tl = new Timeline(new KeyFrame(Duration.millis(timeInMillis), ea -> {
				Image[] symbols = game.spin();
				showSymbols(symbols);
				disableButtons(false);
		}));
		tl.setCycleCount(1);
		tl.play();
		spinAnimation();
		disableButtons(true);
		winLabel.setText(texts.getString("good.luck") + "!");
		} catch (Exception e1) {}
	}
	
	private void showSymbols(Image[] symbols) {
		/*
		 * Pitäisi olla saman suuruiset
		 */
		if(symbols.length==imageViews.length) {
			System.out.println(symbols.length);
			for(int i = 0; i < symbols.length; i++)
				imageViews[i].setImage(symbols[i]);
		}
	}
	
	private void disableButtons(Boolean b) {
		spinButton.setDisable(b);
		toMenu.setDisable(b);
		betButton.setDisable(b);
		turboButton.setDisable(b);
	}
	
	private void spinAnimation() {
		for(int i = 0; i < 5; i++) {
			imageViews[i].setImage(spin1);
		}
		for(int i = 5; i < 10; i++) {
			imageViews[i].setImage(spin2);
		}
		for(int i = 10; i < 15; i++) {
			imageViews[i].setImage(spin3);
		}
	}
	
	public void setBet(ActionEvent e) {
		bet = bet + bet/2;
		bet = Math.round(bet);
		if(bet > 140)
			bet = 1;
		betButton.setText(texts.getString("bet.button") + ": " + ((int)bet));
	}
	
	public void toMenu(ActionEvent e) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("HomepageView.fxml"));
            BorderPane mainView = (BorderPane) loader.load();
            Scene mainScene = new Scene(mainView);
			Stage window = (Stage) spinButton.getScene().getWindow();
			window.setScene(mainScene);
			
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}

}
