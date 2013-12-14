package digitalseraphim.tcgc.core.network;

import java.nio.ByteBuffer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import digitalseraphim.tcgc.items.ItemCard;

public class PacketHandler implements IPacketHandler {
	public static final byte PACKET_SELECTION_UPDATE = 1;

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteBuffer bb = ByteBuffer.wrap(packet.data);

		byte type = bb.get();
		System.out.println("got packet " + type);
		switch (type) {
		case PACKET_SELECTION_UPDATE:
			EntityPlayer p = (EntityPlayer) player;
			ItemStack is = p.getCurrentEquippedItem();
			int amnt = bb.getInt();
			System.out.println("should select " + amnt);
			if (is.getItem() instanceof ItemCard) {
				ItemCard.scrollSelected(is, amnt);
			}
			break;
		default:
			System.out.println("unknown packet type: " + type);
		}

	}

}
