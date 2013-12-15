package digitalseraphim.tcgc.core.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CardCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "card";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "commands.card.usage";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		// TODO Auto-generated method stub

	}

}
