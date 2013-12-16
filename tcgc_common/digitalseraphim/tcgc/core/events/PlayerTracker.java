package digitalseraphim.tcgc.core.events;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.IPlayerTracker;
import digitalseraphim.tcgc.core.helpers.Strings;
import digitalseraphim.tcgc.items.ItemStarterDeck;

public class PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		NBTTagCompound data = player.getEntityData();
		NBTTagCompound modTag;
		
		if(data.hasKey(Strings.MOD_ID)){
			modTag = data.getCompoundTag(Strings.MOD_ID);
		}else{
			modTag = new NBTTagCompound(Strings.MOD_ID);
			data.setCompoundTag(Strings.MOD_ID, modTag);
		}
		
		if(!modTag.getBoolean("gotStarterDeck")){
			modTag.setBoolean("gotStarterDeck", true);
			EntityItem ei = player.dropPlayerItem(ItemStarterDeck.createItemStack());
			ei.delayBeforeCanPickup = 0;
		}
		
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

}
