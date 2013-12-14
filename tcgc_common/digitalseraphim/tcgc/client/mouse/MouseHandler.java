package digitalseraphim.tcgc.client.mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class MouseHandler {

	@ForgeSubscribe
	public void mouseEvent(MouseEvent me){
		if(me.dwheel != 0){
			ItemStack stack = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
		}
	}
}
