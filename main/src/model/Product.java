package model;

public class Product {
	private int number;
	private String description;
	private double price;
	private double creaditAmout;
	private double saleMultiplier;
	
	public Product(int number, String description, double price
			, double creditAmount, double saleMultiplier) {
		this.number = number;
		this.description = description;
		this.price = price;
		this.creaditAmout = creditAmount;
		this.saleMultiplier = saleMultiplier;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setCreaditAmout(double creaditAmout) {
		this.creaditAmout = creaditAmout;
	}

	public void setSaleMultiplier(double saleMultiplier) {
		this.saleMultiplier = saleMultiplier;
	}

	public int getNumber() {
		return number;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public double getCreditAmount() {
		return creaditAmout;
	}

	public double getSaleMultiplier() {
		return saleMultiplier;
	}
	
}
