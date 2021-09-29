import model.Session;
import model.SlotMachine;

public class Main {
	public static void main(String args[]) {
		// VÄLIAIKAINEN: Testataan slotin toimintaa
		Session.loadAvatarImages();
		SlotMachine peli = new SlotMachine();
		for (int i=0;i<100;i++) {
			peli.play();
		}
		view.MainApplication.main(args);
	}
}
