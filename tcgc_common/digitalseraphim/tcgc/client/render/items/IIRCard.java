package digitalseraphim.tcgc.client.render.items;

import digitalseraphim.tcgc.items.ItemCard;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class IIRCard implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return item.getItem() instanceof ItemCard;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type){
		case ENTITY:
			break;
		case EQUIPPED:
			break;
		case EQUIPPED_FIRST_PERSON:
			break;
		case FIRST_PERSON_MAP:
			break;
		case INVENTORY:
			break;
		default:
			break;
			
		}

	}

}
