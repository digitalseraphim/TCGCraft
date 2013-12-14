package digitalseraphim.tcgc.items;

import java.util.Arrays;
import java.util.Vector;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import digitalseraphim.tcgc.TCGCraft;
import digitalseraphim.tcgc.core.logic.Card;

public class ItemCard extends ItemMap {
	//can potentially represent a number of cards
	public static final String NBT_CARDS_ROOT = "cards";
	public static final String NBT_SELECTED = "selected";
	public static final String NBT_COLLAPSED = "collapsed";
	public static final String NBT_COUNT = "count";
	public static final String NBT_CARD_BASE = "card";
	
	public ItemCard(int id) {
		super(id);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer player) {
		if(player.isSneaking()){
			toggleCollapsed(itemStack);
			return itemStack;
		}
		
		return super.onItemRightClick(itemStack, world, player);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		if(player.isSneaking()){
			toggleCollapsed(itemStack);
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
		
		if(ItemCard.getCardCount(item) == 1){
			return super.onDroppedByPlayer(item, player);
		}else{
			Vector<Card> cards = new Vector<>(Arrays.asList(cardsFromItemStack(item))); 
			Card drop = cards.remove(getSelectedCardIndex(item));
			System.out.println("dropping " + drop.getFullName());
			ItemStack newIS = createItemStack(TCGCraft.proxy.cardItem, cards.toArray(new Card[0]));
			item.setTagCompound(newIS.getTagCompound());
			
			player.dropPlayerItem(createItemStack(TCGCraft.proxy.cardItem, new Card[]{drop}));

			return false;
		}
	}
	
	public static ItemStack createItemStack(Item item, Card[] cards){
		ItemStack is = new ItemStack(item);
		NBTTagCompound tagCompound = new NBTTagCompound(NBT_CARDS_ROOT);
		
		tagCompound.setInteger(NBT_COUNT, cards.length);
		tagCompound.setInteger(NBT_SELECTED, 0);
		for(int i = 0; i < cards.length; i++){
			tagCompound.setString(NBT_CARD_BASE+i, cards[i].getFullName());
		}
		
		is.setTagCompound(tagCompound);
		
		return is;
	}

	public static Card[] cardsFromItemStack(ItemStack is){
		NBTTagCompound tagCompound = is.getTagCompound();
		int count = tagCompound.getInteger(NBT_COUNT);
		Card[] cards = new Card[count];
		
		for(int i = 0; i < count; i++){
			cards[i] = Card.getAllCards().get(tagCompound.getString(NBT_CARD_BASE+i));
		}
		
		return cards;
	}
	
	@Override
	public String getItemDisplayName(ItemStack itemStack) {
		Card c = getSelectedCard(itemStack);
		return String.format("%s:%s (%d others)", c.getName(), c.getType().name(), getCardCount(itemStack));
	}

	public static Card getCard(ItemStack is, int i){
		NBTTagCompound tag = is.getTagCompound();
		String name = tag.getString(NBT_CARD_BASE+i);
		return Card.getAllCards().get(name);
	}
	
	public static Card getSelectedCard(ItemStack is){
		NBTTagCompound tag = is.getTagCompound();
		int sel = tag.getInteger(NBT_SELECTED);
		return getCard(is, sel);
	}
	
	public static int getSelectedCardIndex(ItemStack is){
		NBTTagCompound tag = is.getTagCompound();
		return tag.getInteger(NBT_SELECTED);
	}
	
	public static int getCardCount(ItemStack is){
		NBTTagCompound tag = is.getTagCompound();
		return tag.getInteger(NBT_COUNT);
	}
	
	public static void toggleCollapsed(ItemStack is){
		NBTTagCompound tag = is.getTagCompound();
		boolean collapsed = tag.getBoolean(NBT_COLLAPSED);
		tag.setBoolean(NBT_COLLAPSED, !collapsed);
	}
	
	public static void scrollSelected(ItemStack is, int amnt){
		NBTTagCompound tag = is.getTagCompound();
		int sel = tag.getInteger(NBT_SELECTED);
		int count = tag.getInteger(NBT_COUNT);
	
		sel += amnt;
		
		while(sel < 0){
			sel += count;
		}
		
		sel = sel % count;
		
		tag.setInteger(NBT_SELECTED, sel);
	}
	

}
