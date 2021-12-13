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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import model.Product;
import model.Session;
import model.Database;
import model.User;
 
/*
 * Kauppanäkymän kontrolleri.
 * 
 * @author Jukka Hallikainen
 */
public class StoreViewController {
	
	private ArrayList<Product> discountProducts;
	private ArrayList<Product> noDiscountProducts;
	private Image credit;
	private Image coin;
	
	@FXML private BorderPane borderpane;
	@FXML private GridPane gridpane;
	@FXML private Button toHomeBtn;
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
			credit = new Image(new FileInputStream("./src/main/resources/credit_1.png"));
			coin = new Image(new FileInputStream("./src/main/resources/coin_1.png"));	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		useLanguageBundle();
		createProductArrays();
		setDiscountProducts();
		setNoDiscountProducts();
		setChoiceBoxItems();
		refreshAccountInfo();	
	}

	private void useLanguageBundle() {
		logoutBtn.setText(texts.getString("logout.button"));
		toHomeBtn.setText(texts.getString("homepage"));
		toUserInfoBtn.setText(texts.getString("user.details"));	
		buyWithCoinsLabel.setText(texts.getString("buy.with.coins"));
		chooseAmountLabel.setText(texts.getString("choose.credit.amount"));
		buyWithCoinsBtn.setText(texts.getString("buy.button"));
	}
	
	/**
	 * Luo grid panen, jonka sarakkeisiin asetetaan alennustuotteiden tiedot.
	 * Horisontaalisia sarakkeita luodaan niin monta kuin myynnissä olevia alennustuotteita löytyy tietokannasta.
	 */
	public void setDiscountProducts() {
		
		GridPane productPane1 = new GridPane();
		productPane1.setVgap(5);
		productPane1.setPadding(new Insets(18, 0, 18, 24));	
		
		for (int i = 0 ; i < discountProducts.size() ; i++) {
			
			//Sarakkeen leveys
			productPane1.getColumnConstraints().add(new ColumnConstraints(200)); 
			
			 //Lasketaan alennusprosentti hintakertoimesta
			int discount = (int) (100 - (discountProducts.get(i).getSaleMultiplier() * 100));
			
			Label discountLabel = new Label(texts.getString("discount") + discount + " %");
			discountLabel.setFont(Font.font("Arial Black", FontWeight.BOLD, 14));
			discountLabel.setTextFill(Color.RED);
			productPane1.add(discountLabel, i, 0);
			
			Label nameLabel = new Label(discountProducts.get(i).getDescription());
			nameLabel.setFont(Font.font("Arial Black", 16));
			productPane1.add(nameLabel, i, 1);
			
			Label creditLabel = new Label(Double.toString(discountProducts.get(i).getCreditAmount()));
			creditLabel.setFont(Font.font("Arial Black", 15));
			productPane1.add(creditLabel, i, 2);

		    ImageView imageView = new ImageView(credit);
		    imageView.setFitWidth(35);
		    //Asetetaan krediitti-kuva merkkijonon pituuden mukaan, jotta tulee sopivasti jonon "jatkeeksi"
		    imageView.setTranslateX(Double.toString(discountProducts.get(i).getCreditAmount()).length() * 9); 
		    imageView.setPreserveRatio(true);
		    productPane1.add(imageView, i, 2);
	        
			
			if (discountProducts.get(i).getSaleMultiplier() < 0.99) {
				String oldPriceStr = String.valueOf(discountProducts.get(i).getPrice()) + " €";
				Text oldPriceTxt = new Text(oldPriceStr);
				oldPriceTxt.setStrikethrough(true);
				oldPriceTxt.setFill(Color.WHITE);
				oldPriceTxt.setFont(Font.font(16));
				productPane1.add(oldPriceTxt, i, 3);
			}
			
			double price = discountProducts.get(i).getPrice() * discountProducts.get(i).getSaleMultiplier();
			
			Label priceLabel = new Label(Double.toString(price) + " €");
			priceLabel.setFont(Font.font("Arial Black", 16));
			productPane1.add(priceLabel, i, 4);
			
			Button buyBtn = new Button();
			buyBtn.setText(texts.getString("buy.button"));
			buyBtn.setFont(Font.font("Arial Black", FontWeight.BOLD, 18));
			productPane1.add(buyBtn, i, 5);
			
			if (discountProducts.get(i).getCoinAmount() != 0) {
				
				Label coinLabel = new Label(texts.getString("coin.bonus") + Integer.toString(discountProducts.get(i).getCoinAmount()));
				coinLabel.setFont(Font.font("System", FontPosture.ITALIC, 15));
				productPane1.add(coinLabel, i, 6);
			    ImageView imageView2 = new ImageView(coin);
			    imageView2.setFitWidth(26);
			        
			    //Asetetaan kuva merkkijonon pituuden mukaan, jotta tulee sopivasti jonon "jatkeeksi"
			    imageView2.setTranslateX(coinLabel.getText().length() * 7.2); 
			    imageView2.setPreserveRatio(true);
			    productPane1.add(imageView2, i, 6);
			}
			
			Product p = discountProducts.get(i);		
			buyBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                	buyProduct(p);
                }
			});
		}
		
		hbox.getChildren().add(productPane1);
		scrollpane.setContent(productPane1);
	}
	
	/**
	 * Luo grid panen, jonka sarakkeisiin asetetaan normaalihintaisten tuotteiden tiedot.
	 * Horisontaalisia sarakkeita luodaan niin monta kuin myynnissä olevia normaalihintaisia tuotteita löytyy tietokannasta.
	 */
	public void setNoDiscountProducts() {
		
		GridPane productPane2 = new GridPane();
		productPane2.setVgap(5);
		productPane2.setPadding(new Insets(0, 0, 18, 24));
		
		for (int i = 0 ; i < noDiscountProducts.size() ; i++) {
			
			productPane2.getColumnConstraints().add(new ColumnConstraints(200));
			
			Label nameLabel = new Label(noDiscountProducts.get(i).getDescription());
			nameLabel.setFont(Font.font("Arial Black", 16));
			productPane2.add(nameLabel, i, 0);
			
			Label creditLabel = new Label(Double.toString(noDiscountProducts.get(i).getCreditAmount()));
			creditLabel.setFont(Font.font("Arial Black", 15));
			productPane2.add(creditLabel, i, 1);
			
		    ImageView imageView = new ImageView(credit);
		    imageView.setFitWidth(35);
		    //Asetetaan kuva merkkijonon pituuden mukaan, jotta tulee sopivasti jonon "jatkeeksi"
		    imageView.setTranslateX(Double.toString(noDiscountProducts.get(i).getCreditAmount()).length() * 9); 
		    imageView.setPreserveRatio(true);
		    productPane2.add(imageView, i, 1);
			
			double price = noDiscountProducts.get(i).getPrice();
			
			Label priceLabel = new Label(Double.toString(price) + " €");
			priceLabel.setFont(Font.font("Arial Black", 16));
			productPane2.add(priceLabel, i, 2);
			
			Button buyBtn = new Button();
			buyBtn.setText(texts.getString("buy.button"));
			buyBtn.setFont(Font.font("Arial Black", FontWeight.BOLD, 18));
			productPane2.add(buyBtn, i, 3);
			
			if (noDiscountProducts.get(i).getCoinAmount() != 0) { 
				
				Label coinLabel = new Label(texts.getString("coin.bonus") + Integer.toString(noDiscountProducts.get(i).getCoinAmount()));
				coinLabel.setFont(Font.font("System", FontPosture.ITALIC, 15));
				productPane2.add(coinLabel, i, 4);
			    ImageView coinIV = new ImageView(coin);
			    coinIV.setFitWidth(26);
			    //Asetetaan kuva merkkijonon pituuden mukaan, jotta tulee sopivasti jonon "jatkeeksi"
			    coinIV.setTranslateX(coinLabel.getText().length() * 7.2); 
			    coinIV.setPreserveRatio(true);
			    productPane2.add(coinIV, i, 4);
				
			}
			
			Product p = noDiscountProducts.get(i);		
			buyBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                	buyProduct(p);
                }
			});
			
		}
		
		hbox2.getChildren().add(productPane2);
		scrollpane2.setContent(productPane2);
		
	}
	
	/**
	 * Asettaa choice boxiin neljä krediittiarvoa, joita käyttäjä pystyy kolikoilla ostamaan.
	 */	
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
	
	/**
	 * Luo kaksi listaa, joista ensimmäiseen asettaa alennustuotteet ja toiseen normaalihintaiset tuotteet.
	 */
	public void createProductArrays() {
		Product[] allProducts = Database.getProducts();
		discountProducts = new ArrayList<Product>();
		noDiscountProducts = new ArrayList<Product>();
		
		int i = 0;
		for (Product p : allProducts) {
			if (p.getForSaleStatus()) { //Jos tuote on myynnissä
				if (p.getSaleMultiplier() < 0.99) {
					discountProducts.add(p);
				} else {
					noDiscountProducts.add(p);
				}
			} 
		}
		
		Collections.sort(discountProducts); 
	}
	
	
	/**
	 * Asettaa sivun ylälaitaan käyttäjänimen ja saldot
	 */
	public void refreshAccountInfo() {
		nameLabel.setText(User.getUsername());
		creditLabel.setText(String.valueOf(User.getCredits()));
		coinLabel.setText(String.valueOf(User.getCoins()));
	}
	
	/**
	 * Avaa uuden ikkunan, jossa käyttäjä varmistaa klikatun tuotteen ostamisen.
	 * @param p tuote, jota käyttäjä on ostamassa
	 */
	public void buyProduct(Product p) {
		
		Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL); //Taustalla olevaa ikkunaa ei voi klikkailla, ennenkuin tämä suljetaan
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(24, 16, 16, 24));
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(156,114,62), CornerRadii.EMPTY, Insets.EMPTY)));
        
        HBox hbox = new HBox(18);
        hbox.setPadding(new Insets(10, 0, 0, 0));
        
        Label label = new Label();
        label.setText(texts.getString("purchase.confirmation"));    
        label.setFont(Font.font("Arial Black", FontWeight.NORMAL, 15));
        label.setTextFill(Color.WHITE);
        
        Label label2 = new Label();
        label2.setText(p.getDescription() + "?\n\n");
        label2.setFont(Font.font("Arial Black", FontWeight.NORMAL, 15));
        label2.setTextFill(Color.WHITE);
        
        Button noBtn = new Button(texts.getString("no"));
        noBtn.setFont(Font.font("Arial Black", FontWeight.NORMAL, 17));
        Button yesBtn = new Button(texts.getString("yes"));
        yesBtn.setFont(Font.font("Arial Black", FontWeight.NORMAL, 17));
        
        Label successLabel = new Label();
        successLabel.setFont(Font.font("Arial Black", FontWeight.NORMAL, 15));
        successLabel.setTextFill(Color.WHITE);
        
        noBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            	dialog.close();
            }
        });
        
        yesBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            	if (Database.buyProduct(p)) {
            		successLabel.setText(texts.getString("success"));
            		refreshAccountInfo();
            	}
            }
        });
        
        vbox.getChildren().addAll(label, label2, hbox, successLabel);
        hbox.getChildren().addAll(noBtn, yesBtn);
        
        Scene dialogScene = new Scene(vbox, 358, 208);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
        
	}
	
	
	public void toUserInfo(ActionEvent e) {
		Stage window = (Stage) nameLabel.getScene().getWindow();
		Navigator.toUserInfo(window);
	} 
	
	public void toMain(ActionEvent e) {
		Stage window = (Stage) nameLabel.getScene().getWindow();
		Navigator.toMainView(window);
	}
	
	public void logOut(ActionEvent e) {
		Stage window = (Stage) nameLabel.getScene().getWindow();
		Navigator.logout(window);
	}
	
	
	
}
