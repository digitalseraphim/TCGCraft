package digitalseraphim.tcgc.client.render.items;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import digitalseraphim.tcgc.items.ItemCard;

public class IIRCard implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return item.getItem() instanceof ItemCard;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		switch (type) {
		case ENTITY: {
			RenderBlocks render = (RenderBlocks) data[0];
			EntityItem entity = (EntityItem) data[1];
		}
			break;
		case EQUIPPED: {
			RenderBlocks render = (RenderBlocks) data[0];
			EntityLiving entity = (EntityLiving) data[1];
		}
			break;
		case EQUIPPED_FIRST_PERSON: {
			RenderBlocks render = (RenderBlocks) data[0];
			EntityLiving entity = (EntityLiving) data[1];
		}
			break;
		case FIRST_PERSON_MAP: {
			RenderBlocks render = (RenderBlocks) data[0];
			EntityLiving entity = (EntityLiving) data[1];
			MapData mapData = (MapData) data[2];
		}
			break;
		case INVENTORY: {
			RenderBlocks render = (RenderBlocks) data[0];
		}
			break;
		default:
			break;

		}

	}

	public void renderCardFront() {

	}

	public void renderCardBack(RenderBlocks render){
		Tessellator tess = Tessellator.instance;
		render.
		tess.
		
	}
}
