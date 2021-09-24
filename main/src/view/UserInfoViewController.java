package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Order;
import model.Session;
import model.Tietokanta;
import model.User;

public class UserInfoViewController implements Initializable {
	@FXML TextField username;
	@FXML TextField firstname;
	@FXML TextField lastname;
	@FXML TextField email;
	@FXML TextField account_number;
	@FXML TextField login_streak;
	@FXML ListView purchase_history;
	@FXML Button save_button;
	@FXML Button cancel_button;
	@FXML Button home_button;
	
	@Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	init();
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
		if(Tietokanta.saveProfileChanges())
			System.out.println("Päivitys onnistui");
		else
			System.out.println("Päivitys ei tainnut onnistua");
	}
	
	public void cancelChanges() {
		System.out.println("Peruutetaan muutokset");
		setTexts();
	}
	
	private void init() {
		setTexts();
		setOrders();
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
	
	public void toMainView(ActionEvent e) {
		
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("HomepageView.fxml"));
            AnchorPane mainView = (AnchorPane) loader.load();
            Scene mainScene = new Scene(mainView);
			Stage window = (Stage) home_button.getScene().getWindow();
			window.setScene(mainScene);
			
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}

}
