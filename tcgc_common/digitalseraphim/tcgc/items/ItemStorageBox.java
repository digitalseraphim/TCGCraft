package digitalseraphim.tcgc.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import digitalseraphim.tcgc.TCGCraft;
import digitalseraphim.tcgc.core.logic.Card;

public class ItemStorageBox extends Item {
	//can potentially represent a number of cards
	public static final String NBT_SELECTED = "selected";
	public static final String NBT_COLLAPSED = "collapsed";
	public static final String NBT_COUNT = "count";
	public static final String NBT_CARD_BASE = "card";
	
	
	public ItemStorageBox(int id) {
		super(id);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer player) {
		System.out.println("ItemCard.onItemRightClick()");
		
		if(player.isSneaking()){
			NBTTagCompound tag = itemStack.getTagCompound();
			boolean collapsed = tag.getBoolean("collapsed");
			tag.setBoolean("collapsed", !collapsed);
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
			boolean collapsed = tag.getBoolean("collapsed");
			tag.setBoolean("collapsed", !collapsed);
			return true;
		}
		
		return super.onItemUseFirst(itemStack, player, world, x, y, z, side, hitX, hitY,
				hitZ);
	}
	
//	@Override
//	public boolean onItemUse(ItemStack par1ItemStack,
//			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
//			int par6, int par7, float par8, float par9, float par10) {
//		System.out.println("ItemCard.onItemUse()");
//		return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5,
//				par6, par7, par8, par9, par10);
//	}
	
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
	
	@Override
	public String getItemDisplayName(ItemStack itemStack) {
		NBTTagCompound tag = itemStack.getTagCompound();
		int sel = tag.getInteger("selected");
		String name = tag.getString("card"+sel);
		Card c = Card.getAllCards().get(name);
		
		return c.getFullName();
	}

	public static Card getCard(ItemStack is, int i){
		NBTTagCompound tag = is.getTagCompound();
		String name = tag.getString("card"+i);
		return Card.getAllCards().get(name);
	}
	
	public static Card getSelectedCard(ItemStack is){
		NBTTagCompound tag = is.getTagCompound();
		int sel = tag.getInteger("selected");
		return getCard(is, sel);
	}
	
	public static int getSelectedCardIndex(ItemStack is){
		NBTTagCompound tag = is.getTagCompound();
		return tag.getInteger("selected");
	}
	
	public static void scrollSelected(ItemStack is, int amnt){
		NBTTagCompound tag = is.getTagCompound();
		int sel = tag.getInteger("selected");
		int count = tag.getInteger("count");
	
		sel += amnt;
		
		while(sel < 0){
			sel += count;
		}
		
		sel = sel % count;
		
		tag.setInteger("selected", sel);
	}
	
}
