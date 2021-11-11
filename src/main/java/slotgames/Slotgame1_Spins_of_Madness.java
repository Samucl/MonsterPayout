package slotgames;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class Slotgame1_Spins_of_Madness extends AbstractSlotgame1 {
	
	private final String icon_pack = resourcePath+"SpinsOfMadness/";
	private Image[] symbolSet;

	@Override
	void createSymbols() {
		if(symbolSet==null)
			throw new NullPointerException("Virhe: symbooli settiä ei ole määritetty");
			symbols = new SlotSymbol[]{
					new SlotSymbol(symbolSet[0], 2, "bonus",false,true,false),
					
					new SlotSymbol(symbolSet[1], 0, "scatter",false,false,true),
					
					new SlotSymbol(symbolSet[2], 2, "wild",true,false,false),
					
					new SlotSymbol(symbolSet[3], 0, "kulta"),
					
					new SlotSymbol(symbolSet[4], 1, "hopea"),
					
					new SlotSymbol(symbolSet[5], 2, "pronssi"),
					
					new SlotSymbol(symbolSet[6], 3, "hakku"),
					
					new SlotSymbol(symbolSet[7], 4, "lintu"),
					
					new SlotSymbol(symbolSet[8], 5, "marjat"),
					
					new SlotSymbol(symbolSet[9], 5, "muki")
			};
		
	}

	@Override
	void loadSymbols() {
		
		try {
			symbolSet = new Image[] {
					new Image(new FileInputStream(icon_pack+"bonus"+".png")),
					new Image(new FileInputStream(icon_pack+"scatter"+".png")),
					new Image(new FileInputStream(icon_pack+"wild"+".png")),
					new Image(new FileInputStream(icon_pack+"gold"+".png")),
					new Image(new FileInputStream(icon_pack+"silver"+".png")),
					new Image(new FileInputStream(icon_pack+"bronze"+".png")),
					new Image(new FileInputStream(icon_pack+"axe"+".png")),
					new Image(new FileInputStream(icon_pack+"bird"+".png")),
					new Image(new FileInputStream(icon_pack+"berries"+".png")),
					new Image(new FileInputStream(icon_pack+"mug"+".png"))
					};
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
