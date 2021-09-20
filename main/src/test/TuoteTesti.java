package test;

import model.Product;
import model.Tietokanta;

public class TuoteTesti {
	public static void main(String[] args) {
		Product[] products = Tietokanta.getProducts();
		if(products != null) {
			System.out.println("Tuotteiden määrä: "+products.length);
		} else {
			System.out.println("Tietokannassa ei ole tuotteita");
		}
		
	}
}
