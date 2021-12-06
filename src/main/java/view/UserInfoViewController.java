package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Order;
import model.Session;
import model.Database;
import model.User;

public class UserInfoViewController implements Initializable {
	@FXML Text profile_username;
	@FXML Text profile_firstname;
	@FXML ImageView profile_image;
	@FXML Text profile_kredits;
	@FXML Text profile_coins;
	@FXML TextField username;
	@FXML TextField firstname;
	@FXML TextField lastname;
	@FXML TextField email;
	@FXML TextField account_number;
	@FXML Label login_streak;
	@FXML TabPane editProfileTabPane;
	@FXML ListView<String> purchase_history;
	@FXML Button save_button;
	@FXML Button cancel_button;
	@FXML Button home_button;
	@FXML ImageView profile_picture;
	@FXML Button logoutButton;
	@FXML Button toStoreButton;
	@FXML ImageView profile_picture0;
	@FXML ImageView profile_picture1;
	@FXML ImageView profile_picture2;
	@FXML ImageView profile_picture3;
	@FXML ImageView profile_picture4;
	@FXML ImageView profile_picture5;
	private ImageView[] images = new ImageView[6];
	private int selectedImage;
	private ResourceBundle texts;
	
	private void init() {
		texts = Session.getLanguageBundle();
		setImages();
		setTexts();
		setOrders();
		loadProfilePicture();
		profileInit();
		updateLanguage();
	}
	
    public void toEnglish(ActionEvent e) {
    	texts = ResourceBundle.getBundle("lang.language",new Locale("en", "US"));
    	Session.setLanguageBundle(texts);
    	updateLanguage();
    }
    
    public void toFinnish(ActionEvent e) {
    	texts = ResourceBundle.getBundle("lang.language",new Locale("fi", "FI"));
    	Session.setLanguageBundle(texts);
    	updateLanguage();
    }
    
    private void updateLanguage() {
    }
	
	private void setImages() {
		images[0] = profile_picture0;
		images[1] = profile_picture1;
		images[2] = profile_picture2;
		images[3] = profile_picture3;
		images[4] = profile_picture4;
		images[5] = profile_picture5;
	}
	
	public void saveChanges() {
		/*
		 * Poistetaan kaikki mahdolliset "tyhjät" välit tekstistä
		 * "Markus Miettinen" -> "MarkusMiettinen"
		 */
		String newFirstname = firstname.getText().replaceAll("\\s", "");
		String newLastname = lastname.getText().replaceAll("\\s", "");
		String newEmail = email.getText().replaceAll("\\s", "");
		String newAccountNumber = account_number.getText().replaceAll("\\s", "");
		
		/*
		 * Jos käyttäjä ei ole muokannut mitään tekstejä niin poistutaan metodista
		 */
		if(User.getFirstname().equals(newFirstname) && User.getLastname().equals(newLastname)
				&& User.getEmail().equals(newEmail) && User.getAccountNumber().equals(newAccountNumber)) {
			System.out.println("Et ole muokannut mitään tietoja");
			return;
		}
		
		/*
		 * Etunimi max 20-merkkiä
		 * Sukunimi max 20-merkkiä
		 * Sähköposti max 40-merkkiä
		 * Tilinumero max 25-merkkiä
		 * 
		 */
		if(newFirstname.length() > 20) {
			System.out.println("Etunimi on liian pitkä (max 20-merkkiä)");
			return;
		}
		if(newLastname.length() > 20) {
			System.out.println("Sukunimi on liian pitkä (max 20-merkkiä)");
			return;
		}
		if(newEmail.length() > 40) {
			System.out.println("Sähköpostiosoite on liian pitkä (max 40-merkkiä)");
			return;
		}
		if(newAccountNumber.length() > 25) {
			System.out.println("Tilinumero on liian pitkä (max 25-merkkiä)");
			return;
		}
		
		User.setFirstname(newFirstname);
		User.setLastname(newLastname);
		User.setEmail(newEmail);
		User.setAccountNumber(newAccountNumber);
		System.out.println("Tallennetaan");
		if(Database.saveProfileChanges())
			System.out.println("Päivitys onnistui");
		else
			System.out.println("Päivitys ei tainnut onnistua");
		profileInit();
	}
	
	public void cancelChanges() {
		System.out.println("Peruutetaan muutokset");
		setTexts();
	}
	
	private void profileInit() {
		profile_username.setText(User.getUsername());
		//profile_image;
		profile_kredits.setText(texts.getString("credits") + " " + String.valueOf(User.getCredits()));;
		profile_coins.setText(texts.getString("coins") + " " + String.valueOf(User.getCoins()));;
		profile_firstname.setText(User.getFirstname());
	}
	
	private void setTexts() {
		username.setText(User.getUsername());
		firstname.setText(User.getFirstname());
		lastname.setText(User.getLastname());
		email.setText(User.getEmail());
		account_number.setText(User.getAccountNumber());
		login_streak.setText(Integer.toString(User.getLoginStreak()));
	}
	
	private void setOrders() {
		Order[] orders = Session.getOrders();
		/*
		 * Jos ei ole tilauksia niin poistutaan metodista
		 */
		if(orders == null)
			return;
		
		for(int i = orders.length - 1; i >= 0; i--) {
			purchase_history.getItems().add(orders[i].toString());
		}
	}
	
	public void loadProfilePicture() {
		profile_image.setImage(Session.getAvatar(0));
	}
	
	public void editProfile(ActionEvent e) {
		editProfileTabPane.getSelectionModel().select(1);
	}
	
	public void selectImage0() {
		removeEffects();
		images[0].setEffect(setDropShadow(50));
		selectedImage = 0;
	}
	public void selectImage1() {
		removeEffects();
		images[1].setEffect(setDropShadow(50));
		selectedImage = 1;
	}
	public void selectImage2() {
		removeEffects();
		images[2].setEffect(setDropShadow(50));
		selectedImage = 2;
	}
	public void selectImage3() {
		removeEffects();
		images[3].setEffect(setDropShadow(50));
		selectedImage = 3;
	}
	public void selectImage4() {
		removeEffects();
		images[4].setEffect(setDropShadow(50));
		selectedImage = 4;
	}
	public void selectImage5() {
		removeEffects();
		images[5].setEffect(setDropShadow(50));
		selectedImage = 5;
	}
	
	private void removeEffects() {
		images[selectedImage].setEffect(null);
	}
	
	private DropShadow setDropShadow(int intensity) {
		DropShadow ds = new DropShadow();
		ds.setColor(Color.LIGHTSKYBLUE);
		ds.setHeight(intensity);
		ds.setWidth(intensity);
		return ds;
	}
	
	public void toMainView(ActionEvent e) {
		
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("HomepageView.fxml"));
            BorderPane mainView = (BorderPane) loader.load();
            Scene mainScene = new Scene(mainView);
			Stage window = (Stage) home_button.getScene().getWindow();
			window.setScene(mainScene);
			
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void logout(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("LoginView.fxml"));
            BorderPane loginView = (BorderPane) loader.load();
            Scene loginScene = new Scene(loginView);
			Stage window = (Stage) logoutButton.getScene().getWindow();
			Database.logout();
			window.setScene(loginScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public void toStore(ActionEvent e) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("StoreView.fxml"));
            BorderPane storeView = (BorderPane) loader.load();
            Scene storeScene = new Scene(storeView);
			Stage window = (Stage) toStoreButton.getScene().getWindow();
			window.setScene(storeScene);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	@Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	init();
    }

}
