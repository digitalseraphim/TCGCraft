package digitalseraphim.tcgc.core.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import digitalseraphim.tcgc.client.mouse.MouseHandler;
import digitalseraphim.tcgc.core.events.PlayerTracker;
import digitalseraphim.tcgc.core.recipes.CardStackRecipes;
import digitalseraphim.tcgc.core.recipes.StorageBoxRecipes;
import digitalseraphim.tcgc.items.ItemCard;
import digitalseraphim.tcgc.items.ItemCardBooster;
import digitalseraphim.tcgc.items.ItemStarterDeck;
import digitalseraphim.tcgc.items.ItemStorageBox;

public class CommonProxy {
	public ItemCard cardItem = null;
	public ItemCardBooster boosterItem = null;
	public ItemStorageBox storageBoxItem = null;
	public ItemStarterDeck starterDeck = null;
	
	public void initTileEntities() {

	}

	public void initRenderingAndTextures() {
		
	}

	public void initEntities() {
	
	}

	public void initEventHandlers(){
		FMLCommonHandler.instance().bus().register(new PlayerTracker());
	}
	
	public void initItems() {
		GameRegistry.registerItem(cardItem = new ItemCard(), "Card");
		GameRegistry.registerItem(boosterItem = new ItemCardBooster(), "CardBooster");
		GameRegistry.registerItem(storageBoxItem = new ItemStorageBox(), "StorageBox");
		GameRegistry.registerItem(starterDeck = new ItemStarterDeck(), "StarterDeck");
	}
	
	public void initRecipes(){
		GameRegistry.addRecipe(new StorageBoxRecipes());
		GameRegistry.addRecipe(new CardStackRecipes());
		GameRegistry.addShapedRecipe(new ItemStack(storageBoxItem), "xxx","x x","xxx", 'x', Item.itemRegistry.getObject("paper"));
	}


	public static void sendPlayerMessage(String[] msg) {
		for (String s : msg) {
			Minecraft.getMinecraft().thePlayer.sendChatMessage(s);
		}
	}

}
