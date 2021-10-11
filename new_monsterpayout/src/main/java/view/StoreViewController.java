package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
	
	private ArrayList<Product> products1;
	private ArrayList<Product> products2;
	
	@FXML private BorderPane borderpane;
	@FXML private GridPane gridpane;
	@FXML private Button toMainBtn;
	@FXML private HBox hbox;
	@FXML private HBox hbox2;
	@FXML private ScrollPane scrollpane;
	@FXML private ScrollPane scrollpane2;
	
	@FXML private Label nameLabel;
	@FXML private Label creditLabel;
	@FXML private Label coinLabel;
	
	public void initialize() {
		
		setProducts();
		refreshAccountInfo();
		
	}
	
	public void setProducts() {
		
		Product[] allProducts = Tietokanta.getProducts(); //Kaikki tietokannan tuotteet
		products1 = new ArrayList<Product>(); //Laitetaan tähän listaan ne, jotka tällä hetkellä myynnissä ja alennuksessa
		products2 = new ArrayList<Product>(); //Ja tähän ne, jotka myynnissä ja joissa ei alennusta
		
		int i = 0;
		for (Product p : allProducts) {
			if (p.getForSaleStatus()) { //Jos tuote on myynnissä
				if (p.getSaleMultiplier() < 0.99) {
					products1.add(p);
				} else {
					products2.add(p);
				}
				
			} 
		}
		
		Collections.sort(products1); //Laitetaan järjestykseen alennuskertoimen perusteella
		
		//Tehdään alennustuotteille oma GridPane, jonka sarakkeisiin asetetaan tuotetiedot, sitten GridPane asetetaan HBoxiin
		GridPane innerPane = new GridPane();
		innerPane.setVgap(6); //Rivien välille vähän tyhjää tilaa
		innerPane.setStyle("-fx-border-style: solid inside;");
		innerPane.setPadding(new Insets(18, 0, 18, 24));
		
		for (i = 0 ; i < products1.size() ; i++) {
			
			innerPane.getColumnConstraints().add(new ColumnConstraints(200)); //Sarakkeen leveys
			
			int discount = (int) (100 - (products1.get(i).getSaleMultiplier() * 100)); //Lasketaan alennusprosentti hintakertoimesta
			Label discountLabel = new Label(discount + " % ALENNUS");
			discountLabel.setFont(Font.font("system", FontWeight.BOLD, 14));
			discountLabel.setTextFill(Color.RED);
			innerPane.add(discountLabel, i, 0);
			
			Label nameLabel = new Label(products1.get(i).getDescription());
			nameLabel.setFont(Font.font(16));
			innerPane.add(nameLabel, i, 1);
			
			Label creditLabel = new Label(Double.toString(products1.get(i).getCreditAmount()) + " krediittiä");
			creditLabel.setFont(Font.font(18));
			innerPane.add(creditLabel, i, 2);
			
			if (products1.get(i).getSaleMultiplier() < 0.99) { //Jos tuote on alennuksessa niin ilmoitetaan tässä vanha hinta
				String oldPriceStr = String.valueOf(products1.get(i).getPrice()) + " €";
				Text oldPriceTxt = new Text(oldPriceStr);
				oldPriceTxt.setStrikethrough(true);
				innerPane.add(oldPriceTxt, i, 3);
			}
			
			double price = products1.get(i).getPrice() * products1.get(i).getSaleMultiplier();
			
			Label priceLabel = new Label(Double.toString(price) + " €");
			priceLabel.setFont(Font.font(18));
			innerPane.add(priceLabel, i, 4);
			
			Button buyBtn = new Button();
			buyBtn.setText("OSTA!");
			buyBtn.setFont(Font.font("system", FontWeight.BOLD, 18));
			innerPane.add(buyBtn, i, 5);
			
			if (products1.get(i).getCoinAmount() != 0) { //Jos tuotteesta saa kolikkobonuksen niin laitetaan se ylimääräiseksi sarakkeeksi tähän väliin
				
				Label coinLabel = new Label("+" + Integer.toString(products1.get(i).getCoinAmount()) + " kolikkoa bonusta!");
				coinLabel.setFont(Font.font("system", FontPosture.ITALIC, 14));
				innerPane.add(coinLabel, i, 6);
			}
			
			Product p = products1.get(i);		
			buyBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                	buyProduct(p);
                }
			});
			
		}
		
		hbox.getChildren().add(innerPane);
		scrollpane.setContent(innerPane); //Ilman tätä ikkunaa ei voi scrollata (jos tuotteita on paljon)
		
		GridPane lowerPane = new GridPane();
		lowerPane.setVgap(6); //Rivien välille vähän tyhjää tilaa
		lowerPane.setStyle("-fx-border-style: solid inside;");
		lowerPane.setPadding(new Insets(18, 0, 18, 24));
		
		for (i = 0 ; i < products2.size() ; i++) {
			
			lowerPane.getColumnConstraints().add(new ColumnConstraints(200)); //Sarakkeen leveys
			
			Label nameLabel = new Label(products2.get(i).getDescription());
			nameLabel.setFont(Font.font(16));
			lowerPane.add(nameLabel, i, 0);
			
			Label creditLabel = new Label(Double.toString(products2.get(i).getCreditAmount()) + " krediittiä");
			creditLabel.setFont(Font.font(18));
			lowerPane.add(creditLabel, i, 1);
			
			double price = products2.get(i).getPrice();
			
			Label priceLabel = new Label(Double.toString(price) + " €");
			priceLabel.setFont(Font.font(18));
			lowerPane.add(priceLabel, i, 2);
			
			Button buyBtn = new Button();
			buyBtn.setText("OSTA!");
			buyBtn.setFont(Font.font("system", FontWeight.BOLD, 18));
			lowerPane.add(buyBtn, i, 3);
			
			if (products2.get(i).getCoinAmount() != 0) { //Jos tuotteesta saa kolikkobonuksen niin laitetaan se ylimääräiseksi sarakkeeksi tähän väliin
				
				Label coinLabel = new Label("+" + Integer.toString(products2.get(i).getCoinAmount()) + " kolikkoa bonusta!");
				coinLabel.setFont(Font.font("system", FontPosture.ITALIC, 14));
				lowerPane.add(coinLabel, i, 4);
			}
			
			Product p = products2.get(i);		
			buyBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                	buyProduct(p);
                }
			});
			
		}
		
		hbox2.getChildren().add(lowerPane);
		scrollpane2.setContent(lowerPane);
		

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
