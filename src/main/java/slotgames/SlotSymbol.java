package slotgames;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class SlotSymbol {
	/*
	 * Kaikki pelit, jotka käyttävät näitä symbooleja 
	 * sisältävät enintään 5 vaaka riviä
	 * (myöhemmin ehkä muokataan joustavammaksi)
	 */
	Double[] multipliers = new Double[5];
	
	private String name;
	private boolean isWild = false;
	private boolean isBonus = false;
	private boolean isScatter = false;
	
	private Image image = null;
	private String imageURL = null;
	
	/*
	 * Mitä isompi luku on probabilityssä, sitä suuremmalla todennäköisyydellä 
	 * symbooli tulee
	 */
	private int probability = 0;
	
	/*
	 * Luotaessa symboolia, annetaan numero
	 * joka kertoo luokalle millaiset kertoimet
	 * asetetaan,
	 * merkkijono joka antaa nimen,
	 * booleanit jotka määrittävät onki symbooli villi tai bonus
	 */
	/*
	public SlotSymbol(Image symbol, int type, String name, boolean wild, boolean bonus, boolean scatter) {
		isWild = wild;
		isBonus = bonus;
		isScatter = scatter;
		this.name = name;
		if(symbol!=null)
			image = symbol;
		setMultipliers(type);
	}
	*/
	
	/*
	 * Perus symboolin constructor
	 */
	/*
	public SlotSymbol(Image symbol, int type, String name) {
		isWild = false;
		isBonus = false;
		isScatter = false;
		this.name = name;
		if(symbol!=null)
			image = symbol;
		setMultipliers(type);
	}
	*/
	
	
	/*
	 * 
	 * -----------------Käytetään kuviin urllää, eikä suoraan javafx Imagea
	 * 
	 */
	
	/*
	 * Luotaessa symboolia, annetaan numero
	 * joka kertoo luokalle millaiset kertoimet
	 * asetetaan,
	 * merkkijono joka antaa nimen,
	 * booleanit jotka määrittävät onki symbooli villi tai bonus
	 */
	public SlotSymbol(String symbolURL, int type, String name, boolean wild, boolean bonus, boolean scatter) {
		isWild = wild;
		isBonus = bonus;
		isScatter = scatter;
		this.name = name;
		if(symbolURL.length()>0)
			imageURL = symbolURL;
		setMultipliers(type);
	}
	
	/*
	 * Perus symboolin constructor
	 */
	public SlotSymbol(String symbolURL, int type, String name) {
		isWild = false;
		isBonus = false;
		isScatter = false;
		this.name = name;
		if(symbolURL.length()>0)
			imageURL = symbolURL;
		setMultipliers(type);
	}
	
	
	
	
	private void setMultipliers(int type) {
		/*
		 * default tekee symboolista huonoimman "perus" symboolin
		 * 0 - on kovin symbooli ja niin edelleen
		 */
			switch(type) {
			case 0:
				multipliers = new Double[]{0.0, 0.5, 3.0, 15.0, 100.0};
				probability = 2;
				break;
			case 1:
				multipliers = new Double[]{0.0, 0.0, 3.0, 10.0, 60.0};
				probability = 5;
				break;
			case 2:
				multipliers = new Double[]{0.0, 0.0, 2.0, 6.0, 20.0};
				probability = 5;
				break;
			case 3:
				multipliers = new Double[]{0.0, 0.0, 1.0, 3.0, 12.0};
				probability = 10;
				break;
			case 4:
				multipliers = new Double[]{0.0, 0.0, 1.0, 3.0, 8.0};
				probability = 20;
				break;
			case 5:
				multipliers = new Double[]{0.0, 0.0, 0.5, 2.0, 4.0};
				probability = 50;
				break;
			default:
				multipliers = new Double[]{0.0, 0.0, 0.5, 2.0, 4.0};
				probability = 50;
				break;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isBonus() {
		return isBonus;
	}
	
	public boolean isWild() {
		return isWild;
	}
	
	public boolean isScatter() {
		return isScatter;
	}
	
	public Double[] getMultipliers() {
		return multipliers;
	}
	
	public int getProbability() {
		return probability;
	}
	
	public double getMaxMultiplier() {
		return multipliers[multipliers.length-1];
	}
	
	public Image getImage() {
		return image;
	}
	
	public Image getNewImage() {
		try {
			return new Image(new FileInputStream(imageURL));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
