package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	
	public void purchaseOffer(ActionEvent e) {
		
	}
	
	public void purchaseProduct1(ActionEvent e) {
		
	}
	
	public void purchaseProduct2(ActionEvent e) {
		
	}
	
	
}
