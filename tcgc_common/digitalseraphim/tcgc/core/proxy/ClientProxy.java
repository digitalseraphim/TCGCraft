package digitalseraphim.tcgc.core.proxy;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import digitalseraphim.tcgc.client.mouse.MouseHandler;
import digitalseraphim.tcgc.client.render.items.IIRCard;
import digitalseraphim.tcgc.core.helpers.ItemIds;
import digitalseraphim.tcgc.core.helpers.Strings;
import digitalseraphim.tcgc.core.network.PacketHandler;

public class ClientProxy extends CommonProxy {

	public void initTileEntities() {
		super.initTileEntities();
		// ClientRegistry.bindTileEntitySpecialRenderer(TECardTable.class,
		// new TESRCardTable());

	}

	@Override
	public void initRenderingAndTextures() {
		super.initRenderingAndTextures();
		System.out.println("registering at id " + (ItemIds.CARD_ID + 256));
		MinecraftForgeClient.registerItemRenderer(ItemIds.CARD_ID + 256, new IIRCard());
	}

	@Override
	public void initEventHandlers() {
		super.initEventHandlers();
		MinecraftForge.EVENT_BUS.register(new MouseHandler());
	}

	public void sendCardSelectionPacket(int sel) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);

		try {
			outputStream.writeByte(PacketHandler.PACKET_SELECTION_UPDATE);
			outputStream.writeInt(sel);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Strings.MOD_ID;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
	}

}
