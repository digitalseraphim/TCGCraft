package digitalseraphim.tcgc.blocks;

import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;
import digitalseraphim.tcgc.core.helpers.BlockIDs;
import digitalseraphim.tcgc.core.helpers.Strings;

public class ModBlocks {
	public static BlockCardTable cardTable;
	public static BlockMagicFlowing magicFlowingWater;
	public static BlockMagicFlowing magicStationaryWater;
	
	public static void init(){
		cardTable = new BlockCardTable(BlockIDs.CARD_TABLE_ID);
		GameRegistry.registerBlock(cardTable, Strings.MOD_ID + "_cardTable");
		magicFlowingWater = new BlockMagicFlowing(BlockIDs.MAGIC_FLOWING_WATER_ID, Material.water);
		GameRegistry.registerBlock(magicFlowingWater, Strings.MOD_ID + "_magicFlowingWater");
		magicStationaryWater = new BlockMagicFlowing(BlockIDs.MAGIC_FLOWING_WATER_ID+1, Material.water);
		
		GameRegistry.registerBlock(magicStationaryWater, Strings.MOD_ID + "_magicFlowingWater");
	}
}
