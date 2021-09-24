package model;

public class Session {
	/*
	 * Tänne luokkaan tallennetaan "istunnon" tiedot, kuten tilaushistoria
	 */
	private static Order[] orders;
	
	public Session(Order[] orders) {
		Session.orders = orders;
	}
	
	public static void setOrders(Order[] orders) {
		Session.orders = orders;
	}
	
	public static Order[] getOrders() {
		return orders;
	}

}
