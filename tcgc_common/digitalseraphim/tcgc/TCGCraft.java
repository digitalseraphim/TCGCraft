package digitalseraphim.tcgc;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import digitalseraphim.tcgc.blocks.ModBlocks;
import digitalseraphim.tcgc.core.commands.TCGCCommand;
import digitalseraphim.tcgc.core.events.PlayerTracker;
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

	private PlayerTracker playerTracker;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		playerTracker = new PlayerTracker();
		GameRegistry.registerPlayerTracker(playerTracker);
		MinecraftForge.EVENT_BUS.register(playerTracker);

		proxy.initItems();
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(proxy.boosterItem.itemID, 1, 1, 2, 5));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(proxy.boosterItem.itemID, 1, 1, 2, 5));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(proxy.boosterItem.itemID, 1, 1, 2, 5));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(proxy.boosterItem.itemID, 1, 1, 2, 5));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(proxy.boosterItem.itemID, 1, 1, 2, 5));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(proxy.boosterItem.itemID, 1, 1, 2, 5));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(proxy.boosterItem.itemID, 1, 1, 2, 5));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(proxy.boosterItem.itemID, 1, 1, 2, 5));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(proxy.boosterItem.itemID, 1, 1, 2, 5));
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		ModBlocks.init();
		proxy.initTileEntities();
		proxy.initEventHandlers();
		proxy.initRenderingAndTextures();
		proxy.initEntities();
		proxy.initRecipes();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent evt) {
		System.out.println("REGISTERING COMMAND!!!!");
		evt.registerServerCommand(new TCGCCommand());
	}
}
