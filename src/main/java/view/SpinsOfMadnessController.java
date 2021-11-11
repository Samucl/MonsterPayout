package view;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import slotgames.AbstractSlotgame1;
import slotgames.Slotgame1_Spins_of_Madness;

public class SpinsOfMadnessController implements Initializable {
	@FXML ImageView sym00;
	@FXML ImageView sym01;
	@FXML ImageView sym02;
	@FXML ImageView sym03;
	@FXML ImageView sym04;
	
	@FXML ImageView sym10;
	@FXML ImageView sym11;
	@FXML ImageView sym12;
	@FXML ImageView sym13;
	@FXML ImageView sym14;
	
	@FXML ImageView sym20;
	@FXML ImageView sym21;
	@FXML ImageView sym22;
	@FXML ImageView sym23;
	@FXML ImageView sym24;
	
	ImageView[] imageViews = null;
	
	@FXML Button spinButton;
	@FXML Text multiplier;
	
	private AbstractSlotgame1 game = new Slotgame1_Spins_of_Madness();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}
	
	private void init() {
		imageViews = new ImageView[] {
				sym00, sym01, sym02, sym03, sym04,
				sym10, sym11, sym12, sym13, sym14,
				sym20, sym21, sym22, sym23, sym24
		};
	}
	
	public void fireSpin(ActionEvent e) {
		Image[] symbols = game.spin();
		
		/*
		 * Pitäisi olla saman suuruiset
		 */
		if(symbols.length==imageViews.length) {
			System.out.println(symbols.length);
			for(int i = 0; i < symbols.length; i++)
				imageViews[i].setImage(symbols[i]);
		}
	}

}
