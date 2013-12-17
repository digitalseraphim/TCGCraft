package digitalseraphim.tcgc;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
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
import digitalseraphim.tcgc.core.helpers.BlockIDs;
import digitalseraphim.tcgc.core.helpers.ItemIds;
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
	
	public static Configuration config;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		config = new Configuration(new File(event.getModConfigurationDirectory(), "tcgcraft.cfg"));
		
		try{
			config.load();
			BlockIDs.CARD_TABLE_ID = config.get("blocks", "card_table", BlockIDs.DEFAULT_CARD_TABLE_ID).getInt();
			BlockIDs.MAGIC_FLOWING_WATER_ID = config.get("blocks", "magic_flowing_water", BlockIDs.DEFAULT_MAGIC_FLOWING_WATER_ID).getInt();
		
			ItemIds.BOOSTER_ID = config.get("items", "booster", ItemIds.DEFAULT_BOOSTER_ID).getInt();
			ItemIds.CARD_ID = config.get("items", "card", ItemIds.DEFAULT_CARD_ID).getInt();
			ItemIds.STARTER_DECK_ID = config.get("items", "starter_deck", ItemIds.DEFAULT_STARTER_DECK_ID).getInt();
			ItemIds.STORAGE_BOX_ID = config.get("items", "storage_box", ItemIds.DEFAULT_STORAGE_BOX_ID).getInt();
		}finally {
			if (config.hasChanged()) {
				config.save();
			}
		}
		
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
