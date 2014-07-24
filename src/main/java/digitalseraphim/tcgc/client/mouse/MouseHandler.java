package digitalseraphim.tcgc.client.mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import digitalseraphim.tcgc.TCGCraft;
import digitalseraphim.tcgc.core.proxy.ClientProxy;
import digitalseraphim.tcgc.items.ItemCard;

public class MouseHandler {
	public static int dwheel = 0;
	
	@SubscribeEvent
	public void mouseEvent(MouseEvent me){
		dwheel = me.dwheel;
		if(me.dwheel != 0){
			EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
			if(p.isSneaking()){
				ItemStack stack = p.getCurrentEquippedItem();
				if(stack != null){
					Item i = stack.getItem();
					
					if(i instanceof ItemCard){
						((ClientProxy)TCGCraft.proxy).sendCardSelectionPacket(-me.dwheel/120);
						me.setCanceled(true);
					}
				}
			}
		}
	}
}
