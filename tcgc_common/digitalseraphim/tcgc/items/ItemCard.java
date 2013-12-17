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
import digitalseraphim.tcgc.core.helpers.XPUtils;
import digitalseraphim.tcgc.core.logic.Card.ModifierCard;
import digitalseraphim.tcgc.core.logic.Card.SummonCard;
import digitalseraphim.tcgc.core.logic.Card.Type;
import digitalseraphim.tcgc.core.logic.CardInstance;
import digitalseraphim.tcgc.core.proxy.CommonProxy;

public class ItemCard extends ItemMap {
	// can potentially represent a number of cards
	public static final String NBT_CARDS_ROOT = "cards";
	public static final String NBT_SELECTED = "selected";
	public static final String NBT_COLLAPSED = "collapsed";
	public static final String NBT_COUNT = "count";
	public static final String NBT_CARD_BASE = "card";

	public ItemCard(int id) {
		super(id);
	}

	private static void sendMessage(String s){
		CommonProxy.sendPlayerMessage(new String[]{s});
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		System.out.println("onIRC");
		if (player.worldObj.isRemote) {
			return itemStack;
		}
		if (player.isSneaking()) {
			toggleCollapsed(itemStack);
			return itemStack;
		} else {
			int sel = getSelectedCardIndex(itemStack);
			CardInstance[] cards = cardsFromItemStack(itemStack);
			CardInstance cardSel = cards[sel];

			if (cardSel.isActivated()) {
				if(!cardSel.needsTargetBlock()){
					if (cardSel.isActivated()) {
						// cast!
						cardSel.cast(player,0,0,0);
						System.out.println("done casting... ");
						// do good stuff here...
						cardSel.setUsed(true);
						cardSel.setActivated(false);

						for (CardInstance c : cards) {
							c.setLocked(false);
						}
						ItemStack tempItemStack =  ItemCard.createItemStack(TCGCraft.proxy.cardItem, cards);
						itemStack.setTagCompound(tempItemStack.getTagCompound());
						return itemStack;
					}
				}
			} else if(cardSel.isUsed()){
				int restoreXP = cardSel.getBaseCard().getRestoreXPCost();
				int playerXP = player.experienceTotal;
				boolean changed = false;
				
				if(player.capabilities.isCreativeMode){
					System.out.println("creative card unlock");
					cardSel.setUsed(false);
					changed = true;
				}else if(playerXP >= restoreXP && !cardSel.isLocked()){
					XPUtils.subtractXP(player, restoreXP);
					cardSel.setUsed(false);
					changed = true;
				}else{
					if(playerXP < restoreXP){
						sendMessage("Not enough XP to restore");
					}else{
						sendMessage("Card is locked");
					}
				}
				if(changed){
					ItemStack tmpItemStack = ItemCard.createItemStack(TCGCraft.proxy.cardItem, cards);
					ItemCard.setSelected(tmpItemStack, ItemCard.getSelectedCardIndex(itemStack));
					ItemCard.setCollapsed(tmpItemStack, ItemCard.getCollapsed(itemStack));
					return tmpItemStack;
				}else{
					return itemStack;
				}
			} else {
				int toModIdx = -1;
				CardInstance toMod = null;
				Type t = cardSel.getBaseCard().getType();
				int[] totalMana = new int[] { 0, 0, 0, 0, 0, 0 };
				int[] castCost = cardSel.getBaseCard().getCost();
				int xpCost = 0;
				int playerXP = player.experienceTotal;

				if (t == Type.MANA) {
					sendMessage("Cannot cast mana");
					return super.onItemRightClick(itemStack, world, player);
				}

				if (t == Type.MODIFIER) {
					// look for card to modify
					System.out.println("activated item is card mod");

					for (int i = 0; i < cards.length; i++) {
						CardInstance c = cards[i];
						Type ct = c.getBaseCard().getType();
						if(i == sel || ct == Type.MANA){
							continue;
						}
						if(ct == Type.SUMMON && c.isActivated()){
							if(toMod != null){
								sendMessage("too many activated summon cards");
								return super.onItemRightClick(itemStack, world, player);
							}
							toModIdx = i;
							toMod = c;
						}else if(ct == Type.SPELL){
							//no spells in here!
							sendMessage("Found a spell in this hand");
							return super.onItemRightClick(itemStack, world, player);
						}else if(ct==Type.MODIFIER && !c.isActivated()){
							//more than one un-activated modifier
							sendMessage("Too many unactivated modifiers");
							return super.onItemRightClick(itemStack, world, player);
						}
					}
				}

				xpCost = cardSel.getBaseCard().getUseXPCost();
				

				for (int i = 0; i < cards.length; i++) {
					if (i == sel || i == toModIdx) {
						continue;
					}

					CardInstance card = cards[i];

					if (!card.isActivated() && card.getBaseCard().getType() != Type.MANA) {
						sendMessage("???");
						return super.onItemRightClick(itemStack, world, player);
					}

					if (card.getBaseCard().getType() == Type.MANA && !card.isUsed()) {
						xpCost += card.getBaseCard().getUseXPCost();

						card.getBaseCard().addCost(totalMana);
					}
				}

				System.out.println("xpCost = " + xpCost);
				System.out.println("playerXP = " + playerXP);
				if (!player.capabilities.isCreativeMode && xpCost > playerXP) {
					// player doesn't have enough xp
					sendMessage("Not enough XP");
					return super.onItemRightClick(itemStack, world, player);
				}
				System.out.println("Total mana: " + Arrays.toString(totalMana));
				System.out.println("cost      : " + Arrays.toString(castCost));

				// check if enough to cast
				boolean canCast = true;
				for (int i = 0; i < castCost.length; i++) {
					if (totalMana[i] < castCost[i]) {
						System.out.println("cant cast at " + i);
						sendMessage("Not enough mana");
						canCast = false;
						break;
					}
				}

				if (canCast) {
					if(t == Type.MODIFIER){
						if(toMod != null){
							ModifierCard modCard = (ModifierCard)cardSel.getBaseCard();
							SummonCard toModCard = (SummonCard)toMod.getBaseCard();
							toModCard.addModCard(modCard);
							cardSel.setUsed(true);
							cardSel.setLocked(true);
						}
					}
					
					for (int i = 0; i < cards.length; i++) {
						CardInstance card = cards[i];
						if (card.getBaseCard().getType() == Type.MANA) {
							card.setUsed(true);
							card.setLocked(true);
						} else if (t == Type.MODIFIER && card == cardSel){
							System.out.println("selected modifier");
							if(!card.isUsed()){
								System.out.println("not marked used, marking active");
								card.setActivated(true);
							}
						} else {
							card.setActivated(true);
							card.setLocked(true);
						}
					}
					itemStack = ItemCard.createItemStack(TCGCraft.proxy.cardItem, cards);
					XPUtils.subtractXP(player, xpCost);
				}
			}
		}

		return itemStack;
	}

	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ) {
		if (player.isSneaking()) {
			toggleCollapsed(itemStack);
			return true;
		}

		return super.onItemUseFirst(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ) {
		System.out.println("ItemCard.onItemUse()");
		int sel = getSelectedCardIndex(itemStack);
		CardInstance[] cards = cardsFromItemStack(itemStack);
		CardInstance cardSel = cards[sel];

		if (cardSel.isActivated()) {
			// cast!
			cardSel.cast(player, x, y, z);
			System.out.println("done casting... ");
			// do good stuff here...
			cardSel.setUsed(true);
			cardSel.setActivated(false);

			for (CardInstance c : cards) {
				c.setLocked(false);
			}
			ItemStack tempItemStack =  ItemCard.createItemStack(TCGCraft.proxy.cardItem, cards);
			itemStack.setTagCompound(tempItemStack.getTagCompound());
			return true;
		}

		return super.onItemUse(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		System.out.println("ItemCard.onDroppedByPlayer()");

		if (ItemCard.getCardCount(item) == 1) {
			return super.onDroppedByPlayer(item, player);
		} else {
			Vector<CardInstance> cards = new Vector<CardInstance>(Arrays.asList(cardsFromItemStack(item)));
			int sel = getSelectedCardIndex(item);
			CardInstance selCard = getSelectedCard(item);
			if (selCard.isLocked()) {
				return false;
			}

			CardInstance drop = cards.remove(sel);
			boolean collapsed = getCollapsed(item);

			System.out.println("dropping " + drop.getBaseCard().getFullName());
			ItemStack newIS = createItemStack(TCGCraft.proxy.cardItem, cards.toArray(new CardInstance[0]));
			item.setTagCompound(newIS.getTagCompound());

			int count = getCardCount(item);

			setSelected(item, Math.max(sel - 1, sel % count));
			setCollapsed(item, collapsed);
			player.dropPlayerItem(createItemStack(TCGCraft.proxy.cardItem, new CardInstance[] { drop }));

			return false;
		}
	}

	public static ItemStack createItemStack(Item item, CardInstance[] cards) {
		ItemStack is = new ItemStack(item);
		NBTTagCompound tagCompound = new NBTTagCompound(NBT_CARDS_ROOT);

		tagCompound.setInteger(NBT_COUNT, cards.length);
		tagCompound.setInteger(NBT_SELECTED, 0);
		for (int i = 0; i < cards.length; i++) {
			tagCompound.setCompoundTag(NBT_CARD_BASE + i, cards[i].toTagCompound());
		}

		is.setTagCompound(tagCompound);

		return is;
	}

	public static CardInstance[] cardsFromItemStack(ItemStack is) {
		NBTTagCompound root = is.getTagCompound();
		int count = root.getInteger(NBT_COUNT);
		CardInstance[] cards = new CardInstance[count];

		for (int i = 0; i < count; i++) {
			NBTTagCompound card = root.getCompoundTag(NBT_CARD_BASE + i);
			cards[i] = CardInstance.fromNBT(card);
			if(cards[i] == null){
				i--;
				count--;
				root.setInteger(NBT_COUNT, count);
			}
		}

		return cards;
	}

	@Override
	public String getItemDisplayName(ItemStack itemStack) {
		CardInstance c = getSelectedCard(itemStack);
		int count = getCardCount(itemStack);
		if (count > 1) {
			return String.format("%s:%s%s (%d others)", c.getBaseCard().getName(), c.getBaseCard().getType().getName(),
					c.isActivated() ? " (Active)" : (c.isUsed() ? (c.isLocked() ? " (Used&Locked)" : "(Used)") : ""),
					count - 1);
		}
		return String.format("%s:%s%s", c.getBaseCard().getName(), c.getBaseCard().getType().getName(),
				c.isActivated() ? " (Active)" : (c.isUsed() ? (c.isLocked() ? " (Used&Locked)" : "(Used)") : ""));
	}

	public static CardInstance getCard(ItemStack is, int i) {
		NBTTagCompound tag = is.getTagCompound();
		NBTTagCompound cardNBT = tag.getCompoundTag(NBT_CARD_BASE + i);
		CardInstance ci = CardInstance.fromNBT(cardNBT);
		return ci.getBaseCard()==null?null:ci;
	}

	public static CardInstance getSelectedCard(ItemStack is) {
		NBTTagCompound tag = is.getTagCompound();
		int sel = tag.getInteger(NBT_SELECTED);
		return getCard(is, sel);
	}

	public static int getSelectedCardIndex(ItemStack is) {
		NBTTagCompound tag = is.getTagCompound();
		return tag.getInteger(NBT_SELECTED);
	}

	public static int getCardCount(ItemStack is) {
		NBTTagCompound tag = is.getTagCompound();
		return tag.getInteger(NBT_COUNT);
	}

	public static boolean getCollapsed(ItemStack is) {
		NBTTagCompound tag = is.getTagCompound();
		return tag.getBoolean(NBT_COLLAPSED);
	}

	public static void setCollapsed(ItemStack is, boolean collapsed) {
		NBTTagCompound tag = is.getTagCompound();
		tag.setBoolean(NBT_COLLAPSED, collapsed);
	}

	public static void toggleCollapsed(ItemStack is) {
		NBTTagCompound tag = is.getTagCompound();
		tag.setBoolean(NBT_COLLAPSED, !tag.getBoolean(NBT_COLLAPSED));
	}

	public static void setSelected(ItemStack is, int sel) {
		NBTTagCompound tag = is.getTagCompound();
		int count = tag.getInteger(NBT_COUNT);
		tag.setInteger(NBT_SELECTED, sel % count);
	}

	public static void scrollSelected(ItemStack is, int amnt) {
		NBTTagCompound tag = is.getTagCompound();
		int sel = tag.getInteger(NBT_SELECTED);
		int count = tag.getInteger(NBT_COUNT);

		sel += amnt;

		while (sel < 0) {
			sel += count;
		}

		sel = sel % count;

		tag.setInteger(NBT_SELECTED, sel);
	}

}
