package digitalseraphim.tcgc.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import digitalseraphim.tcgc.TCGCraft;
import digitalseraphim.tcgc.items.ItemCard;

public class CardSelectionMessage implements IMessage,
		IMessageHandler<CardSelectionMessage, IMessage> {

	int selected;
	
	public CardSelectionMessage() {
	}
	
	public CardSelectionMessage(int sel){
		selected = sel;
	}
	
	@Override
	public IMessage onMessage(CardSelectionMessage message, MessageContext ctx) {
		EntityPlayer p = ctx.getServerHandler().playerEntity;
		ItemStack is = p.getCurrentEquippedItem();
		if (is.getItem() instanceof ItemCard) {
			ItemCard.scrollSelected(is, message.selected);
		}
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		selected = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(selected);
	}

}
