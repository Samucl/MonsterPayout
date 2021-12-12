package slotgames;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class Slotgame1_Spooky_Spins extends AbstractSlotgame1 {
	
	private final String icon_pack = resourcePath+"SpookySpins/";
	private Image[] symbolSet;

	@Override
	void createSymbols() {
		if(symbolSet==null)
			throw new NullPointerException("Virhe: symbooli settiä ei ole määritetty");
			symbols = new SlotSymbol[]{
					new SlotSymbol(symbolSet[0], 2, "bonus",false,true,false),
					
					new SlotSymbol(symbolSet[1], 0, "scatter",false,false,true),
					
					new SlotSymbol(symbolSet[2], 2, "wild",true,false,false),
					
					new SlotSymbol(symbolSet[3], 0, "coin"),
					
					new SlotSymbol(symbolSet[4], 1, "donut"),
					
					new SlotSymbol(symbolSet[5], 2, "spider"),
					
					new SlotSymbol(symbolSet[6], 3, "candies"),
					
					new SlotSymbol(symbolSet[7], 4, "candy"),
					
					new SlotSymbol(symbolSet[8], 5, "pumpkin1"),
					
					new SlotSymbol(symbolSet[9], 5, "apple")
			};
		
	}

	@Override
	void loadSymbols() {
		
		try {
			symbolSet = new Image[] {
					new Image(new FileInputStream(icon_pack+"bonus"+".gif")),
					new Image(new FileInputStream(icon_pack+"scatter"+".gif")),
					new Image(new FileInputStream(icon_pack+"wild"+".gif")),
					new Image(new FileInputStream(icon_pack+"coin"+".gif")),
					new Image(new FileInputStream(icon_pack+"donut"+".gif")),
					new Image(new FileInputStream(icon_pack+"spider"+".gif")),
					new Image(new FileInputStream(icon_pack+"candies"+".gif")),
					new Image(new FileInputStream(icon_pack+"candy"+".gif")),
					new Image(new FileInputStream(icon_pack+"pumpkin1"+".gif")),
					new Image(new FileInputStream(icon_pack+"apple"+".gif"))
					};
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}