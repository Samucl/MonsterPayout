package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MainApplication extends Application {
	
    private Stage primaryStage;
	
    /**
	* start metodissa kaikki ohjelman avautuessa suoritettavat toiminnot
	*/
	@Override
	public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MonsterPayout");
        showKirjautumisView();
        
        //showStoreView();


	}
    
	/**
	* Metodi jolla asetetaan kirjautumisView primaryStageen heti ohjelman avaessa.
	*/
    public void showKirjautumisView() {
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
    
    public void showStoreView() {
    	try {
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(MainApplication.class.getResource("StoreView.fxml"));
    		GridPane storeView = (GridPane) loader.load();
    		Scene scene = new Scene(storeView);
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