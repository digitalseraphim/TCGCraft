package digitalseraphim.tcgc.core.network;

import ibxm.Player;

import java.nio.ByteBuffer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import digitalseraphim.tcgc.core.helpers.Strings;
import digitalseraphim.tcgc.items.ItemCard;

public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Strings.MOD_ID.toLowerCase());
	
	public static void init(){
		INSTANCE.registerMessage(CardSelectionMessage.class, CardSelectionMessage.class, 0, Side.SERVER);
	}
	
}
