package digitalseraphim.tcgc.core.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import digitalseraphim.tcgc.core.helpers.ItemIds;
import digitalseraphim.tcgc.items.ItemCard;

public class CommonProxy {
	public void initTileEntities() {
		//GameRegistry.registerTileEntity(TECardTable.class, TECardTable.NAME);
	}

	public void initRenderingAndTextures() {
	}

	public void initEntities() {
	
	}

	public void initItems() {
		GameRegistry.registerItem(new ItemCard(ItemIds.CARD_ID), "Card");
	}
}
