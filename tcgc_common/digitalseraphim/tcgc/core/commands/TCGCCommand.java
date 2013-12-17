package digitalseraphim.tcgc.core.commands;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import digitalseraphim.tcgc.TCGCraft;
import digitalseraphim.tcgc.core.logic.Card;
import digitalseraphim.tcgc.core.logic.CardInstance;
import digitalseraphim.tcgc.items.ItemCard;

public class TCGCCommand extends CommandBase {
	static Map<String, String[]> helpOptions = new HashMap<String, String[]>();

	static {
		helpOptions.put("", new String[] { "Help contents:", "  [Card] Types", "  [Hand] Management",
				"  [Prep]aration", "  [Cast]ing", "[Mod]ifiers" });

		helpOptions.put("Card", new String[] {});
		helpOptions.put("Hand", new String[] {});
		helpOptions.put("Prep", new String[] {});
		helpOptions.put("Cast", new String[] {});
		helpOptions.put("Mod", new String[] {});
	}

	@Override
	public String getCommandName() {
		return "tcgc";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "commands.tcgc.usage";
	}

	enum Commands {
		Help, Card;

		public static Commands fromString(String s) {
			for (Commands c : values()) {
				if (c.name().equalsIgnoreCase(s)) {
					return c;
				}
			}
			return null;
		}
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] args) {

		if (args.length > 0) {
			Commands c = Commands.fromString(args[0]);

			switch (c) {
			case Help:
				if (args.length == 1) {

				}
				icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText(""));
			case Card:
			}

		}

		if (args.length >= 2) {
			EntityPlayerMP entityplayermp = getPlayer(icommandsender, args[0]);

			ItemStack itemStack = ItemCard.createItemStack(TCGCraft.proxy.cardItem,
					new CardInstance[] { new CardInstance(Card.getAllCards().get(args[1])) });

			EntityItem entityItem = entityplayermp.dropPlayerItem(itemStack);
			entityItem.delayBeforeCanPickup = 0;
		}
	}

}
