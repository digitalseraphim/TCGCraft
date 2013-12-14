package digitalseraphim.tcgc.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import digitalseraphim.tcgc.TCGCraft;
import digitalseraphim.tcgc.core.logic.Card;

public class ItemCard extends ItemMap {
	//can potentially represent a number of cards
	
	public ItemCard(int id) {
		super(id);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer player) {
		System.out.println("ItemCard.onItemRightClick()");
		
		if(player.isSneaking()){
			NBTTagCompound tag = itemStack.getTagCompound();
			int sel = tag.getInteger("selected");
			int count = tag.getInteger("count");
			sel = (sel+1)%count;
			tag.setInteger("selected", sel);
			return itemStack;
		}
		
		return super.onItemRightClick(itemStack, world, player);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		System.out.println("ItemCard.onItemUseFirst()");
		
		if(player.isSneaking()){
			NBTTagCompound tag = itemStack.getTagCompound();
			int sel = tag.getInteger("selected");
			int count = tag.getInteger("count");
			sel = (sel+1)%count;
			tag.setInteger("selected", sel);
			return true;
		}
		
		return super.onItemUseFirst(itemStack, player, world, x, y, z, side, hitX, hitY,
				hitZ);
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		System.out.println("ItemCard.onItemUse()");
		return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5,
				par6, par7, par8, par9, par10);
	}
	
	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		System.out.println("ItemCard.onDroppedByPlayer()");
		return super.onDroppedByPlayer(item, player);
	}
	
	public static ItemStack createItemStack(Card[] cards){
		ItemStack is = new ItemStack(TCGCraft.proxy.cardItem);
		NBTTagCompound tagCompound = new NBTTagCompound("cards");
		
		tagCompound.setInteger("count", cards.length);
		tagCompound.setInteger("selected", 0);
		for(int i = 0; i < cards.length; i++){
			tagCompound.setString("card"+i, cards[i].getFullName());
		}
		
		is.setTagCompound(tagCompound);
		
		return is;
	}

	public static Card[] cardsFromItemStack(ItemStack is){
		NBTTagCompound tagCompound = is.getTagCompound();
		int count = tagCompound.getInteger("count");
		Card[] cards = new Card[count];
		
		for(int i = 0; i < count; i++){
			cards[i] = Card.getAllCards().get(tagCompound.getString("card"+i));
		}
		
		return cards;
	}
	
}
