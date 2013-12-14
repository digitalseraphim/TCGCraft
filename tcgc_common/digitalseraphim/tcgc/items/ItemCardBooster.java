package digitalseraphim.tcgc.items;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import digitalseraphim.tcgc.core.logic.Card;

public class ItemCardBooster extends ItemMap {
	private int numCards = 15;

	private int manaProb = 10;
	private int spellProb = 10;
	private int selfModProb = 10;
	private int cardModProb = 10;
	private int summonProb = 10;
	
	
	protected ItemCardBooster(int id) {
		super(id);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		if(par2World.isRemote){
			return null;
		}

		Card[] cards = new Card[numCards];
		Random r = new Random();
		
		for(int i = 0; i < numCards; i++){
			int n = r.nextInt(getTotalProb());
			
			if(n < manaProb){
				cards[i] = Card.getRandomManaCard(r);
				continue;
			}
			n -= manaProb;
			if(n < spellProb){
				cards[i] = Card.getRandomSpellCard(r);
				continue;
			}
			n -= spellProb;
			if(n < selfModProb){
				cards[i] = Card.getRandomSelfModCard(r);
				continue;
			}
			n -= selfModProb;
			if(n < cardModProb){
				cards[i] = Card.getRandomCardModCard(r);
				continue;
			}
			cards[i] = Card.getRandomSummonCard(r);
		}
		
		return null;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 16;
	}

	private int getTotalProb(){
		return manaProb + spellProb + selfModProb + cardModProb + summonProb;
	}
	
}
