package digitalseraphim.tcgc.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class BlockCardTable extends Block {
	Icon sideIcon;
	
	public BlockCardTable(int id) {
		super(id, Material.rock);
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return sideIcon;
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		sideIcon = iconRegister.registerIcon("tcgc:cardtable_block.png");
	}
}
