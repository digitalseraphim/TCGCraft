package digitalseraphim.tcgc.blocks;

import ibxm.Player;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockMagicFluid extends BlockFluidClassic {

	public BlockMagicFluid(Fluid fluid, Material material) {
		super(fluid, material);
		setBlockTextureName("water_flow");
	}

	@Override
	public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3) {
		if(!(par5Entity instanceof EntityPlayer)){
			super.velocityToAddToEntity(par1World, par2, par3, par4, par5Entity, par6Vec3);
			par6Vec3.xCoord*=10;
			par6Vec3.yCoord*=10;
			par6Vec3.zCoord*=10;
		}else{
			par6Vec3.xCoord=0;
			par6Vec3.yCoord=0;
			par6Vec3.zCoord=0;			
		}
	}
}
