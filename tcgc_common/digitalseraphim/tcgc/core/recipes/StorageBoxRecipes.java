package digitalseraphim.tcgc.core.recipes;

import digitalseraphim.tcgc.items.ItemCard;
import digitalseraphim.tcgc.items.ItemStorageBox;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class StorageBoxRecipes implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world) {
		for(int row = 0; row < 3; row++){
			
		}
		
		return false;
	}
	
	private boolean isStorageBox(ItemStack is){
		return is.getItem() instanceof ItemStorageBox;
	}

	private boolean isCardStack(ItemStack is){
		return is.getItem() instanceof ItemCard;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		
		return null;
	}

	@Override
	public int getRecipeSize() {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return null;
	}

}
