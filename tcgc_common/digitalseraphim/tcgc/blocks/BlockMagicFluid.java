package digitalseraphim.tcgc.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.common.network.Player;

public class BlockMagicFluid extends BlockFluidClassic {

	public BlockMagicFluid(int id, Fluid fluid, Material material) {
		super(id, fluid, material);
		setTextureName("water");
	}

	@Override
	public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3) {
		if(!(par5Entity instanceof Player)){
			super.velocityToAddToEntity(par1World, par2, par3, par4, par5Entity, par6Vec3);
			par6Vec3.xCoord*=10;
			par6Vec3.yCoord*=10;
			par6Vec3.zCoord*=10;
		}
	}
	
	@Override
	public int tickRate(World par1World) {
		return 120;
	}
	
}
