package model;

/**
 * Tämä luokka edustaa virtuaalikaupan ja tietokannan tuotepaketteja.
 * Luokka implementoi Comparable-rajapintaa, jotta tuotteet saadaan kauppasivulle järjestyksessä alennuksen mukaan.
 */
public class Product implements Comparable<Product> {
	private int id;
	private String description;
	private double price;
	private double creditAmount;
	private double coinAmount;
	private double saleMultiplier;
	private boolean forSale;

	public Product(String description, double creditAmount, double coinAmount, double saleMultiplier, double price, boolean forSale) {
		this.description = description;
		this.price = price;
		this.creditAmount = creditAmount;
		this.coinAmount = coinAmount;
		this.saleMultiplier = saleMultiplier;
		this.forSale = forSale;
	}

	public Product(int number, String description, double price
			, double creditAmount, double coinAmount, double saleMultiplier, boolean forSale) {
		this.id = number;
		this.description = description;
		this.price = price;
		this.creditAmount = creditAmount;
		this.coinAmount = coinAmount;
		this.saleMultiplier = saleMultiplier;
		this.forSale = forSale;
	}

    @Override
    public int compareTo(Product p){
        return (int) ((int)(this.saleMultiplier * 100) - (p.getSaleMultiplier() * 100)); //K
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

	public void setForSaleStatus(boolean b) {
		this.forSale = b;

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

	public double getCoinAmount() {
		return coinAmount;
	}

}
