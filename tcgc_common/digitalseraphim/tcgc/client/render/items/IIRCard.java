package digitalseraphim.tcgc.client.render.items;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.IItemRenderer;
import digitalseraphim.tcgc.items.ItemCard;

public class IIRCard implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		System.out.println("IIRCard.handleRenderType() - " + item.getItem().itemID + " / " + item.getItem().getClass().getName() + " / "  + type.name());
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
//			EntityItem entity = (EntityItem) data[1];
			
			renderCardBack(render.minecraftRB.getTextureManager());
		}
			break;
		case EQUIPPED: {
			RenderBlocks render = (RenderBlocks) data[0];
			EntityLiving entity = (EntityLiving) data[1];
			renderCardBack(render.minecraftRB.getTextureManager());
		}
			break;
		case EQUIPPED_FIRST_PERSON: {
			RenderBlocks render = (RenderBlocks) data[0];
			EntityLiving entity = (EntityLiving) data[1];
			System.out.println("IIRCard.renderItem() - EQUIPPED_FIRST_PERSON");
		}
			break;
		case FIRST_PERSON_MAP: {
			EntityPlayer player  = (EntityPlayer) data[0];
			TextureManager texMan = (TextureManager) data[1];
			MapData mapData = (MapData) data[2];
			renderCardBack(texMan);
		}
			break;
		case INVENTORY: {
			RenderBlocks render = (RenderBlocks) data[0];
			renderCardBack(render.minecraftRB.getTextureManager());
		}
			break;
		default:
			break;

		}

	}

	public void renderCardFront() {

	}

	public void renderCardBack(TextureManager texMan){
		Tessellator tess = Tessellator.instance;
		bindTexture(texMan, new ResourceLocation("tcgc:textures/items/card_back"));
		float x = 1.F/32.F;
		
		tess.startDrawingQuads();
		
		tess.addVertexWithUV(-12*x, .1, -.5, 0, 1);
		tess.addVertexWithUV(-12*x, .1, .5, 0, 0);
		tess.addVertexWithUV(12*x, .1, .5, 1, 1);
		tess.addVertexWithUV(12*x, .1, -.5, 1, 0);
		
		tess.draw();
	}
	
    protected void bindTexture(TextureManager texMan, ResourceLocation res)
    {
    	texMan.bindTexture(res);
    }
}
