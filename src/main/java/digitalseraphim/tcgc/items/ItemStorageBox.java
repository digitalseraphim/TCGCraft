package digitalseraphim.tcgc.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import digitalseraphim.tcgc.TCGCraft;

public class ItemStorageBox extends Item {
	
	public ItemStorageBox() {
		super();
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5,
				par6, par7, par8, par9, par10);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		int count = ItemCard.getCardCount(itemStack);
		return String.format("Deck Box (%d cards)\n", count);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		if(itemStack.getTagCompound().getBoolean("actAsContainer")){
			return new ItemStack(TCGCraft.proxy.storageBoxItem);
		}
		return null;
	}

}
