package digitalseraphim.tcgc.items;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import digitalseraphim.tcgc.TCGCraft;
import digitalseraphim.tcgc.core.logic.Card;
import digitalseraphim.tcgc.core.logic.CardInstance;

public class ItemCardBooster extends Item {
	private int totalNumCards = 15;
	private int[] numCards = new int[]{8,4,2,1};
	
	public ItemCardBooster() {
		super();
		setCreativeTab(TCGCraft.tabsTCGC);
		setTextureName("tcgc:card_pack");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer player) {
		itemStack.stackSize --;
		if(world.isRemote){
			return itemStack;
		}

		CardInstance[] cards = new CardInstance[totalNumCards];
		int k = 0;
		Random r = new Random();
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < numCards[i]; j++){
				cards[k++] = new CardInstance(Card.getRandomCardByRarity(r, EnumRarity.values()[i]));
			}
		}
		
		EntityItem entityItem = player.dropPlayerItemWithRandomChoice(ItemCard.createItemStack(TCGCraft.proxy.cardItem, cards), false);
        entityItem.delayBeforeCanPickup = 0;

        return itemStack;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 16;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return "Booster Pack (Right click to open)";
	}
	
}
