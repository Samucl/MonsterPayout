package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Product;
import model.Tietokanta;

public class SetProductsViewController {
	
	private Product[] products;
	
	@FXML private Button replaceOfferBtn;
	@FXML private Button replaceProductBtn1;
	@FXML private Button replaceProductBtn2;
	
	@FXML private TextField descriptionTF;
	@FXML private TextField creditsTF;
	@FXML private TextField discountTF;
	@FXML private TextField priceTF;
	@FXML private Button addNewBtn;
	
	@FXML private Button logOutBtn;
	
	@FXML private TableView<Product> productsTable;
	@FXML private TableColumn<Product, Integer> idColumn;
	@FXML private TableColumn<Product, String> productColumn;
	@FXML private TableColumn<Product, Double> discountColumn;
	@FXML private TableColumn<Product, Double> priceColumn;
	
	//Alustetaan TableView
	public void initialize() {
		
		//Mitä muuttujaa mikäkin kolumni vastaa Product-luokassa
		idColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
		productColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
		discountColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("saleMultiplier"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		
		refreshListView();
	
	}
	
	//Käydään tietokannasta tuotteet, lisätään ne ObservableListiin, joka liitetään TableViewiin
	public void refreshListView() {
		
		products = Tietokanta.getProducts();
		ObservableList<Product> productOL = FXCollections.observableArrayList();
		
		for (Product p : products) {
			productOL.add(p);
		}
		
		productsTable.setItems(productOL);
		
	}
	
	//Syöttää uuden tuotteen tietokantaan ja päivittää sen listaan
	public void addNewProduct(ActionEvent e) {
		
	}
	
	//Laittaa valitun tuotteen kauppasivulle tarjoustuotteeksi
	public void replaceOfferProduct(ActionEvent e) {
		
	}
	
	//Laittaa valitun tuotteen kauppasivulle keskelle
	public void replaceProduct1(ActionEvent e) {
		/*
		 if (productsTable.getSelectionModel().getSelectedItem() != null) {
			 productsTable.getSelectionModel().getSelectedItem().
		 } */
	}
	
	//Laittaa valitun tuotteen kauppasivulle oikealle
	public void replaceProduct2(ActionEvent e) {
		
	}
	
	
}

