package view;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class MonsterPayoutGUI extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			//******Kokeiluaa
			primaryStage.setTitle("MonsterPayout");
			Label kokeiluLabel = new Label("Kokeillaan toimiiko");
			Button kokeiluButton = new Button("Ei tee mit‰‰n");
			root.setCenter(kokeiluLabel);
			root.setBottom(kokeiluButton);
			//******Kokeiluaa
			
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
