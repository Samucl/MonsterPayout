import model.Session;
import model.SlotMachine;

public class Main {
	public static void main(String args[]) {
		
		/*
		 * VÄLIAIKAINEN: Testataan slotin toimintaa
		SlotMachine peli = new SlotMachine();
		for (int i=0;i<100;i++) {
			peli.play();
		}
		*/
		
		/*
		 * Haetaan resursseista kaikki profiilikuvat sessioon
		 */
		Session.loadAvatarImages();
		
		/*
		 * Käynnistetään ohjelma
		 */
		view.MainApplication.main(args); 
	}
}
