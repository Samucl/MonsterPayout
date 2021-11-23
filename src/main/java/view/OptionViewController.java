package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import model.Session;

public class OptionViewController implements Initializable {
	
	@FXML
	public Label languageLabel;
	
	@FXML
	public ChoiceBox languageChoice;
	
	private ResourceBundle lang;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}
	
	private void init() {
		lang = Session.getLanguageBundle();
		
		
	}

}
