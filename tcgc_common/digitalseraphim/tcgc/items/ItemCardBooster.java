package digitalseraphim.tcgc.items;

import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import digitalseraphim.tcgc.TCGCraft;
import digitalseraphim.tcgc.core.logic.Card;
import digitalseraphim.tcgc.core.logic.CardInstance;

public class ItemCardBooster extends Item {
	private int totalNumCards = 15;
	private int[] numCards = new int[]{8,4,2,1};
	
	public ItemCardBooster(int id) {
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

		CardInstance[] cards = new CardInstance[totalNumCards];
		int k = 0;
		Random r = new Random();
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < numCards[i]; j++){
				cards[k++] = new CardInstance(Card.getRandomCardByRarity(r, EnumRarity.values()[i]));
			}
		}
		
		itemStack.stackSize--;
		
		EntityItem entityItem = player.dropPlayerItem(ItemCard.createItemStack(TCGCraft.proxy.cardItem, cards));
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
		packIcon = iconRegister.registerIcon("tcgc:card_pack"); 
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return packIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected String getIconString() {
		return "card_pack";
	}
	
	@Override
	public String getItemDisplayName(ItemStack par1ItemStack) {
		return "Booster Pack";
	}
	
}
