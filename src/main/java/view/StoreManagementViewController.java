package view;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Database;
import model.Product;
import model.Session;

/*
 * Kaupan hallinta -näkymän kontrolleri.
 *
 * @author Jukka Hallikainen
 */
public class StoreManagementViewController {

	private Product[] products;

	@FXML private TextField nameTF;
	@FXML private TextField creditTF;
	@FXML private TextField coinTF;
	@FXML private TextField priceTF;
	@FXML private Button addNewBtn;
	@FXML private Button editBtn;
	@FXML private Button logOutBtn;
	@FXML private Button removeFromSaleBtn;
	@FXML private Button putUpForSaleBtn;

	@FXML private TableView<Product> productsTable;
	@FXML private TableColumn<Product, Integer> idColumn;
	@FXML private TableColumn<Product, String> nameColumn;
	@FXML private TableColumn<Product, Double> creditColumn;
	@FXML private TableColumn<Product, Integer> coinColumn;
	@FXML private TableColumn<Product, Double> discountColumn;
	@FXML private TableColumn<Product, Double> priceColumn;
	@FXML private TableColumn<Product, Boolean> forSaleColumn;

	private ResourceBundle texts = Session.getLanguageBundle();

	/**
	* Alustaa TableViewin.
	*/
	public void initialize() {

		useLanguageBundle();

		//Määrittää mitä instanssimuuttujaa mikäkin sarake hakee Product-luokasta.
		idColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
		creditColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("creditAmount"));
		coinColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("coinAmount"));
		discountColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("saleMultiplier"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		forSaleColumn.setCellValueFactory(new PropertyValueFactory<Product, Boolean>("forSaleStatus"));

		refreshTableView();

	}

	/**
	* Käy tietokannasta tuotteet, lisää ne ObservableListiin, joka liitetään TableViewiin.
	*/
	public void refreshTableView() {

		products = Database.getProducts();
		if (products != null) {
			ObservableList<Product> productOL = FXCollections.observableArrayList();
			for (Product p : products) {
				productOL.add(p);
			}
			productsTable.setItems(productOL);
		}

	}

	/**
	* Tallentaa uuden tuotteen tietokantaan ja päivittää sen listaan.
	*/
	public void addNewProduct(ActionEvent e) {

		//TODO Tähän myöhemmin parempia validointeja
		if (!(nameTF.getText().isEmpty()) && !(creditTF.getText().isEmpty()) && !(coinTF.getText().isEmpty()) && !(priceTF.getText().isEmpty()) ) {
			String name = nameTF.getText();
			double credits = Double.parseDouble(creditTF.getText());
			double coins = Double.parseDouble(coinTF.getText());
			double price = Double.parseDouble(priceTF.getText());
			Product p = new Product(name, credits, coins, 1, price, false);

			Database.createProduct(p);

			nameTF.setText("");
			creditTF.setText("");
			coinTF.setText("");
			priceTF.setText("");

			refreshTableView();
		}
	}

	/**
	 * Laittaa valitun tuotteen kauppasivulle.
	 * @param e : Click-event.
	 */
	public void putUpForSale(ActionEvent e) {
		Product p = productsTable.getSelectionModel().getSelectedItem();
		if(p != null) {
			p.setForSaleStatus(true);
			Database.editProduct(p);

			refreshTableView();
         }
	}

	/**
	* Poistaa valitun tuotteen kauppasivulta.
	* @param e : Click-event.
	*/
	public void removeFromSale(ActionEvent e) {
		Product p = productsTable.getSelectionModel().getSelectedItem();
		if (p != null) {
			p.setForSaleStatus(false);
			Database.editProduct(p);

			refreshTableView();
         }
	}

	/**
	* Avaa popup-dialogin tuotteen tietojen vaihtamista varten.
	* @param e : Click-event.
	*/
	public void editProduct(ActionEvent e) {

		Product product = productsTable.getSelectionModel().getSelectedItem();

		if (product != null) {
			try {
	        	Stage dialog = new Stage();
	        	dialog.setTitle(product.getDescription());
	            dialog.initModality(Modality.APPLICATION_MODAL); //Taustalla olevaa ikkunaa ei voi klikkailla, ennenkuin tämä suljetaan

	            VBox dialogVbox = new VBox(4);

	            TextField newNameTF = new TextField();
	            newNameTF.setPromptText(product.getDescription());
	            newNameTF.setFocusTraversable(false);

	            TextField newPriceTF = new TextField();
	            newPriceTF.setPromptText(String.valueOf(product.getPrice()));
	            newPriceTF.setFocusTraversable(false);

	            Button editBtn = new Button(texts.getString("set.button"));

	            editBtn.setOnAction(new EventHandler<ActionEvent>(){
	                @Override
	                public void handle(ActionEvent event) {

	                	String newName = newNameTF.getText();
	                	double newPrice = 0;

	                	if (!(newPriceTF.getText().isEmpty())) {
	                		newPrice = Double.valueOf(newPriceTF.getText()); //Jos uusi hinta on syötetty
	                	} else {
	                		newPrice = product.getPrice() * product.getSaleMultiplier(); //Jos käyttäjä ei syötä uutta hintaa niin käytetään aiempaa
	                	}

	                	if ((newPrice != 0) && (newPrice <= product.getPrice())) {

	                		if (newName == null || newName.isEmpty()) {
	                			newName = product.getDescription(); //Jos käyttäjä ei syötä uutta tuotenimeä niin käytetään aiempaa
	                		}

	                		product.setDescription(newName);
	                		double saleMultiplier = newPrice / product.getPrice(); //Huom. tuotteen varsinainen hinta (getPrice) ei koskaan muutu alkuperäisestä, vaan alennuskertoimella lasketaan sen kauppahinta
	                		product.setSaleMultiplier(saleMultiplier);

	                		if (Database.editProduct(product)) {
	                			Label successLabel = new Label();
	                			successLabel.setText("Tiedot tallennettu");
	                			dialogVbox.getChildren().add(successLabel);
	                		}
	                	}
	                }
				});

	            dialogVbox.getChildren().addAll(newNameTF, newPriceTF, editBtn);

	            Scene dialogScene = new Scene(dialogVbox, 200, 150);
	            dialog.setScene(dialogScene);
	            dialog.showAndWait();

	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
		}

		refreshTableView();

	}

	public void useLanguageBundle() {

		idColumn.setText(texts.getString("id.column"));
		nameColumn.setText(texts.getString("name.column"));
		creditColumn.setText(texts.getString("credit.column"));
		coinColumn.setText(texts.getString("coin.column"));
		discountColumn.setText(texts.getString("discount.column"));
		priceColumn.setText(texts.getString("price.column"));
		forSaleColumn.setText(texts.getString("forsale.column"));

		nameTF.setPromptText(texts.getString("name.ph"));
		creditTF.setPromptText(texts.getString("credit.ph"));
		coinTF.setPromptText(texts.getString("coin.ph"));
		priceTF.setPromptText(texts.getString("price.ph"));

		addNewBtn.setText(texts.getString("add.button"));
		editBtn.setText(texts.getString("edit.button"));
		logOutBtn.setText(texts.getString("logout.button"));
		removeFromSaleBtn.setText(texts.getString("remove.from.sale.button"));
		putUpForSaleBtn.setText(texts.getString("put.up.for.sale.button"));

	}

	public void logout(ActionEvent e) {
        Stage window = (Stage) logOutBtn.getScene().getWindow();
		Navigator.logout(window);
	}

}

