package digitalseraphim.tcgc.client.render.items;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import digitalseraphim.tcgc.core.logic.Card;
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
			// EntityItem entity = (EntityItem) data[1];

			renderCardBack(render);
		}
			break;
		case EQUIPPED: {
			RenderBlocks render = (RenderBlocks) data[0];
			// EntityLiving entity = (EntityLiving) data[1];
			renderCardBack(render);
		}
			break;
		case FIRST_PERSON_MAP: {
			TextureManager texMan = (TextureManager) data[1];
			renderFirstPerson(texMan, item);
			break;
		}
		case EQUIPPED_FIRST_PERSON: {

		}
			break;
		case INVENTORY: {
			RenderBlocks render = (RenderBlocks) data[0];
			renderCardBack(render);
		}
			break;
		default:
			break;

		}

	}

	float partialTicks = 0;

	public void renderFirstPerson(TextureManager texMan, ItemStack item) {
		Card[] cards = ItemCard.cardsFromItemStack(item);
		int sel = item.getTagCompound().getInteger("selected");
		for (int i = 0; i < cards.length; i++) {
			GL11.glPushMatrix();
			GL11.glTranslatef(-5f * cards.length + (i * 10f), (i == sel) ? -20f : 0f, -1 + .05f * Math.abs(i - sel));
			renderCardFront(texMan, item, i);
			GL11.glPopMatrix();
		}
	}

	public void renderCardFront(TextureManager texMan, ItemStack item, int i) {
		texMan.bindTexture(new ResourceLocation("tcgc:textures/items/card_front_base.png"));
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorOpaque_I(0xffffff);
		float x = 1.F / 32.F;
		byte b0 = 7;
		tess.addVertexWithUV((double) (0 - b0), (double) (128 + b0), 0.0D, 0.0D, 1.0D);
		tess.addVertexWithUV((double) (128 + b0), (double) (128 + b0), 0.0D, x * 24., 1.0D);
		tess.addVertexWithUV((double) (128 + b0), (double) (0 - b0), 0.0D, x * 24., 0.0D);
		tess.addVertexWithUV((double) (0 - b0), (double) (0 - b0), 0.0D, 0.0D, 0.0D);
		tess.draw();

		texMan.bindTexture(new ResourceLocation("tcgc:textures/items/manasymbols.png"));
		
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

		Card c = ItemCard.getCard(item, i);
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 0, -.01f);
		fr.drawString(c.getName(), 0, 0, 0);
		GL11.glPopMatrix();
	}

	public void renderCardBack(RenderBlocks render) {
		Tessellator tess = Tessellator.instance;
		bindTexture(render, new ResourceLocation("tcgc:textures/items/card_back.png"));
		float x = 1.F / 32.F;

		tess.startDrawingQuads();

		tess.addVertexWithUV(-12 * x, .1, -.5, 0, 1);
		tess.addVertexWithUV(-12 * x, .1, .5, 0, 0);
		tess.addVertexWithUV(12 * x, .1, .5, 1, 1);
		tess.addVertexWithUV(12 * x, .1, -.5, 1, 0);

		tess.draw();
	}

	protected void bindTexture(RenderBlocks render, ResourceLocation res) {
		render.minecraftRB.getTextureManager().bindTexture(res);
	}
}
