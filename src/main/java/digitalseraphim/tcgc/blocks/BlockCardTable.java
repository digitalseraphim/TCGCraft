package digitalseraphim.tcgc.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import digitalseraphim.tcgc.TCGCraft;
import digitalseraphim.tcgc.tile.TECardTable;

public class BlockCardTable extends BlockContainer {
	IIcon sideIcon;
	IIcon animIcon;
	
	public BlockCardTable() {
		super(Material.rock);
		setCreativeTab(TCGCraft.tabsTCGC);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		return sideIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		sideIcon = iconRegister.registerIcon("tcgc:cardtable_block");
		animIcon = iconRegister.registerIcon("tcgc:anim_strip");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TECardTable();
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
