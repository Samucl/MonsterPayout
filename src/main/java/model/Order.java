package model;

import java.time.LocalDate;
import java.sql.Date;

public class Order {
	private int orderId;
	private String date;
	private String description;
	private double price;
	private double credits;
	
	private LocalDate dateSeparated;
	private String timeSeparated;
	
	public Order(int id, String date, String description, double price, double credits) {
		this.orderId = id;
		this.date = date;
		this.description = description;
		this.price = price;
		this.credits = credits;
		
		try {
			String[] strings = date.split(" ");
			dateSeparated = Date.valueOf(strings[0]).toLocalDate();
			timeSeparated = strings[1];
		} catch (Exception e) {
			dateSeparated = null;
			timeSeparated = "";
		}
	}
	
	public LocalDate getDateAsLocalDate() {
		return dateSeparated;
	}
	
	public String getTimeString() {
		return timeSeparated;
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
