package digitalseraphim.tcgc.core.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import digitalseraphim.tcgc.client.render.TESRCardTable;
import digitalseraphim.tcgc.tile.TECardTable;

public class ClientProxy extends CommonProxy {
	
	public void initTileEntities() {
		super.initTileEntities();
		ClientRegistry.bindTileEntitySpecialRenderer(TECardTable.class,
				new TESRCardTable());
		
	}
	
}
