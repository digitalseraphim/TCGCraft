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
import digitalseraphim.tcgc.items.ItemStorageBox;

public class StorageBoxRecipes implements IRecipe {

	ItemStack output = new ItemStack(TCGCraft.proxy.storageBoxItem);
	
	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world) {
		int stacksFound = 0;
		int boxesFound = 0;
		
		for(int row = 0; row < 3; row++){
			for(int col = 0; col < 3; col++){
				ItemStack is = inventorycrafting.getStackInRowAndColumn(row, col);
				if(is != null){
					if(!isStorageBox(is) && !isCardStack(is)){
						return false;
					}
					if(isStorageBox(is)){
						boxesFound++;
					}
					stacksFound++;
				}
			}
		}
		
		return boxesFound > 0 && stacksFound > 1;
	}
	
	private boolean isStorageBox(ItemStack is){
		return is.getItem() instanceof ItemStorageBox;
	}

	private boolean isCardStack(ItemStack is){
		return is.getItem() instanceof ItemCard;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		Vector<CardInstance> cards = new Vector<CardInstance>();
		boolean foundFirstBox = false;

		for(int row = 0; row < 3; row++){
			for(int col = 0; col < 3; col++){
				ItemStack is = inventorycrafting.getStackInRowAndColumn(row, col);
				
				if(is == null){
					continue;
				}
				
				if(isStorageBox(is)){
					if(!foundFirstBox){
						foundFirstBox = true;
					}else{
						is.getTagCompound().setBoolean("actAsContainer", true);
					}
				}
				
				cards.addAll(Arrays.asList(ItemCard.cardsFromItemStack(is)));
			}
		}
		
		return ItemCard.createItemStack(TCGCraft.proxy.storageBoxItem, cards.toArray(new CardInstance[0]));
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
