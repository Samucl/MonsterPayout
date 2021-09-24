package model;

public class Product {
	private int id;
	private String description;
	private double price;
	private double creditAmount;
	private double saleMultiplier;
	
	public Product(int number, String description, double price
			, double creditAmount, double saleMultiplier) {
		this.id = number;
		this.description = description;
		this.price = price;
		this.creditAmount = creditAmount;
		this.saleMultiplier = saleMultiplier;
	}

	public void setId(int number) {
		this.id = number;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setCreditAmount(double creaditAmout) {
		this.creditAmount = creaditAmout;
	}

	public void setSaleMultiplier(double saleMultiplier) {
		this.saleMultiplier = saleMultiplier;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public double getCreditAmount() {
		return creditAmount;
	}

	public double getSaleMultiplier() {
		return saleMultiplier;
	}
	
}
