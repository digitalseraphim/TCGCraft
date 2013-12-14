package digitalseraphim.tcgc;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import digitalseraphim.tcgc.blocks.ModBlocks;
import digitalseraphim.tcgc.core.helpers.Strings;
import digitalseraphim.tcgc.core.proxy.CommonProxy;
import digitalseraphim.tcgc.creativetab.CreativeTabTCGC;

@Mod(modid=Strings.MOD_ID, name=Strings.MOD_NAME, version="0.0.1")
public class TCGCraft {

	@Instance(Strings.MOD_ID)
	public static TCGCraft instance;
	
	@SidedProxy(clientSide="digitalseraphim.tcgc.core.proxy.ClientProxy", serverSide="digitalseraphim.tcgc.core.proxy.ClientProxy")
	public static CommonProxy proxy;
	
	public static CreativeTabs tabsTCGC = new CreativeTabTCGC();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		ModBlocks.init();
		proxy.initTileEntities();
		proxy.initEventHandlers();
		proxy.initRenderingAndTextures();
		proxy.initEntities();
		proxy.initItems();
	}
	
}
