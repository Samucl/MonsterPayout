package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
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
import javafx.scene.effect.DropShadow;
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
	
	@FXML private AnchorPane exchangePane;
	@FXML private GridPane exchangeGridPane;
	@FXML private ChoiceBox<String> buyWithCoinsChoiceBox;
	@FXML private Label buyWithCoinsLabel;
	@FXML private Label chooseAmountLabel;
	@FXML private Label youGetThisLabel;
	@FXML private Label creditExchangeLabel;
	@FXML private Button buyWithCoinsBtn;
	@FXML private Label buyWithCoinsSuccessLabel;
	@FXML private ImageView creditImage;
	
	private NumberFormat numberFormat;
	private ResourceBundle texts = Session.getLanguageBundle();
	
	
	public void initialize() {
		numberFormat = Session.getNumberFormatter();
		
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

		exchangePane.setEffect(setDropShadow(50, Color.BLACK));
		exchangeGridPane.setEffect(setDropShadow(50, Color.BLACK));
	}

	private void useLanguageBundle() {
		logoutBtn.setText(texts.getString("logout.button"));
		toHomeBtn.setText(texts.getString("homepage"));
		toUserInfoBtn.setText(texts.getString("user.details"));	
		buyWithCoinsLabel.setText(texts.getString("buy.with.coins"));
		chooseAmountLabel.setText(texts.getString("choose.coin.amount"));
		buyWithCoinsBtn.setText(texts.getString("buy.button"));
	}
	
	/**
	 * Luo grid panen, jonka sarakkeisiin asetetaan alennustuotteiden tiedot.
	 * Horisontaalisia sarakkeita luodaan niin monta kuin myynnissä olevia alennustuotteita löytyy tietokannasta.
	 */
	public void setDiscountProducts() {
		
		GridPane productPane1 = new GridPane();
		productPane1.setVgap(6);
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
			nameLabel.setFont(Font.font("Arial Black", 17));
			nameLabel.setUnderline(true);
			productPane1.add(nameLabel, i, 1);
			
			Label creditLabel = new Label(numberFormat.format(discountProducts.get(i).getCreditAmount()));
			creditLabel.setFont(Font.font("Arial Black", 15));
			productPane1.add(creditLabel, i, 2);

		    ImageView imageView = new ImageView(credit);
		    imageView.setFitWidth(35);
		    //Asetetaan krediitti-kuva merkkijonon pituuden mukaan, jotta tulee sopivasti jonon "jatkeeksi"
		    imageView.setTranslateX(creditLabel.getText().length() * 9.5); 
		    imageView.setPreserveRatio(true);
		    productPane1.add(imageView, i, 2);
		    
			if (discountProducts.get(i).getCoinAmount() != 0) {
				
				Label coinLabel = new Label(numberFormat.format(discountProducts.get(i).getCoinAmount()));
				coinLabel.setFont(Font.font("System", FontWeight.BOLD, 15));
				productPane1.add(coinLabel, i, 3);
			    ImageView coinIV = new ImageView(coin);
			    coinIV.setFitWidth(26);
			    //Asetetaan kuva merkkijonon pituuden mukaan, jotta tulee sopivasti jonon "jatkeeksi"
			    coinIV.setTranslateX(coinLabel.getText().length() * 10); 
			    coinIV.setPreserveRatio(true);
			    productPane1.add(coinIV, i, 3);
			}
	        
			
			if (discountProducts.get(i).getSaleMultiplier() < 0.99) {
				String oldPriceStr = String.valueOf(discountProducts.get(i).getPrice()) + " €";
				Text oldPriceTxt = new Text(oldPriceStr);
				oldPriceTxt.setStrikethrough(true);
				oldPriceTxt.setFill(Color.WHITE);
				oldPriceTxt.setFont(Font.font(16));
				productPane1.add(oldPriceTxt, i, 4);
			}
			
			double price = discountProducts.get(i).getPrice() * discountProducts.get(i).getSaleMultiplier();
			
			Label priceLabel = new Label(Double.toString(price) + " €");
			priceLabel.setFont(Font.font("Arial Black", 16));
			productPane1.add(priceLabel, i, 5);
			
			Button buyBtn = new Button();
			buyBtn.setText(texts.getString("buy.button"));
			buyBtn.setFont(Font.font("System", FontWeight.BOLD, 17));
			buyBtn.setStyle("-fx-background-color: white");
			productPane1.add(buyBtn, i, 6);
		
			
			Product p = discountProducts.get(i);		
			buyBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                	buyProduct(p);
                }
			});
			
		}
		
		hbox.getChildren().add(productPane1);
		hbox.setEffect(setDropShadow(50, Color.BLACK));
		scrollpane.setContent(productPane1);
	}
	
	/**
	 * Luo grid panen, jonka sarakkeisiin asetetaan normaalihintaisten tuotteiden tiedot.
	 * Horisontaalisia sarakkeita luodaan niin monta kuin myynnissä olevia normaalihintaisia tuotteita löytyy tietokannasta.
	 */
	public void setNoDiscountProducts() {
		
		GridPane productPane2 = new GridPane();
		productPane2.setVgap(6);
		productPane2.setPadding(new Insets(0, 0, 18, 24));
		
		for (int i = 0 ; i < noDiscountProducts.size() ; i++) {
			
			productPane2.getColumnConstraints().add(new ColumnConstraints(200));
			
			Label nameLabel = new Label(noDiscountProducts.get(i).getDescription());
			nameLabel.setFont(Font.font("Arial Black", 17));
			nameLabel.setUnderline(true);
			productPane2.add(nameLabel, i, 0);
			
			Label creditLabel = new Label(numberFormat.format(noDiscountProducts.get(i).getCreditAmount()));
			creditLabel.setFont(Font.font("Arial Black", 15));
			productPane2.add(creditLabel, i, 1);
			
		    ImageView imageView = new ImageView(credit);
		    imageView.setFitWidth(35);
		    //Asetetaan kuva merkkijonon pituuden mukaan, jotta tulee sopivasti jonon "jatkeeksi"
		    imageView.setTranslateX(creditLabel.getText().length() * 9.5); 
		    imageView.setPreserveRatio(true);
		    productPane2.add(imageView, i, 1);
		    
		    if (noDiscountProducts.get(i).getCoinAmount() != 0) { 
				
		    	Label coinLabel = new Label(numberFormat.format(noDiscountProducts.get(i).getCoinAmount()));
				coinLabel.setFont(Font.font("System", FontWeight.BOLD, 15));
				productPane2.add(coinLabel, i, 2);
			    ImageView coinIV = new ImageView(coin);
			    coinIV.setFitWidth(26);
			    //Asetetaan kuva merkkijonon pituuden mukaan, jotta tulee sopivasti jonon "jatkeeksi"
			    coinIV.setTranslateX(coinLabel.getText().length() * 10); 
			    coinIV.setPreserveRatio(true);
			    productPane2.add(coinIV, i, 2);
				
			}
			
			double price = noDiscountProducts.get(i).getPrice();
			
			Label priceLabel = new Label(Double.toString(price) + " €");
			priceLabel.setFont(Font.font("Arial Black", 16));
			productPane2.add(priceLabel, i, 3);
			
			
			Button buyBtn = new Button();
			buyBtn.setText(texts.getString("buy.button"));
			buyBtn.setFont(Font.font("System", FontWeight.BOLD, 17));
			buyBtn.setStyle("-fx-background-color: white");
			productPane2.add(buyBtn, i, 4);
			
			Product p = noDiscountProducts.get(i);		
			buyBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                	buyProduct(p);
                }
			});


		}
		
		hbox2.getChildren().add(productPane2);
		hbox2.setEffect(setDropShadow(50, Color.BLACK));
		scrollpane2.setContent(productPane2);
		
	}
	
	/**
	 * Asettaa choice boxiin neljä kolikkoarvoa, joita käyttäjä pystyy vaihtamaan krediiteiksi.
	 */	
	public void setChoiceBoxItems() {
		buyWithCoinsChoiceBox.getItems().add(numberFormat.format(100));
		buyWithCoinsChoiceBox.getItems().add(numberFormat.format(1000));
		buyWithCoinsChoiceBox.getItems().add(numberFormat.format(10000));
		buyWithCoinsChoiceBox.getItems().add(numberFormat.format(100000));
		
		buyWithCoinsChoiceBox.setOnAction((e) -> {	
			
			//Muunnetaan choiceboxin alkio int-muotoon
			String coinAmountStr = buyWithCoinsChoiceBox.getSelectionModel().getSelectedItem();
			Number coinAmountNum = 0;
			try {
				coinAmountNum = numberFormat.parse(coinAmountStr);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			int coinAmount = coinAmountNum.intValue();
			
		    double creditAmount = coinAmount / 1000.0;
			
			youGetThisLabel.setText(texts.getString("you.get"));
			creditExchangeLabel.setText(numberFormat.format(creditAmount));
			buyWithCoinsBtn.setText(texts.getString("exchange"));	
			youGetThisLabel.setVisible(true);
			buyWithCoinsBtn.setVisible(true);
			creditExchangeLabel.setVisible(true);
			creditImage.setVisible(true);
			
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
	 * Asettaa sivun ylälaitaan käyttäjänimen ja saldot.
	 */
	public void refreshAccountInfo() {
		nameLabel.setText(User.getUsername());
		creditLabel.setText(texts.getString("credits") + ": " + numberFormat.format(User.getCredits()));
		coinLabel.setText(texts.getString("coins") + ": " +  numberFormat.format(User.getCoins()));
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
        vbox.setStyle("-fx-background-color: #232932");
        
        HBox hbox = new HBox(18);
        hbox.setPadding(new Insets(10, 0, 0, 0));
        
        Label label = new Label();
        label.setText(texts.getString("purchase.confirmation"));    
        label.setFont(Font.font("System", FontWeight.BOLD, 16));
        label.setTextFill(Color.rgb(255, 228, 0));
        
        Label label2 = new Label();
        label2.setText(p.getDescription() + "?\n\n");
        label2.setFont(Font.font("System", FontWeight.BOLD, 16));
        label2.setTextFill(Color.rgb(255, 228, 0));
        
        Button noBtn = new Button(texts.getString("no"));
        noBtn.setFont(Font.font("System", FontWeight.BOLD, 18));
        Button yesBtn = new Button(texts.getString("yes"));
        yesBtn.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        Label successLabel = new Label();
        successLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        successLabel.setTextFill(Color.rgb(255, 228, 0));
        
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
        
        hbox.getChildren().addAll(noBtn, yesBtn);
        vbox.getChildren().addAll(label, label2, hbox, successLabel);
        
        Scene dialogScene = new Scene(vbox, 354, 228);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
        
	}
	
	private DropShadow setDropShadow(int intensity, Color color) {
		DropShadow ds = new DropShadow();
		ds.setColor(color);
		ds.setHeight(intensity);
		ds.setWidth(intensity);
		return ds;
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
