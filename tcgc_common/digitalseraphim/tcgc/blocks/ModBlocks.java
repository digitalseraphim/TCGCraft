package digitalseraphim.tcgc.blocks;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import digitalseraphim.tcgc.core.helpers.BlockIDs;
import digitalseraphim.tcgc.core.helpers.Strings;

public class ModBlocks {
	public static BlockCardTable cardTable;
	public static BlockMagicFluid magicFlowingWater;
	public static BlockMagicFluid magicStationaryWater;
	
	public static void init(){
		cardTable = new BlockCardTable(BlockIDs.CARD_TABLE_ID);
		GameRegistry.registerBlock(cardTable, Strings.MOD_ID + "_cardTable");
		magicFlowingWater = new BlockMagicFluid(BlockIDs.MAGIC_FLOWING_WATER_ID, FluidRegistry.WATER, Material.water);
		GameRegistry.registerBlock(magicFlowingWater, Strings.MOD_ID + "_magicFlowingWater");
	}
}
