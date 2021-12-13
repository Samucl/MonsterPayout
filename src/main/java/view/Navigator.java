package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Database;
import moneyrain.MoneyRain;

/**
 * Tähän luokkaan on keskitetty navigointi eri ikkunoihin.
 *
 */

public class Navigator {
	
	public static void toMainView(Stage stage) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("HomepageView.fxml"));
            BorderPane mainView = (BorderPane) loader.load();
            Scene mainScene = new Scene(mainView);
			Stage window = stage;
			window.setTitle("MonsterPayout");
			window.setScene(mainScene);		
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public static void toArcadeBlackjack1(Stage stage) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("ArcadeBlackjack1View2.fxml"));
            BorderPane blackjackView = (BorderPane) loader.load();
            Scene blackjackScene = new Scene(blackjackView);
			Stage window = stage;
			window.setTitle("MPO - Arcade Blackjack");
			window.setScene(blackjackScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public static void toCasinoBlackjack1(Stage stage) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("CasinoBlackjack1View.fxml"));
            BorderPane blackjackView = (BorderPane) loader.load();
            Scene blackjackScene = new Scene(blackjackView);
			Stage window = stage;
			window.setTitle("MPO - Casino Blackjack");
			window.setScene(blackjackScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public static void toMoneyRain() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("MoneyRainMenuView.fxml"));
            AnchorPane moneyrainmenuView = (AnchorPane) loader.load();
            Scene moneyrainmenuScene = new Scene(moneyrainmenuView);
			Stage window = new Stage();
			window.setOnCloseRequest(evt -> {
				if(MoneyRain.getTl() != null) {
					MoneyRain.getTl().stop();//Pysäyttää pelin timelinen jos suljetaan ikkuna kesken pelin.
				}
			});
			window.setScene(moneyrainmenuScene);
			window.setTitle("MPO - Money Rain");
			window.setResizable(false);
			window.show();
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public static void toSlalomMadness() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("SlalomMadnessMenuView.fxml"));
            AnchorPane slalomMenuView = (AnchorPane) loader.load();
            Scene slalomMenuScene = new Scene(slalomMenuView);
			Stage window = new Stage();
			window.setScene(slalomMenuScene);
			window.setTitle("MPO - Slalom Madness");
			window.setResizable(false);
			window.show();
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public static void toFastPoker(Stage stage) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("Fast_pokerView.fxml"));
            BorderPane fastPokerView = (BorderPane) loader.load();
            Scene fastPokerScene = new Scene(fastPokerView);
			Stage window = stage;
			window.setTitle("MPO - Fast Poker");
			window.setScene(fastPokerScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public static void toLuckySpins(Stage stage) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("LuckySpinsView.fxml"));
            BorderPane luckySpinsView = (BorderPane) loader.load();
            Scene luckySpinsScene = new Scene(luckySpinsView);
			Stage window = stage;
			window.setTitle("MPO - Lucky Spins");
			window.setScene(luckySpinsScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public static void toSpookySpins(Stage stage) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("SpookySpinsView.fxml"));
            BorderPane spookySpinsView = (BorderPane) loader.load();
            Scene spookySpinsScene = new Scene(spookySpinsView);
			Stage window = stage;
			window.setTitle("MPO - Spooky Spins");
			window.setScene(spookySpinsScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public static void toUserInfo(Stage stage) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("UserInfoView.fxml"));
            BorderPane userInfoView = (BorderPane) loader.load();
            Scene userInfoScene = new Scene(userInfoView);
			Stage window = stage;
			window.setTitle("MPO - Profile");
			window.setScene(userInfoScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	} 
	
	public static void toStore(Stage stage) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("StoreView.fxml"));
            BorderPane storeView = (BorderPane) loader.load();
            Scene storeScene = new Scene(storeView);
			Stage window = stage;
			window.setTitle("MPO - Store");
			window.setScene(storeScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public static void toLogin(Stage stage) {	
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("LoginView.fxml"));
            BorderPane loginView = (BorderPane) loader.load();
            Scene loginScene = new Scene(loginView);
			Stage window = stage;
			window.setTitle("MPO - Sign In");
			window.setScene(loginScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public static void toSignUp(Stage stage) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("RegisterView.fxml"));
            BorderPane registerView = (BorderPane) loader.load();
            Scene registerScene = new Scene(registerView);
			Stage window = stage;
			window.setTitle("MPO - Sign Up");
			window.setScene(registerScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public static void toStoreManagement(Stage stage) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("StoreManagementView.fxml"));
            GridPane spView = (GridPane) loader.load();
            Scene spScene = new Scene(spView);
			Stage window = stage;
			window.setTitle("MPO - Store Management");
			window.setScene(spScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public static void logout(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("LoginView.fxml"));
            BorderPane loginView = (BorderPane) loader.load();
            Scene loginScene = new Scene(loginView);
			Stage window = stage;
			window.setTitle("MPO - Sign In");
			Database.logout();
			window.setScene(loginScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
}
