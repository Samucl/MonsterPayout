package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import model.Product;
import model.Tietokanta;
import model.User;
 
public class StoreViewController {
	
	private ArrayList<Product> products;
	
	@FXML private BorderPane borderpane;
	@FXML private GridPane gridpane;
	@FXML private Button toMainBtn;
	@FXML private HBox hbox;
	@FXML private ScrollPane scrollpane;
	
	@FXML private Label nameLabel;
	@FXML private Label creditLabel;
	@FXML private Label coinLabel;
	
	public void initialize() {
		
		setProducts();
		refreshAccountInfo();
		
	}
	
	public void setProducts() {
		
		Product[] allProducts = Tietokanta.getProducts(); //Kaikki tietokannan tuotteet
		products = new ArrayList<Product>(); //Laitetaan tähän listaan ne, jotka tällä hetkellä myynnissä
		int i = 0;
		for (Product p : allProducts) {
			if (p.getForSaleStatus()) { //Jos tuote on myynnissä
				products.add(p);
			}
		}
		
		Collections.sort(products); //Laitetaan järjestykseen alennuskertoimen perusteella
		
		//Tehdään tuotteille oma GridPane, jonka sarakkeisiin asetetaan tuotetiedot, sitten GridPane asetetaan HBoxiin
		GridPane innerPane = new GridPane();
		innerPane.setVgap(6); //Rivien välille vähän tyhjää tilaa
		
		for (i = 0 ; i < products.size() ; i++) {
			
			innerPane.getColumnConstraints().add(new ColumnConstraints(180)); //Sarakkeen leveys
			
			Label nameLabel = new Label(products.get(i).getDescription());
			nameLabel.setFont(Font.font(16));
			innerPane.add(nameLabel, i, 0);
			
			Label creditLabel = new Label(Double.toString(products.get(i).getCreditAmount()) + " krediittiä");
			creditLabel.setFont(Font.font(18));
			innerPane.add(creditLabel, i, 1);
			
			if (products.get(i).getSaleMultiplier() < 0.99) { //Jos tuote on alennuksessa niin ilmoitetaan tässä vanha hinta
				String oldPriceStr = String.valueOf(products.get(i).getPrice()) + " €";
				Text oldPriceTxt = new Text(oldPriceStr);
				oldPriceTxt.setStrikethrough(true);
				innerPane.add(oldPriceTxt, i, 2);
			}
			
			double price = products.get(i).getPrice() * products.get(i).getSaleMultiplier();
			
			Label priceLabel = new Label(Double.toString(price) + " €");
			priceLabel.setFont(Font.font(18));
			innerPane.add(priceLabel, i, 3);
			
			Button buyBtn = new Button();
			buyBtn.setText("OSTA!");
			buyBtn.setFont(Font.font("system", FontWeight.BOLD, 18));
			innerPane.add(buyBtn, i, 4);
			
			if (products.get(i).getCoinAmount() != 0) { //Jos tuotteesta saa kolikkobonuksen niin laitetaan se ylimääräiseksi sarakkeeksi tähän väliin
				
				Label coinLabel = new Label("+ " + Integer.toString(products.get(i).getCoinAmount()) + " kolikkoa bonusta!");
				coinLabel.setFont(Font.font("system", FontPosture.ITALIC, 14));
				innerPane.add(coinLabel, i, 5);
			}
			if (products.get(i).getSaleMultiplier() < 0.99) { //Jos tuote on alennuksessa niin ilmoitetaan tässä alennusprosentti

				int discount = (int) (100 - (products.get(i).getSaleMultiplier() * 100)); //Lasketaan alennusprosentti hintakertoimesta
				Label discountLabel = new Label(discount + " % ALENNUS");
				discountLabel.setFont(Font.font("system", FontWeight.BOLD, 14));
				discountLabel.setTextFill(Color.RED);
				innerPane.add(discountLabel, i, 6);
			}
			
			Product p = products.get(i);		
			buyBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                	buyProduct(p);
                }
			});
			
		}
		
		hbox.getChildren().add(innerPane);
		scrollpane.setContent(innerPane); //Ilman tätä ikkunaa ei voi scrollata (jos tuotteita on paljon)
		

	}
	
	//Asetetaan sivun ylälaitaan käyttäjänimi ja saldot
	public void refreshAccountInfo() {
		nameLabel.setText(User.getUsername());
		creditLabel.setText("Krediitit: " + User.getCredits());
		coinLabel.setText("Kolikot: " + User.getCoins());
	}
	
	public void buyProduct(Product p) {
		Tietokanta.buyProduct(p);
		refreshAccountInfo();
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
