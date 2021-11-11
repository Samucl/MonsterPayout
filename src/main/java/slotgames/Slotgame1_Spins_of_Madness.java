package slotgames;

import javafx.scene.image.Image;

public class Slotgame1_Spins_of_Madness extends AbstractSlotgame1 {
	
	private Image[] symbolSet = {new Image("")};

	@Override
	void createSymbols() {
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
}
