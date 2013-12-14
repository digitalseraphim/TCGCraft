package digitalseraphim.tcgc.client.mouse;

import digitalseraphim.tcgc.items.ItemCard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class MouseHandler {
	public static int dwheel = 0;
	
	@ForgeSubscribe
	public void mouseEvent(MouseEvent me){
		dwheel = me.dwheel;
		if(me.dwheel != 0){
			EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
			if(p.isSneaking()){
				ItemStack stack = p.getCurrentEquippedItem();
				Item i = stack.getItem();
				
				if(i instanceof ItemCard){
					System.out.println("scrolling - " + me.dwheel/120);
					ItemCard.scrollSelected(stack, -me.dwheel/120);
					me.setCanceled(true);
				}
			}
		}
	}
}
