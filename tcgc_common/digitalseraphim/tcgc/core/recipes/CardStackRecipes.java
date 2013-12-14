package digitalseraphim.tcgc.core.recipes;

import java.util.Arrays;
import java.util.Vector;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import digitalseraphim.tcgc.TCGCraft;
import digitalseraphim.tcgc.core.logic.CardInstance;
import digitalseraphim.tcgc.items.ItemCard;

public class CardStackRecipes implements IRecipe {

	ItemStack output = new ItemStack(TCGCraft.proxy.storageBoxItem);
	
	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world) {
		int stacksFound = 0;
		
		for(int row = 0; row < 3; row++){
			for(int col = 0; col < 3; col++){
				ItemStack is = inventorycrafting.getStackInRowAndColumn(row, col);
				if(is != null){
					if(!isCardStack(is)){
						return false;
					}
					stacksFound++;
				}
			}
		}
		
		return stacksFound > 1;
	}
	
	private boolean isCardStack(ItemStack is){
		return is.getItem() instanceof ItemCard;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		Vector<CardInstance> cards = new Vector<>();

		for(int row = 0; row < 3; row++){
			for(int col = 0; col < 3; col++){
				ItemStack is = inventorycrafting.getStackInRowAndColumn(row, col);
				
				if(is == null){
					continue;
				}
				
				cards.addAll(Arrays.asList(ItemCard.cardsFromItemStack(is)));
			}
		}
		
		return ItemCard.createItemStack(TCGCraft.proxy.cardItem, cards.toArray(new CardInstance[0]));
	}

	@Override
	public int getRecipeSize() {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

}
