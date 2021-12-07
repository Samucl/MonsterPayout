package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import model.Product;
import model.Session;
import model.Database;
import model.User;
 
public class StoreViewController {
	
	private ArrayList<Product> products1;
	private ArrayList<Product> products2;
	private int bgCardWidth = 140;
	private Image bgCard;
	private Image credit;
	private Image coin;
	
	@FXML private BorderPane borderpane;
	@FXML private GridPane gridpane;
	@FXML private Button toMainBtn;
	@FXML private Button toUserInfoBtn;
	@FXML private Button logoutBtn;
	
	@FXML private StackPane stackpane;
	@FXML private HBox hbox;
	@FXML private HBox hbox2;
	@FXML private ScrollPane scrollpane;
	@FXML private ScrollPane scrollpane2;
	
	@FXML private Label nameLabel;
	@FXML private Label creditLabel;
	@FXML private Label coinLabel;
	
	@FXML private ChoiceBox<Double> buyWithCoinsChoiceBox;
	@FXML private Label buyWithCoinsLabel;
	@FXML private Label chooseAmountLabel;
	@FXML private Label buyWithCoinsPriceLabel;
	@FXML private Button buyWithCoinsBtn;
	@FXML private Label buyWithCoinsSuccessLabel;
	@FXML private ImageView coinImage;
	

	private ResourceBundle texts = Session.getLanguageBundle();
	
	
	public void initialize() {
		try {
			bgCard = new Image(new FileInputStream("./src/main/resources/card_deck_1/taka.png"));
			credit = new Image(new FileInputStream("./src/main/resources/credit_1.png"));
			coin = new Image(new FileInputStream("./src/main/resources/coin_1.png"));	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		useLanguageBundle();
		setProducts();
		setDiscountProducts();
		setChoiceBoxItems();
		refreshAccountInfo();
		
	}
	
	private void useLanguageBundle() {
		logoutBtn.setText(texts.getString("logout.button"));
		toMainBtn.setText(texts.getString("store"));
		toUserInfoBtn.setText(texts.getString("user.details"));
		
		buyWithCoinsLabel.setText(texts.getString("buy.with.coins"));
		chooseAmountLabel.setText(texts.getString("choose.credit.amount"));
		buyWithCoinsBtn.setText(texts.getString("buy.button"));
	}
	
	public void setProducts() {
		
		Product[] allProducts = Database.getProducts(); //Kaikki tietokannan tuotteet
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
		
		/*
		for (i = 0 ; i < products1.size() ; i++) {
			ImageView bgCardIV = new ImageView(bgCard);
			stackpane.getChildren().add(bgCardIV);
			bgCardIV.setTranslateX(i * 200);
		} */
		
		//Tehdään alennustuotteille oma GridPane, jonka sarakkeisiin asetetaan tuotetiedot, sitten GridPane asetetaan HBoxiin
		GridPane innerPane = new GridPane();
		innerPane.setVgap(5); //Rivien välille vähän tilaa
		innerPane.setPadding(new Insets(18, 0, 18, 24));
		
		
		for (i = 0 ; i < products1.size() ; i++) {
			
			innerPane.getColumnConstraints().add(new ColumnConstraints(200)); //Sarakkeen leveys
			
			int discount = (int) (100 - (products1.get(i).getSaleMultiplier() * 100)); //Lasketaan alennusprosentti hintakertoimesta
			Label discountLabel = new Label(texts.getString("discount") + discount + " %");
			discountLabel.setFont(Font.font("system", FontWeight.BOLD, 14));
			discountLabel.setTextFill(Color.RED);
			innerPane.add(discountLabel, i, 0);
			
			Label nameLabel = new Label(products1.get(i).getDescription());
			nameLabel.setFont(Font.font(16));
			innerPane.add(nameLabel, i, 1);
			
			Label creditLabel = new Label(Double.toString(products1.get(i).getCreditAmount()));
			creditLabel.setFont(Font.font(18));
			innerPane.add(creditLabel, i, 2);

		    ImageView imageView = new ImageView(credit);
		    imageView.setFitWidth(35);
		    imageView.setTranslateX(Double.toString(products1.get(i).getCreditAmount()).length() * 9); //Asetetaan kuva merkkijonon pituuden mukaan, jotta tulee sopivasti jonon "jatkeeksi"
		    imageView.setPreserveRatio(true);
		    innerPane.add(imageView, i, 2);
	        
			
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
			buyBtn.setText(texts.getString("buy.button"));
			buyBtn.setFont(Font.font("system", FontWeight.BOLD, 18));
			innerPane.add(buyBtn, i, 5);
			
			if (products1.get(i).getCoinAmount() != 0) { //Jos tuotteesta saa kolikkobonuksen niin laitetaan se ylimääräiseksi sarakkeeksi
				
					Label coinLabel = new Label(texts.getString("coin.bonus") + Integer.toString(products1.get(i).getCoinAmount()));
					coinLabel.setFont(Font.font("system", FontPosture.ITALIC, 14));
					innerPane.add(coinLabel, i, 6);
			        ImageView imageView2 = new ImageView(coin);
			        imageView2.setFitWidth(26);
			        imageView2.setTranslateX(coinLabel.getText().length() * 7.2); //Asetetaan kuva merkkijonon pituuden mukaan, jotta tulee sopivasti jonon "jatkeeksi"
			        imageView2.setPreserveRatio(true);
			        innerPane.add(imageView2, i, 6);
			}
			
			Product p = products1.get(i);		
			buyBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                	buyProduct(p);
                }
			});
		}
		
		scrollpane.setContent(innerPane);
	}
	
	//-------------------------------------
	
	public void setDiscountProducts() {
		
		GridPane lowerPane = new GridPane();
		lowerPane.setVgap(5); //Rivien välille vähän tilaa
		lowerPane.setPadding(new Insets(0, 0, 18, 24));
		
		for (int i = 0 ; i < products2.size() ; i++) {
			
			lowerPane.getColumnConstraints().add(new ColumnConstraints(200)); //Sarakkeen leveys
			
			Label nameLabel = new Label(products2.get(i).getDescription());
			nameLabel.setFont(Font.font(16));
			lowerPane.add(nameLabel, i, 0);
			
			Label creditLabel = new Label(Double.toString(products2.get(i).getCreditAmount()));
			creditLabel.setFont(Font.font(18));
			lowerPane.add(creditLabel, i, 1);
			
			FileInputStream input;
			try {
				input = new FileInputStream("./src/main/resources/credit_1.png");
				Image image = new Image(input);
		        ImageView imageView = new ImageView(image);
		        imageView.setFitWidth(35);
		        imageView.setTranslateX(Double.toString(products2.get(i).getCreditAmount()).length() * 9); //Asetetaan kuva merkkijonon pituuden mukaan, jotta tulee sopivasti jonon "jatkeeksi"
		        imageView.setPreserveRatio(true);
		        lowerPane.add(imageView, i, 1);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			double price = products2.get(i).getPrice();
			
			Label priceLabel = new Label(Double.toString(price) + " €");
			priceLabel.setFont(Font.font(18));
			lowerPane.add(priceLabel, i, 2);
			
			Button buyBtn = new Button();
			buyBtn.setText(texts.getString("buy.button"));
			buyBtn.setFont(Font.font("system", FontWeight.BOLD, 18));
			lowerPane.add(buyBtn, i, 3);
			
			if (products2.get(i).getCoinAmount() != 0) { //Jos tuotteesta saa kolikkobonuksen niin laitetaan se ylimääräiseksi sarakkeeksi tähän
				
				FileInputStream input2;
				try {
					Label coinLabel = new Label(texts.getString("coin.bonus") + Integer.toString(products2.get(i).getCoinAmount()));
					coinLabel.setFont(Font.font("system", FontPosture.ITALIC, 14));
					lowerPane.add(coinLabel, i, 4);
					input2 = new FileInputStream("./src/main/resources/coin_1.png");
					Image image = new Image(input2);
			        ImageView imageView = new ImageView(image);
			        imageView.setFitWidth(26);
			        imageView.setTranslateX(coinLabel.getText().length() * 7.2); //Asetetaan kuva merkkijonon pituuden mukaan, jotta tulee sopivasti jonon "jatkeeksi"
			        imageView.setPreserveRatio(true);
			        lowerPane.add(imageView, i, 4);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
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
	
	//-------------------------------------

	public void setChoiceBoxItems() {
		buyWithCoinsChoiceBox.getItems().add(0.1);
		buyWithCoinsChoiceBox.getItems().add((double) 1);
		buyWithCoinsChoiceBox.getItems().add((double) 10);
		buyWithCoinsChoiceBox.getItems().add((double) 100);
		
		buyWithCoinsChoiceBox.setOnAction((e) -> {	
			double creditAmount = buyWithCoinsChoiceBox.getSelectionModel().getSelectedItem();
		    int coinAmount = (int) (1000 * creditAmount);
			
			buyWithCoinsPriceLabel.setText(texts.getString("price") + String.valueOf(coinAmount));
			buyWithCoinsPriceLabel.setVisible(true);
			buyWithCoinsBtn.setVisible(true);
			coinImage.setVisible(true);
			
			//Ostotapahtuma kolikoilla
			buyWithCoinsBtn.setOnAction((e2) -> {
			    if (Database.decreaseCoinBalance(coinAmount) != 0) {
			    	Database.increaseCreditBalance(creditAmount);
			    	buyWithCoinsSuccessLabel.setText(texts.getString("success"));
			    	refreshAccountInfo();
			    } else {
			    	buyWithCoinsSuccessLabel.setText(texts.getString("not.enough.coins"));
			    }
			}); 
		});
	}
	
	//Asetetaan sivun ylälaitaan käyttäjänimi ja saldot
	public void refreshAccountInfo() {
		nameLabel.setText(User.getUsername());
		creditLabel.setText(String.valueOf(User.getCredits()));
		coinLabel.setText(String.valueOf(User.getCoins()));
	}
	
	public void buyProduct(Product p) {
		Database.buyProduct(p);
		refreshAccountInfo();
	}
	
	public void toUserInfoView(ActionEvent e) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("UserInfoView.fxml"));
            BorderPane userInfoView = (BorderPane) loader.load();
            Scene userInfoScene = new Scene(userInfoView);
			Stage window = (Stage) toUserInfoBtn.getScene().getWindow();
			window.setScene(userInfoScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	} 
	
	public void toMainView(ActionEvent e) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("HomepageView.fxml"));
            BorderPane mainView = (BorderPane) loader.load();
            Scene mainScene = new Scene(mainView);
			Stage window = (Stage) toMainBtn.getScene().getWindow();
			window.setScene(mainScene);
			
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void logOut(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("LoginView.fxml"));
            BorderPane loginView = (BorderPane) loader.load();
            Scene loginScene = new Scene(loginView);
			Stage window = (Stage) logoutBtn.getScene().getWindow();
			Database.logout();
			window.setScene(loginScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	
	
}
