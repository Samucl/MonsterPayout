package model;

import java.util.*;

public class SlottiPeli {
	private int[] reel = new int[] {1, 1, 1, 2, 2, 2, 3, 3, 3, 4};
	private int[] winnings = new int[] {0, 10, 20, 30, 60};
	private int[] outcome = new int[3];
	Random rand = new Random();
	
	public SlottiPeli(){}
	
	public SlottiPeli(int[] reel, int[] winnings) {
		this.reel = reel;
		this.winnings = winnings;
	}
	
	// Pelataan kierros slotteja
	public void play() {
		spin();
		if (checkWin()) {
			System.out.println("Voitit " + payout() + " krediittiä!");
		}
	}
	
	// Pyöräytetään yksi kierros numeroita koneesta
	public void spin() {
		for (int i=0;i<3; i++) {
			outcome[i] = reel[rand.nextInt(9)];
		}
		System.out.println(Arrays.toString(outcome));

	}
	
	// Tarkistetaan onko voittoja
	public boolean checkWin() {
		if (outcome[0] == outcome[1] && outcome[0] == outcome[2]) {
			return true;
		}
		System.out.println("Ei voittoja");
		return false;
	}
	
	// Maksetaan voitot
	public int payout() {
		return winnings[outcome[0]];
	}

	// Getterit
	public int[] getReel() {
		return reel;
	}

	public int[] getWinnings() {
		return winnings;
	}

	public int[] getOutcome() {
		return outcome;
	}

	// Setterit
	public void setReel(int[] reel) {
		this.reel = reel;
	}

	public void setWinnings(int[] winnings) {
		this.winnings = winnings;
	}


	public void setOutcome(int[] outcome) {
		this.outcome = outcome;
	}
}
