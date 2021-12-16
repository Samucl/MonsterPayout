package monsterpayout.app;
import model.Session;

public class Main {
	public static void main(String args[]) {
		/*
		 * Haetaan resursseista kaikki profiilikuvat sessioon
		 */
		Session.initialization();

		/*
		 * Käynnistetään ohjelma
		 */
		view.MainApplication.main(args);
	}
}
