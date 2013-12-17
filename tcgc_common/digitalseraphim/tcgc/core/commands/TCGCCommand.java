package digitalseraphim.tcgc.core.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import digitalseraphim.tcgc.TCGCraft;
import digitalseraphim.tcgc.core.logic.Card;
import digitalseraphim.tcgc.core.logic.CardInstance;
import digitalseraphim.tcgc.items.ItemCard;

public class TCGCCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "tcgc";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "commands.tcgc.usage";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] args) {
		if (args.length >= 2) {
			EntityPlayerMP entityplayermp = getPlayer(icommandsender, args[0]);

			ItemStack itemStack = ItemCard.createItemStack(TCGCraft.proxy.cardItem, new CardInstance[] { new CardInstance(Card.getAllCards()
					.get(args[1])) });

			EntityItem entityItem = entityplayermp.dropPlayerItem(itemStack);
            entityItem.delayBeforeCanPickup = 0;
		}
	}

}
