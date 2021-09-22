package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Product;
import model.Tietokanta;

public class StoreViewController {
	
	private Product[] products;
	
	@FXML private Button purchaseOfferBtn;
	@FXML private Button purchaseBtn1;
	@FXML private Button purchaseBtn2;
	@FXML private Button toMainBtn;
	@FXML private TextArea offerTF;
	@FXML private TextArea productTF1;
	@FXML private TextArea productTF2;
	
	@FXML
	public void initialize() {
		products = Tietokanta.getProducts();
		System.out.println(products);
		
		showProducts();
	}
	
	//Asettaa tuotteiden tiedot TextFieldeihin
	
	@FXML
	public void showProducts() {
		offerTF.setText("\n\nTähän joku \nsuperhyvä tarjous");
		
		productTF1.setText("\n\n" + String.valueOf(products[0].getCreditAmount()) + " krediittiä\n\n"
				+  "Hinta: " + String.valueOf(products[0].getPrice()) + " €!");
		
		productTF2.setText("\n\n" + String.valueOf(products[1].getCreditAmount()) + " krediittiä\n\n"
				+  "Hinta: " + String.valueOf(products[1].getPrice()) + " €!");
		
	}
	
	public void toHomepage(ActionEvent e) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("HomepageView.fxml"));
            AnchorPane homepageView = (AnchorPane) loader.load();
            Scene homepageScene = new Scene(homepageView);
			Stage window = (Stage) toMainBtn.getScene().getWindow();
			window.setScene(homepageScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void purchaseOffer(ActionEvent e) {
		
	}
	
	public void purchaseProduct1(ActionEvent e) {
		double amount = products[0].getCreditAmount();
		int updatedRows = Tietokanta.increaseCreditBalance(amount);
		System.out.println(updatedRows + " rows updated.");
	}
	
	public void purchaseProduct2(ActionEvent e) {
		double amount = products[1].getCreditAmount();
		int updatedRows = Tietokanta.increaseCreditBalance(amount);
		System.out.println(updatedRows + " rows updated.");
	}
	
	public void toMainView(ActionEvent e) {
		
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("HomepageView.fxml"));
            AnchorPane mainView = (AnchorPane) loader.load();
            Scene mainScene = new Scene(mainView);
			Stage window = (Stage) toMainBtn.getScene().getWindow();
			window.setScene(mainScene);
			
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}

	
	
}
