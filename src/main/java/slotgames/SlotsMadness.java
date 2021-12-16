/*
 * @author Eljas Hirvelä
 */

package slotgames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlotsMadness {

	private final int bonus_symbols = 3;
	private final int scatter_symbols = 3;
	private final int[] scatter_freespins = {5,10,15};
	/*
	 * Symboolien määrä. Tässä otetaan sama määrä kuin Veikkauksen
	 * Kulta-Jaska 2 pelissä ja matkitaan symboolejen arvoa
	 *
	 * 0 = bonus
	 * 1 = scatter/freespin (3x=5, 4x=10, 5x = 15)
	 * 2 = wild
	 * 3 =
	 * 4 =
	 * 5 =
	 * 6 =
	 * 7 =
	 * 8 =
	 * 9 =
	 */
	private SlotSymbol[] symbols = {
			new SlotSymbol(null, 2, "bonus",false,true,false),

			new SlotSymbol(null, 0, "scatter",false,false,true),

			new SlotSymbol(null, 2, "wild",true,false,false),

			new SlotSymbol(null, 0, "kulta"),

			new SlotSymbol(null, 1, "hopea"),

			new SlotSymbol(null, 2, "pronssi"),

			new SlotSymbol(null, 3, "hakku"),

			new SlotSymbol(null, 4, "lintu"),

			new SlotSymbol(null, 5, "marjat"),

			new SlotSymbol(null, 5, "muki")
	};

	/*
	 * Pelin constructori hakee tähän muuttujaan parhaimman symboolin
	 * mahdollisia 5-wildin putkia varten
	 */
	private SlotSymbol highestPayer = null;

	/*
	 * Tehdään rivit.
	 * 5 riviä, kussakin rivissä 3 symboolia
	 */
	private SlotSymbol[][] rows = new SlotSymbol[3][5];

	/*
	 * Voittolinjat tähän 2-uloitteeseen tauluun.
	 */
	private int[][] win_lines = {
			{0,0,0,0,0},
			{1,1,1,1,1},
			{2,2,2,2,2},
			{0,0,1,0,0},
			{0,1,0,1,0},
			{0,1,1,1,0},
			{0,1,2,1,0},
			{1,0,0,0,1},
			{1,0,1,0,1},
			{1,1,0,1,1},
			{1,1,2,1,1},
			{1,2,1,2,1},
			{1,2,2,2,1},
			{2,2,1,2,2},
			{2,1,2,1,2},
			{2,1,1,1,2},
			{2,1,0,1,2}
	};

	private List<SlotSymbol> reel_of_symbols = new ArrayList<>();

	public SlotsMadness() {
		/*
		 * Jokaista määritettyä symboolia asetetaan
		 * reel_of_symbols listaan niin monta kertaa, kuin
		 * symboolin probability luku on (määrittyy SlotSymbol-olion
		 * "setMultipliers" luokassa switch-casessa.
		 */
		for(SlotSymbol s : symbols) {
			for(int i = 0; i < s.getProbability(); i++)
				reel_of_symbols.add(s);
		}

		/*
		 * Haetaan suurin symbooli
		 */
		highestPayer = symbols[0];
		for(SlotSymbol s : symbols) {
			if(s.getMaxMultiplier()>highestPayer.getMaxMultiplier())
				highestPayer = s;
		}
	}

	public void spin() {
		Random rand = new Random();
		/*
		 * Syötetään kaksiuloitteeseen rows tauluun
		 * satunnaisesti "reel_of_symbols" listasta symbooleja
		 */
		for(SlotSymbol[] row : rows) {
			for(int i = 0; i < row.length; i++) {
				row[i] = reel_of_symbols.get(rand.nextInt(reel_of_symbols.size()));
			}
		}

		/*
		 * Kirjoitetaan näytölle testin vuoksi
		 */
		for (SlotSymbol[] row : rows) {
			String s = "";
			for(int y = 0; y < row.length; y++) {
				s+=row[y].getName()+"-";
			}
			System.out.println(s);
		}
	}

	public void checkLines() {
		double multiply_bet = 0.0;
		int freespins = 0;
		boolean launch_bonus = false;

		/*
		 * Lasketaan Scatter ja Bonukset
		 */
		int bonus = 0;
		int scatter = 0;
		for(SlotSymbol[] row : rows) {
			for(int i = 0; i < rows.length; i++) {
				if(row[i].isBonus())
					bonus++;
				if(row[i].isScatter())
					scatter++;
			}
		}

		/*
		 * Laukastaanko bonus
		 */
		if(bonus>=bonus_symbols)
			launch_bonus = true;

		if(scatter>=scatter_symbols) {
			int freespins_index = 0;
			if(scatter>scatter_symbols) {
				int surplus = scatter-scatter_symbols;
				if(surplus>=scatter_freespins.length)
					freespins_index = scatter_freespins.length-1;
			}
			freespins = scatter_freespins[freespins_index];
		}

		/*
		 * Lasketaan voittorivit
		 */
		for(int[] line : win_lines) {
			/*
			 * Mikä symbooli on kyseessä
			 */
			SlotSymbol symbol = null;

			/*
			 * Kuinka monta samaa symboolia
			 * vierekkäin
			 */
			int connected = 0;

			/*
			 * Jos rivin ensimmäinen symbooli on
			 * wild, määritä symbooli vasta, kun
			 * rivillä tulee vastaan normaali symbooli.
			 * Wild-symbooli alusta loppuun = kovin symbooli
			 * (käy symbooli lista läpi ja valitse se, millä
			 * kovin kerroin)
			 */
			boolean first_is_wild = false;

			/*
			 * Skippaa bonus ja scatter symboolit.
			 * Laske näiden summa toisella tavalla
			 */
			for(int i = 1; i < rows[0].length; i++) {

				/*
				 * Poistutaan for silmukasta jos ensimmäinen symbooli on bonus tai scatter
				 */
				if(rows[line[i-1]][i-1].isBonus()||rows[line[i-1]][i-1].isScatter())
					break;

				/*
				 * Katsotaan onko ensimmäinen symbooli wild-symbooli ja
				 * asetetaan muuttujat sen mukaan.
				 */
				if(rows[line[i-1]][i-1].isWild()) {
					first_is_wild = true;
				} else {
					symbol=rows[line[i-1]][i-1];
				}

				if(symbol!=null) {
					if(symbol==rows[line[i]][i] || rows[line[i]][i].isWild()) {
						/*
						 * ilmoitetaan uudesta yhteydestä
						 */
						connected++;

						/*
						 * Jos ensimmäinen symbooli oli wild tai boolean on pysyny TRUE
						 * muodossa, koska seuraavat symboolit ovat myös olleet wildejä,
						 * yritetään selvittää saadaanko riville "perus" symbooli
						 */
						if(first_is_wild) {
							if(!rows[line[i]][i].isWild()) {
								symbol=rows[line[i]][i];
								first_is_wild = false;
							}
						}
					} else {
						break;
					}
				} else if (first_is_wild) {
					/*
					 * ilmoitetaan uudesta yhteydestä
					 */
					connected++;

					/*
					 * Jos ensimmäinen symbooli oli wild tai boolean on pysyny TRUE
					 * muodossa, koska seuraavat symboolit ovat myös olleet wildejä,
					 * yritetään selvittää saadaanko riville "perus" symbooli
					 */
					if(!rows[line[i]][i].isWild()) {
						symbol=rows[line[i]][i];
						first_is_wild = false;
					}
				} else {
					break;
				}
			}

			/*
			 * Jos voittorivillä osuu symboolit yhteen
			 */
			if(connected>0) {
				/*
				 * Jos first_is_wild boolean on pysynyt TRUE
				 * muodossa, on voittorivin kaikki symboolit
				 * wild-symbooleja, jolloin asetetaan voittosymbooliksi
				 * highestPayer
				 */
				if(first_is_wild)
					symbol = highestPayer;
				multiply_bet+=symbol.getMultipliers()[connected];
			}

			String text = "Connections: "+connected;
			if(symbol!=null)
				text+=" "+symbol.getName();
			System.out.println(text);
		}

		System.out.println("Bonus: "+bonus+"\nScatter: "+scatter);
		System.out.println("Panos kerrotaan: "+multiply_bet);
		if(launch_bonus)
			System.out.println("LAUNCH BONUS");
		if(freespins>0)
			System.out.println(freespins+" FREESPINS");
	}

	public void launchBonus() {

	}

}
