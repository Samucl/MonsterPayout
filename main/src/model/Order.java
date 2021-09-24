package model;

public class Order {
	private int orderId;
	private String date;
	private String description;
	private double price;
	private double credits;
	
	public Order(int id, String date, String description, double price, double credits) {
		this.orderId = id;
		this.date = date;
		this.description = description;
		this.price = price;
		this.credits = credits;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getCredits() {
		return credits;
	}

	public void setCredits(double credits) {
		this.credits = credits;
	}
	
	public String toString(){
		return date + " - " + description + " - " + price +"€"+"   -   "+credits+" krediittiä";
	}
	
	
}
