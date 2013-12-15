package digitalseraphim.tcgc.blocks;

import cpw.mods.fml.common.network.Player;
import net.minecraft.block.BlockFlowing;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockMagicFlowing extends BlockFlowing {

	protected BlockMagicFlowing(int par1, Material par2Material) {
		super(par1, par2Material);
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
		return 20;
	}
	
}
