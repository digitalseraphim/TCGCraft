package digitalseraphim.tcgc.blocks;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import digitalseraphim.tcgc.core.helpers.Strings;

public class ModBlocks {
	public static BlockCardTable cardTable;
	public static BlockMagicFluid magicFlowingWater;
	public static Fluid magicWaterFluid;
	public static void init(){
		cardTable = new BlockCardTable();
		GameRegistry.registerBlock(cardTable, Strings.MOD_ID + "_cardTable");
		magicWaterFluid = new Fluid("magicWater").setLuminosity(15).setViscosity(12000);
		FluidRegistry.registerFluid(magicWaterFluid);
		magicFlowingWater = new BlockMagicFluid(magicWaterFluid, Material.water);
		GameRegistry.registerBlock(magicFlowingWater, Strings.MOD_ID + "_magicFlowingWater");
	}
}
