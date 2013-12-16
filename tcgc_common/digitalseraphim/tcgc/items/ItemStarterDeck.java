package digitalseraphim.tcgc.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import digitalseraphim.tcgc.TCGCraft;

public class ItemStarterDeck extends Item {
	
	public ItemStarterDeck(int id) {
		super(id);
		setCreativeTab(TCGCraft.tabsTCGC);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer player) {
		if(world.isRemote){
			itemStack.stackSize --;
			return itemStack;
		}

		ItemStack is = new ItemStack(TCGCraft.proxy.boosterItem);
		is.stackSize=3;
		
		EntityItem entityItem = player.dropPlayerItem(is);
        entityItem.delayBeforeCanPickup = 0;

        return itemStack;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 16;
	}

	Icon packIcon;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		packIcon = iconRegister.registerIcon("tcgc:textures/items/card_pack.png"); 
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return packIcon;
	}
}
