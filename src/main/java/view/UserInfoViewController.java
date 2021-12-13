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
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
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
	@FXML Text profile_username, profile_firstname, profileText;
	@FXML ImageView profile_image;
	@FXML Text profile_kredits, profile_coins;
	@FXML TextField username, firstname, lastname, email, account_number;
	@FXML Label login_streak, firstnameLabel, usernameLabel, loginStreakLabel;
	@FXML Tab profileTab, editProfileTab, purchaseHistoryTab;
	@FXML TabPane editProfileTabPane;
	@FXML ListView<String> purchase_history;
	@FXML Button save_button, cancel_button;;
	@FXML Button home_button, logoutButton, toStoreButton;
	@FXML Label profilePictureLabel, usernameLabel2, firstnameLabel2, lastnameLabel, emailLabel, accountNumberLabel;
	@FXML Label errorLabel;
	@FXML MenuButton languageButton;
	@FXML Button editPictureButton;
	@FXML ImageView profile_picture, profile_picture0, profile_picture1, profile_picture2, profile_picture3, profile_picture4, profile_picture5;
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
    	profileTab.setText(texts.getString("profile"));
    	editProfileTab.setText(texts.getString("edit") + " " + texts.getString("profile.partitive").toLowerCase());
    	purchaseHistoryTab.setText(texts.getString("purchase.history"));
    	firstnameLabel.setText(texts.getString("firstname"));
    	usernameLabel.setText(texts.getString("username"));
    	languageButton.setText(texts.getString("language"));
    	editPictureButton.setText(texts.getString("change.profile.pic"));
    	profileText.setText(texts.getString("your.profile"));
    	loginStreakLabel.setText(texts.getString("login.streak"));
    	cancel_button.setText(texts.getString("cancel.button"));
    	save_button.setText(texts.getString("save.button"));
    	usernameLabel2.setText(texts.getString("username"));
    	firstnameLabel2.setText(texts.getString("firstname"));
    	lastnameLabel.setText(texts.getString("lastname"));
    	emailLabel.setText(texts.getString("email"));
    	accountNumberLabel.setText(texts.getString("account.number"));
    	profilePictureLabel.setText(texts.getString("change.profile.pic"));
    	home_button.setText(texts.getString("user.details"));
    	logoutButton.setText(texts.getString("logout"));
    	toStoreButton.setText(texts.getString("store"));
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
		errorLabel.setVisible(false);
		errorLabel.setEffect(setDropShadow(20, Color.RED));
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
			errorLabel.setText(texts.getString(""));
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
			errorLabel.setVisible(true);
			System.out.println("Etunimi on liian pitkä (max 20-merkkiä)");
			errorLabel.setText(texts.getString("firstname") + " " + texts.getString("too.long").toLowerCase() + " (max 20-" + texts.getString("characters").toLowerCase() + ")");
			return;
		}
		if(newLastname.length() > 20) {
			errorLabel.setVisible(true);
			System.out.println("Sukunimi on liian pitkä (max 20-merkkiä)");
			errorLabel.setText(texts.getString("lastname") + " " + texts.getString("too.long").toLowerCase() + " (max 20-" + texts.getString("characters").toLowerCase() + ")");
			return;
		}
		if(newEmail.length() > 40) {
			errorLabel.setVisible(true);
			System.out.println("Sähköpostiosoite on liian pitkä (max 40-merkkiä)");
			errorLabel.setText(texts.getString("email") + " " + texts.getString("too.long").toLowerCase() + " (max 40-" + texts.getString("characters").toLowerCase() + ")");
			return;
		}
		if(newAccountNumber.length() > 25) {
			errorLabel.setVisible(true);
			System.out.println("Tilinumero on liian pitkä (max 25-merkkiä)");
			errorLabel.setText(texts.getString("account.number") + " " + texts.getString("too.long").toLowerCase() + " (max 25-" + texts.getString("characters").toLowerCase() + ")");
			return;
		}
		
		User.setFirstname(newFirstname);
		User.setLastname(newLastname);
		User.setEmail(newEmail);
		User.setAccountNumber(newAccountNumber);
		System.out.println("Tallennetaan");
		if(Database.saveProfileChanges()) {
			errorLabel.setVisible(true);
			errorLabel.setEffect(setDropShadow(20, Color.LIGHTSKYBLUE));
			errorLabel.setText(texts.getString("update.succesful"));
		}
		else {
			errorLabel.setVisible(true);
			errorLabel.setText(texts.getString("update.failed"));
		}
		profileInit();
	}
	
	public void cancelChanges() {
		errorLabel.setVisible(false);
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
		images[0].setEffect(setDropShadow(50, Color.LIGHTSKYBLUE));
		selectedImage = 0;
	}
	public void selectImage1() {
		removeEffects();
		images[1].setEffect(setDropShadow(50, Color.LIGHTSKYBLUE));
		selectedImage = 1;
	}
	public void selectImage2() {
		removeEffects();
		images[2].setEffect(setDropShadow(50, Color.LIGHTSKYBLUE));
		selectedImage = 2;
	}
	public void selectImage3() {
		removeEffects();
		images[3].setEffect(setDropShadow(50, Color.LIGHTSKYBLUE));
		selectedImage = 3;
	}
	public void selectImage4() {
		removeEffects();
		images[4].setEffect(setDropShadow(50, Color.LIGHTSKYBLUE));
		selectedImage = 4;
	}
	public void selectImage5() {
		removeEffects();
		images[5].setEffect(setDropShadow(50, Color.LIGHTSKYBLUE));
		selectedImage = 5;
	}
	
	private void removeEffects() {
		images[selectedImage].setEffect(null);
	}
	
	private DropShadow setDropShadow(int intensity, Color color) {
		DropShadow ds = new DropShadow();
		ds.setColor(color);
		ds.setHeight(intensity);
		ds.setWidth(intensity);
		return ds;
	}
	
	public void toMainView(ActionEvent e) {
		Stage window = (Stage) home_button.getScene().getWindow();
		Navigator.toMainView(window);	
	}
	
	public void logout(ActionEvent e) {
        Stage window = (Stage) logoutButton.getScene().getWindow();
		Navigator.logout(window);
	}
	
	public void toStore(ActionEvent e) {
		Stage window = (Stage) toStoreButton.getScene().getWindow();
		Navigator.toStore(window);
	}
	
	@Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	init();
    }

}
