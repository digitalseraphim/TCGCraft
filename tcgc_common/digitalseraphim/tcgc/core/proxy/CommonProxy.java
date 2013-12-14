package digitalseraphim.tcgc.core.proxy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import digitalseraphim.tcgc.core.helpers.ItemIds;
import digitalseraphim.tcgc.core.recipes.StorageBoxRecipes;
import digitalseraphim.tcgc.items.ItemCard;
import digitalseraphim.tcgc.items.ItemCardBooster;
import digitalseraphim.tcgc.items.ItemStorageBox;

public class CommonProxy {
	public ItemCard cardItem = null;
	public ItemCardBooster boosterItem = null;
	public ItemStorageBox storageBoxItem = null;
	
	public void initTileEntities() {
		//GameRegistry.registerTileEntity(TECardTable.class, TECardTable.NAME);
	}

	public void initRenderingAndTextures() {
		
	}

	public void initEntities() {
	
	}

	public void initEventHandlers(){
		
	}
	
	public void initItems() {
		GameRegistry.registerItem(cardItem = new ItemCard(ItemIds.CARD_ID), "Card");
		GameRegistry.registerItem(boosterItem = new ItemCardBooster(ItemIds.BOOSTER_ID), "CardBooster");
		GameRegistry.registerItem(storageBoxItem = new ItemStorageBox(ItemIds.STORAGE_BOX_ID), "StorageBox");
	}
	
	public void initRecipes(){
		GameRegistry.addRecipe(new StorageBoxRecipes());
		GameRegistry.addShapedRecipe(new ItemStack(storageBoxItem), "xxx","x x","xxx", 'x', Item.paper);
	}
}
