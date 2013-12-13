package digitalseraphim.tcgc.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import digitalseraphim.tcgc.core.helpers.BlockIDs;
import digitalseraphim.tcgc.core.helpers.Strings;

public class ModBlocks {
	public static BlockCardTable cardTable;
	
	public static void init(){
		cardTable = new BlockCardTable(BlockIDs.CARD_TABLE_ID);
		GameRegistry.registerBlock(cardTable, Strings.MOD_ID + "_cardTable");
	}
}
