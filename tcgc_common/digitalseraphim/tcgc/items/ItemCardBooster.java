package digitalseraphim.tcgc.items;

import java.util.Random;

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
import digitalseraphim.tcgc.core.logic.Card;
import digitalseraphim.tcgc.core.logic.CardInstance;

public class ItemCardBooster extends Item {
	private int numCards = 15;

	private int manaProb = 10;
	private int spellProb = 10;
	private int selfModProb = 10;
	private int cardModProb = 10;
	private int summonProb = 10;
	
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

		CardInstance[] cards = new CardInstance[numCards];
		Random r = new Random();
		
		for(int i = 0; i < numCards; i++){
			int n = r.nextInt(getTotalProb());
			
			if(n < manaProb){
				cards[i] = new CardInstance(Card.getRandomManaCard(r));
				continue;
			}
			n -= manaProb;
			if(n < spellProb){
				cards[i] = new CardInstance(Card.getRandomSpellCard(r));
				continue;
			}
			n -= spellProb;
			if(n < selfModProb){
				cards[i] = new CardInstance(Card.getRandomSelfModCard(r));
				continue;
			}
			n -= selfModProb;
			if(n < cardModProb){
				cards[i] = new CardInstance(Card.getRandomCardModCard(r));
				continue;
			}
			cards[i] = new CardInstance(Card.getRandomSummonCard(r));
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

	private int getTotalProb(){
		return manaProb + spellProb + selfModProb + cardModProb + summonProb;
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
