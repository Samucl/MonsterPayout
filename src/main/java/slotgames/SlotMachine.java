package slotgames;

import java.util.*;

public class SlotMachine {
	private int[] reel = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4 ,4, 5, 5, 5, 6, 6, 6};
	private int[] winnings = new int[] {0, 10, 20, 30, 60, 120, 200};
	private int[] outcome = new int[3];
	
	public SlotMachine(){}
	
	public SlotMachine(int[] reel, int[] winnings) {
		this.reel = reel;
		this.winnings = winnings;
	}
	
	// Pelataan kierros slotteja
	public void play() {
		spin();
	}
	
	// Pyöräytetään yksi kierros numeroita koneesta
	public void spin() {
		Random rand = new Random();
		for (int i=0;i<3; i++) {
			outcome[i] = reel[rand.nextInt(reel.length)];
		}
	}
	
	// Tarkistetaan onko voittoja
	public boolean checkWin() {
		if ((outcome[0] == outcome[1] && outcome[0] == outcome[2]) || (outcome[0] == 6 && outcome[1] == outcome[2]) || (outcome[1] == 6 && outcome[0] == outcome[2]) || (outcome[2] == 6 && outcome[0] == outcome[1]) ||
				(outcome[0] == 6 && outcome[1] == 6) || (outcome[1] == 6 && outcome[2] == 6) || (outcome[0] == 6 && outcome[2] == 6)){ // Erikseen tarkistetaan jos tulee "wild" iconeja
			return true;
		}
		return false;
	}
	
	// Maksetaan voitot
	public int payout() {
		for(int i = 0; i < outcome.length; i++) {
			if(outcome[i] != 6) {
				return winnings[outcome[i]]; // Jos kaikki rivit on 6 eli "wild"
			}	
		}
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
