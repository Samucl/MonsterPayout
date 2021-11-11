package slotgames;

public class PeliTestaaja {

	public static void main(String[] args) {
		//SlotsMadness peli = new SlotsMadness();
		//peli.spin();
		//peli.checkLines();
		AbstractSlotgame1 game = new Slotgame1_Spins_of_Madness();
		game.spin();
		game.checkLines();

	}

}
