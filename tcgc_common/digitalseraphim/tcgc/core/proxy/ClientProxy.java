package digitalseraphim.tcgc.core.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import digitalseraphim.tcgc.client.render.items.IIRCard;
import digitalseraphim.tcgc.core.helpers.ItemIds;


public class ClientProxy extends CommonProxy {
	
	public void initTileEntities() {
		super.initTileEntities();
//		ClientRegistry.bindTileEntitySpecialRenderer(TECardTable.class,
//				new TESRCardTable());
		
	}
	
	@Override
	public void initRenderingAndTextures() {
		super.initRenderingAndTextures();
		System.out.println("registering at id " + (ItemIds.CARD_ID+256));
		MinecraftForgeClient.registerItemRenderer(ItemIds.CARD_ID+256, new IIRCard());
	}
	
}
