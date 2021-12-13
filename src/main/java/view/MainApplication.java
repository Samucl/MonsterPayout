package view;

import java.awt.Toolkit;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Database;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MainApplication extends Application {
	
    private Stage primaryStage;
	
    /**
	* start-metodissa kaikki ohjelman avautuessa suoritettavat toiminnot
	*/
	@Override
	public void start(Stage primaryStage) {
		
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MonsterPayout");
        
        //Ohjelman iconin asettaminen
        this.primaryStage.getIcons().add(new Image("file:./src/main/resources/smallLogo.png"));
        
        showLoginView();

	}
    
	/**
	* Metodi jolla asetetaan loginView primaryStageen heti ohjelman avautuessa
	*/
    public void showLoginView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("LoginView.fxml"));
            BorderPane loginView = (BorderPane) loader.load();
            Scene scene = new Scene(loginView);
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