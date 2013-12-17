package digitalseraphim.tcgc.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import digitalseraphim.tcgc.TCGCraft;

public class ItemStarterDeck extends Item {
	
	public ItemStarterDeck(int id) {
		super(id);
		setCreativeTab(TCGCraft.tabsTCGC);
		setTextureName("tcgc:card_back");
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
		return 1;
	}

	public static ItemStack createItemStack(){
		return new ItemStack(TCGCraft.proxy.starterDeck, 1);
	}
	
	@Override
	public String getItemDisplayName(ItemStack par1ItemStack) {
		setTextureName("starter_deck");
		return "Starter Deck (Right click to open)";
	}
	
}
