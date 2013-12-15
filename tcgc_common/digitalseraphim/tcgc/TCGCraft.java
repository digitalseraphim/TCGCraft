package digitalseraphim.tcgc;

import net.minecraft.creativetab.CreativeTabs;

import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import digitalseraphim.tcgc.blocks.ModBlocks;
import digitalseraphim.tcgc.core.commands.CardCommand;
import digitalseraphim.tcgc.core.helpers.Strings;
import digitalseraphim.tcgc.core.network.PacketHandler;
import digitalseraphim.tcgc.core.proxy.CommonProxy;
import digitalseraphim.tcgc.creativetab.CreativeTabTCGC;

@Mod(modid = Strings.MOD_ID, name = Strings.MOD_NAME, version = "0.0.1")
@NetworkMod(channels = { Strings.MOD_ID }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class TCGCraft {

	@Instance(Strings.MOD_ID)
	public static TCGCraft instance;

	@SidedProxy(clientSide = "digitalseraphim.tcgc.core.proxy.ClientProxy", serverSide = "digitalseraphim.tcgc.core.proxy.ClientProxy")
	public static CommonProxy proxy;

	public static CreativeTabs tabsTCGC = new CreativeTabTCGC();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		ModBlocks.init();
		proxy.initTileEntities();
		proxy.initEventHandlers();
		proxy.initRenderingAndTextures();
		proxy.initEntities();
		proxy.initItems();
		proxy.initRecipes();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent evt) {
		System.out.println("REGISTERING COMMAND!!!!");
		evt.registerServerCommand(new CardCommand());
	}
}
