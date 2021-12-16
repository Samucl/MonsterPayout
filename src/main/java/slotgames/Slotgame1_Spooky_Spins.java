package slotgames;

public class Slotgame1_Spooky_Spins extends AbstractSlotgame1 {

	private final String icon_pack = resourcePath+"SpookySpins/";
	private String[] symbolSetURL;

	@Override
	void createSymbols() {
		if(symbolSetURL==null)
			throw new NullPointerException("Virhe: symbooli settiä ei ole määritetty");
			symbols = new SlotSymbol[]{
					new SlotSymbol(symbolSetURL[0], 2, "bonus",false,true,false),

					new SlotSymbol(symbolSetURL[1], 0, "scatter",false,false,true),

					new SlotSymbol(symbolSetURL[2], 2, "wild",true,false,false),

					new SlotSymbol(symbolSetURL[3], 0, "coin"),

					new SlotSymbol(symbolSetURL[4], 1, "donut"),

					new SlotSymbol(symbolSetURL[5], 2, "spider"),

					new SlotSymbol(symbolSetURL[6], 3, "candies"),

					new SlotSymbol(symbolSetURL[7], 4, "candy"),

					new SlotSymbol(symbolSetURL[8], 5, "pumpkin1"),

					new SlotSymbol(symbolSetURL[9], 5, "apple")
			};

	}

	@Override
	void loadSymbols() {
		symbolSetURL = new String[] {
				icon_pack+"bonus"+".gif",
				icon_pack+"scatter"+".gif",
				icon_pack+"wild"+".gif",
				icon_pack+"coin"+".gif",
				icon_pack+"donut"+".gif",
				icon_pack+"spider"+".gif",
				icon_pack+"candies"+".gif",
				icon_pack+"candy"+".gif",
				icon_pack+"pumpkin1"+".gif",
				icon_pack+"apple"+".gif"
		};
	}
}