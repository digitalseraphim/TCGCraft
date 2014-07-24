package digitalseraphim.tcgc.core.proxy;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import digitalseraphim.tcgc.client.mouse.MouseHandler;
import digitalseraphim.tcgc.client.render.items.IIRCard;
import digitalseraphim.tcgc.core.events.PlayerTracker;
import digitalseraphim.tcgc.core.helpers.Strings;
import digitalseraphim.tcgc.core.network.CardSelectionMessage;
import digitalseraphim.tcgc.core.network.PacketHandler;

public class ClientProxy extends CommonProxy {

	public void initTileEntities() {
		super.initTileEntities();
	}

	@Override
	public void initRenderingAndTextures() {
		super.initRenderingAndTextures();
		MinecraftForgeClient.registerItemRenderer(cardItem, new IIRCard());
	}

	@Override
	public void initEventHandlers() {
		super.initEventHandlers();
		MinecraftForge.EVENT_BUS.register(new MouseHandler());
	}

	public void sendCardSelectionPacket(int sel) {
		CardSelectionMessage csm = new CardSelectionMessage(sel);
		PacketHandler.INSTANCE.sendToServer(csm);
	}
	
}
