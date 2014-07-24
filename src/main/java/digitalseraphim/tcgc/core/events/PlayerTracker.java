package digitalseraphim.tcgc.core.events;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import digitalseraphim.tcgc.core.helpers.Strings;
import digitalseraphim.tcgc.items.ItemStarterDeck;

public class PlayerTracker {

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent playerEvent) {
		NBTTagCompound data = playerEvent.player.getEntityData();
		NBTTagCompound modTag;

		if (data.hasKey(Strings.MOD_ID)) {
			modTag = data.getCompoundTag(Strings.MOD_ID);
		} else {
			modTag = new NBTTagCompound();
			data.setTag(Strings.MOD_ID, modTag);
		}

		if (!modTag.getBoolean("gotStarterDeck")) {
			modTag.setBoolean("gotStarterDeck", true);
			EntityItem ei = playerEvent.player.dropPlayerItemWithRandomChoice(
					ItemStarterDeck.createItemStack(), false);
			ei.delayBeforeCanPickup = 0;
		}
	}
}
