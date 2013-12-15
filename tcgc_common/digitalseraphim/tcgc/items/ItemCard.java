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
import digitalseraphim.tcgc.core.logic.Card;
import digitalseraphim.tcgc.core.logic.Card.Type;
import digitalseraphim.tcgc.core.logic.CardInstance;

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

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
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
				// do nothing, this handled in onItemUse()
			} else {
				int toModIdx = -1;
				Card toMod = null;
				Type t = cardSel.getBaseCard().getType();
				int[] totalMana = new int[] { 0, 0, 0, 0, 0, 0 };
				int[] castCost = cardSel.getBaseCard().getCost();
				int xpCost = 0;
				int playerXP = player.experienceTotal;

				if (t == Type.MANA) {
					System.out.println("activated item is mana");
					return super.onItemRightClick(itemStack, world, player);
				}

				if (t == Type.CARD_MODIFIER) {
					// look for card to modify
					System.out.println("activated item is card mod");

				}

				xpCost = cardSel.getBaseCard().getUseXPCost();
				if (!player.capabilities.isCreativeMode && xpCost > playerXP) {
					// player doesn't have enough xp
					return super.onItemRightClick(itemStack, world, player);
				}

				for (int i = 0; i < cards.length; i++) {
					if (i == sel || i == toModIdx) {
						continue;
					}

					CardInstance card = cards[i];

					if (!card.isActivated() && card.getBaseCard().getType() != Type.MANA) {
						return super.onItemRightClick(itemStack, world, player);
					}

					if (card.getBaseCard().getType() == Type.MANA && !card.isUsed()) {
						xpCost += card.getBaseCard().getUseXPCost();

						card.getBaseCard().addCost(totalMana);
					}
				}

				if (!player.capabilities.isCreativeMode && xpCost > playerXP) {
					// player doesn't have enough xp
					return super.onItemRightClick(itemStack, world, player);
				}

				System.out.println("Total mana: " + Arrays.toString(totalMana));
				System.out.println("cost      : " + Arrays.toString(castCost));

				// check if enough to cast
				boolean canCast = true;
				for (int i = 0; i < castCost.length; i++) {
					if (totalMana[i] < castCost[i]) {
						System.out.println("cant cast at " + i);
						canCast = false;
						break;
					}
				}

				if (canCast) {
					for (int i = 0; i < cards.length; i++) {
						CardInstance card = cards[i];
						if (card.getBaseCard().getType() == Type.MANA) {
							card.setUsed(true);
							card.setLocked(true);
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
		// cast!

		// do good stuff here...
		int sel = getSelectedCardIndex(itemStack);
		CardInstance[] cards = cardsFromItemStack(itemStack);
		CardInstance cardSel = cards[sel];

		if (cardSel.isActivated()) {
			cardSel.setUsed(true);
			cardSel.setActivated(false);

			for (CardInstance c : cards) {
				c.setLocked(false);
			}

			itemStack = ItemCard.createItemStack(TCGCraft.proxy.cardItem, cards);
			return true;
		}

		return super.onItemUseFirst(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}

	// @Override
	// public boolean onItemUse(ItemStack par1ItemStack,
	// EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
	// int par6, int par7, float par8, float par9, float par10) {
	// System.out.println("ItemCard.onItemUse()");
	// return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4,
	// par5,
	// par6, par7, par8, par9, par10);
	// }

	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		System.out.println("ItemCard.onDroppedByPlayer()");

		if (ItemCard.getCardCount(item) == 1) {
			return super.onDroppedByPlayer(item, player);
		} else {
			Vector<CardInstance> cards = new Vector<>(Arrays.asList(cardsFromItemStack(item)));
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
		return CardInstance.fromNBT(cardNBT);
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
