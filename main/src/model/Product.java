package model;

public class Product {
	private int id;
	private String description;
	private double price;
	private double creditAmount;
	private int coinAmount;
	private double saleMultiplier;
	private boolean forSale;
	
	public Product(int number, String description, double price
			, double creditAmount, int coinAmount, double saleMultiplier, boolean forSale) {
		this.id = number;
		this.description = description;
		this.price = price;
		this.creditAmount = creditAmount;
		this.coinAmount = coinAmount;
		this.saleMultiplier = saleMultiplier;
		this.forSale = forSale;
		
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
	
	public boolean getForSaleStatus() {
		return forSale;
	}

	public double getCreditAmount() {
		return creditAmount;
	}

	public double getSaleMultiplier() {
		return saleMultiplier;
	}
	
}
