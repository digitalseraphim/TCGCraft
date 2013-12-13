package digitalseraphim.tcgc.core.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import digitalseraphim.tcgc.tile.TECardTable;

public class CommonProxy {
	public void initTileEntities() {
		GameRegistry.registerTileEntity(TECardTable.class, TECardTable.NAME);
	}

	public void initRenderingAndTextures() {
	}

	public void initEntities() {
	}

	public void initItems() {
	}
}
