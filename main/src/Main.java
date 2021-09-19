import model.SlottiPeli;

public class Main {
	public static void main(String args[]) {
		// VÄLIAIKAINEN: Testataan slotin toimintaa
		SlottiPeli peli = new SlottiPeli();
		for (int i=0;i<20;i++) {
			peli.play();
		}
		view.MainApplication.main(args);
	}
}
