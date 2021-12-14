package view;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ForTestingLaunchView extends Application {
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MonsterPayout");
        
        //Ohjelman iconin asettaminen
        this.primaryStage.getIcons().add(new Image("file:./src/main/resources/smallLogo.png"));
        
        /*
         * Avataan spins of madness
         */
        showSpinsOfMadness();
		
	}
	
	private void showSpinsOfMadness() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ForTestingLaunchView.class.getResource("SpinsOfMadnessView.fxml"));
            BorderPane view = (BorderPane) loader.load();
            Scene scene = new Scene(view);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
