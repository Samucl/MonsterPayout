package slotgames;

import java.io.File;
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
					
					new SlotSymbol(symbolSet[0], 0, "scatter",false,false,true),
					
					new SlotSymbol(symbolSet[0], 2, "wild",true,false,false),
					
					new SlotSymbol(symbolSet[0], 0, "kulta"),
					
					new SlotSymbol(symbolSet[0], 1, "hopea"),
					
					new SlotSymbol(symbolSet[0], 2, "pronssi"),
					
					new SlotSymbol(symbolSet[0], 3, "hakku"),
					
					new SlotSymbol(symbolSet[0], 4, "lintu"),
					
					new SlotSymbol(symbolSet[0], 5, "marjat"),
					
					new SlotSymbol(symbolSet[0], 5, "muki")
			};
		
	}

	@Override
	void loadSymbols() {
		
		try {
			symbolSet = new Image[] {
					new Image(new FileInputStream(icon_pack+"placeholder"+".png"))
					};
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
