package digitalseraphim.tcgc.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import digitalseraphim.tcgc.TCGCraft;
import digitalseraphim.tcgc.tile.TECardTable;

public class BlockCardTable extends BlockContainer {
	Icon sideIcon;
	
	public BlockCardTable(int id) {
		super(id, Material.rock);
		setCreativeTab(TCGCraft.tabsTCGC);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return sideIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		sideIcon = iconRegister.registerIcon("tcgc:anim_strip");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TECardTable();
	}
}
