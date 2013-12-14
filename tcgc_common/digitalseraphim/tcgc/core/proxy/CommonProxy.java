package digitalseraphim.tcgc.core.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import digitalseraphim.tcgc.core.helpers.ItemIds;
import digitalseraphim.tcgc.items.ItemCard;
import digitalseraphim.tcgc.items.ItemCardBooster;

public class CommonProxy {
	public ItemCard cardItem = null;
	public ItemCardBooster boosterItem = null;
	
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
	}
}
