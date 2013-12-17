package digitalseraphim.tcgc.core.commands;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import digitalseraphim.tcgc.TCGCraft;
import digitalseraphim.tcgc.core.logic.Card;
import digitalseraphim.tcgc.core.logic.CardInstance;
import digitalseraphim.tcgc.items.ItemCard;

public class TCGCCommand extends CommandBase {
	static Map<String, String[]> helpOptions = new HashMap<String, String[]>();

	static {
		helpOptions.put("", new String[] { "Help contents:", "  [Card] Types", "  [Hand] Management",
				"  [Prep]aration", "  [Cast]ing", "[Modi]fiers", "[Rest]oring" });

		helpOptions
				.put("CARD",
						new String[] { "There are 4 card types:",
								"  Mana: These provide the magic to pay for the cards you want to cast.",
								"  Spells: These are active attack or defense spells",
								"  Summon: These summon various creatures.",
								"  Modifiers: These can be applied to the player or to an activated summon card (See 'help mod' for more)",
								"Each card has 2 XP costs, listed at the bottom of the card.  The first is the cost to 'use' the card (see " +
								"'help prep', and the second is the 'restore' cost (see 'help rest')."});
		helpOptions.put("HAND", new String[] {"Hand Management:", "Hands are a very important aspect of casting.  A hand contains a card to cast, " +
				"as well as the mana to cast it. To remove a card from a hand, press your 'drop' key while the card is selected in the hand. " +
				"To add a card, or multiple cards, to a hand, place all of them into a crafting table. (This will change in a later version)"});
		helpOptions.put("PREP", new String[] {"Preparing a card for casting:", "To prepare a card for casting, place the card and the mana to cast " +
				"it into a hand. It is important to only put the mana you need into the hand, as it will all be used, and you will have to pay the XP " +
				"cost for all cards in the hand. Once the hand is ready, right click the hand with the card to cast selected. The card description " +
				"should change to 'Activated' and you should see a shimmer over the card. The mana used to activate the spell will become 'Used&Locked' "+
				"(See 'help rest') The card is now ready to cast (see 'help cast'), or, if it is a summoning card, modification (see 'help mod')."});
		helpOptions.put("CAST", new String[] {"Casting an activated card:", "Once you have activated a card, casting is simple. If you are casting " +
				"a summon spell, point at the ground where you want to spawn the creature and right click. If it is a directed attack spell, point " +
				"in the direction you want to fire it, and right click. Other spells are directionless. Once the spell has been cast, the card status " +
				"will change to 'used' and will have to be restored before being used again. The mana cards used will be unlocked, but will remain " +
				"in the 'used' state until restored (see 'help rest')."});
		helpOptions.put("MODI", new String[] {"Modifiers:", "Modifier cards can be used in 2 ways, the first and simple way is to cast them like a " +
				"normal, undirected spell. This will apply the modifier to the player casting the card. The second is to apply the modifier to an " +
				"activated summon spell. This causes the summoned creature to have the modifier's effect. To apply a modifier to a summon spell, first " +
				"activate the summon spell (see 'help prep').  Next, add the modifier and the mana needed to cast it to the hand containing the activated " +
				"summon spell.  Now, prepare the modifier.  This will mark it and the mana as 'Used&Locked'.  The modifier has now been applied " +
				"and will activate on summon."});
		helpOptions.put("REST", new String[] {"Restoring Cards:", "Once you cast a spell, the spell and all of the mana used to cast it are 'Used' an " +
				"cannot be re-used until they have been 'restored'.  To restore a card, simply right click with it selected in the hand.  If you have " +
				"enough XP, the card will be unlocked and will be ready for reuse."});
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

	private void sendMessage(ICommandSender ics, String[] msg){
		for(String s: msg){
			ics.sendChatToPlayer(ChatMessageComponent.createFromText(s));
		}
	}
	
	private boolean isOP(ICommandSender ics){
		return MinecraftServer.getServer().getConfigurationManager().getOps().contains(ics.getPlayerCoordinates());
	}
	
	@Override
	public void processCommand(ICommandSender ics, String[] args) {

		if (args.length > 0) {
			Commands c = Commands.fromString(args[0]);

			switch (c) {
			case Help:
				if (args.length == 1) {
					sendMessage(ics, helpOptions.get(""));
				}else{
					String s = args[1].substring(0,4).toUpperCase();
					if(helpOptions.containsKey(s)){
						sendMessage(ics, helpOptions.get("s"));
					}else{
						sendMessage(ics, new String[]{"Unknown help topic '"+s+"'"});
					}
				}
			case Card:
				ics.
				
				
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
