package digitalseraphim.tcgc.client.render.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import digitalseraphim.tcgc.core.logic.CardInstance;
import digitalseraphim.tcgc.items.ItemCard;

public class IIRCard implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return item.getItem() instanceof ItemCard;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch(helper){
		case ENTITY_BOBBING:
		case ENTITY_ROTATION:
			return true;
		case BLOCK_3D:
		case EQUIPPED_BLOCK:
		case INVENTORY_BLOCK:
		default:
			return false;
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		switch (type) {
		case ENTITY: {
			RenderBlocks render = (RenderBlocks) data[0];
			// EntityItem entity = (EntityItem) data[1];
			GL11.glPushMatrix();
			GL11.glScalef(1.f/128.f, -1.f/128.f, -1.f);
			GL11.glTranslatef(-64f, -128f, 0f);
			renderCardFront(render.minecraftRB.getTextureManager(), ItemCard.getSelectedCard(item));
			renderCardBack(render.minecraftRB.getTextureManager(), item, ItemCard.getSelectedCardIndex(item));
			GL11.glPopMatrix();

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
		case EQUIPPED_FIRST_PERSON:
			//this shouldn't happen
			break;
		case INVENTORY: {
			RenderBlocks render = (RenderBlocks) data[0];
			GL11.glPushMatrix();
			GL11.glScalef(1.f/8.f, 1.f/8.f, -1.f);
			renderCardFront(render.minecraftRB.getTextureManager(), ItemCard.getSelectedCard(item));
			GL11.glPopMatrix();
		}
			break;
		default:
			break;

		}

	}


	public void renderFirstPerson(TextureManager texMan, ItemStack item) {
		CardInstance[] cards = ItemCard.cardsFromItemStack(item);
		int sel = ItemCard.getSelectedCardIndex(item);
		boolean collapsed = item.getTagCompound().getBoolean("collapsed");
		if(collapsed){
			GL11.glPushMatrix();
			renderCardFront(texMan, ItemCard.getSelectedCard(item));
			GL11.glPopMatrix();
		}else{
			for (int i = 0; i < cards.length; i++) {
				GL11.glPushMatrix();
				GL11.glTranslatef(-5f * cards.length + (i * 10f), (i == sel) ? -20f : 0f, -1 + .05f * Math.abs(i - sel) + ((i>sel)?.025f:0));
				renderCardFront(texMan, cards[i]);
				GL11.glPopMatrix();
			}	
		}
	}

	public void renderCardBack(TextureManager texMan, ItemStack item, int i) {
		texMan.bindTexture(new ResourceLocation("tcgc:textures/items/card_back.png"));
		Tessellator tess = Tessellator.instance;
		byte b0 = 7;
		
		tess.startDrawingQuads();
		tess.setColorOpaque_I(0xffffff);
		tess.addVertexWithUV((double) (0 - b0), (double) (0 - b0), 0.0D, 0.0D, 0.0D);
		tess.addVertexWithUV((double) (128 + b0), (double) (0 - b0), 0.0D, 1.0D, 0.0D);
		tess.addVertexWithUV((double) (128 + b0), (double) (128 + b0), 0.0D, 1.0D, 1.0D);
		tess.addVertexWithUV((double) (0 - b0), (double) (128 + b0), 0.0D, 0.0D, 1.0D);
		tess.draw();
	}
	
	
	public void renderCardFront(TextureManager texMan, CardInstance card) {
		texMan.bindTexture(new ResourceLocation("tcgc:textures/items/card_front_base.png"));
		Tessellator tess = Tessellator.instance;
		byte b0 = 7;
		
		tess.startDrawingQuads();
		tess.setColorOpaque_I(0xffffff);
		tess.addVertexWithUV((double) (0 - b0), (double) (128 + b0), 0.0D, 0.0D, 1.0D);
		tess.addVertexWithUV((double) (128 + b0), (double) (128 + b0), 0.0D, 1.0D, 1.0D);
		tess.addVertexWithUV((double) (128 + b0), (double) (0 - b0), 0.0D, 1.0D, 0.0D);
		tess.addVertexWithUV((double) (0 - b0), (double) (0 - b0), 0.0D, 0.0D, 0.0D);
		tess.draw();

		texMan.bindTexture(new ResourceLocation("tcgc:textures/items/manasymbols.png"));
		
		float xx = 128 - 8 * card.getBaseCard().getTotalCost();
		float yy = 4;
		float zz = -.01f;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 0, -.01f);

		if(card.getBaseCard().getEarthCost() > 0){
			for(int jj = 0; jj < card.getBaseCard().getEarthCost(); jj++){
				tess.startDrawingQuads();
				tess.setColorOpaque_I(0xffffff);
				tess.addVertexWithUV(xx    , yy + 8, zz, 0.0D, 1D);
				tess.addVertexWithUV(xx + 8, yy + 8, zz, 0.125D, 1D);
				tess.addVertexWithUV(xx + 8, yy    , zz, 0.125D, 0.0D);
				tess.addVertexWithUV(xx    , yy    , zz, 0.0D, 0.0D);
				tess.draw();
				xx += 8;
			}
		}
		
		if(card.getBaseCard().getFireCost() > 0){
			for(int jj = 0; jj < card.getBaseCard().getFireCost(); jj++){
				tess.startDrawingQuads();
				tess.setColorOpaque_I(0xffffff);
				tess.addVertexWithUV(xx    , yy + 8, zz, .125D, 1.0D);
				tess.addVertexWithUV(xx + 8, yy + 8, zz, .25D, 1.0D);
				tess.addVertexWithUV(xx + 8, yy    , zz, .25D, 0.D);
				tess.addVertexWithUV(xx    , yy    , zz, .125D, 0.D);
				tess.draw();
				xx += 8;
			}
		}
		
		if(card.getBaseCard().getAirCost() > 0){
			for(int jj = 0; jj < card.getBaseCard().getAirCost(); jj++){
				tess.startDrawingQuads();
				tess.setColorOpaque_I(0xffffff);
				tess.addVertexWithUV(xx    , yy + 8, zz, 0.25D,  1.0D);
				tess.addVertexWithUV(xx + 8, yy + 8, zz, 0.375D, 1.0D);
				tess.addVertexWithUV(xx + 8, yy    , zz, 0.375D, 0.0D);
				tess.addVertexWithUV(xx    , yy    , zz, 0.25D,  0.0D);
				tess.draw();
				xx += 8;
			}
		}

		if(card.getBaseCard().getWaterCost() > 0){
			for(int jj = 0; jj < card.getBaseCard().getWaterCost(); jj++){
				tess.startDrawingQuads();
				tess.setColorOpaque_I(0xffffff);
				tess.addVertexWithUV(xx    , yy + 8, zz, 0.375D, 1D);
				tess.addVertexWithUV(xx + 8, yy + 8, zz, 0.5D,   1D);
				tess.addVertexWithUV(xx + 8, yy    , zz, 0.5D,   0D);
				tess.addVertexWithUV(xx    , yy    , zz, 0.375D, 0D);
				tess.draw();
				xx += 8;
			}
		}
		
		if(card.getBaseCard().getOrderCost() > 0){
			for(int jj = 0; jj < card.getBaseCard().getOrderCost(); jj++){
				tess.startDrawingQuads();
				tess.setColorOpaque_I(0xffffff);
				tess.addVertexWithUV(xx    , yy + 8, zz, 0.5D,   1D);
				tess.addVertexWithUV(xx + 8, yy + 8, zz, 0.625D, 1D);
				tess.addVertexWithUV(xx + 8, yy    , zz, 0.625D, 0D);
				tess.addVertexWithUV(xx    , yy    , zz, 0.5D,   0D);
				tess.draw();
				xx += 8;
			}
		}

		if(card.getBaseCard().getEntropyCost() > 0){
			for(int jj = 0; jj < card.getBaseCard().getEntropyCost(); jj++){
				tess.startDrawingQuads();
				tess.setColorOpaque_I(0xffffff);
				tess.addVertexWithUV(xx    , yy + 8, zz, 0.625D, 1D);
				tess.addVertexWithUV(xx + 8, yy + 8, zz, 0.75D,  1D);
				tess.addVertexWithUV(xx + 8, yy    , zz, 0.75D,  0D);
				tess.addVertexWithUV(xx    , yy    , zz, 0.625D, 0D);
				tess.draw();
				xx += 8;
			}
		}

		Minecraft.getMinecraft().fontRenderer.drawString(card.getBaseCard().getName(), 0, 0, 0);

		zz = -0.02f;
		if(card.isUsed()){
			
			tess.startDrawingQuads();
			tess.setColorRGBA_I(0x0, 128);
			tess.addVertex((double) (0 - b0), (double) (128 + b0), zz);
			tess.addVertex((double) (128 + b0), (double) (128 + b0), zz);
			tess.addVertex((double) (128 + b0), (double) (0 - b0),zz);
			tess.addVertex((double) (0 - b0), (double) (0 - b0), zz);
			tess.draw();
		}
		
		//if(card.isActivated()){
			long tick = Minecraft.getSystemTime()/20000;
			int t = (int)((tick/3)+1)%16;
			int s = (int)((tick/3))%16;

			tess.addVertexWithUV(0,1,0, 0,.0625*t);
			tess.addVertexWithUV(1,1,0, 1,.0625*t);
			tess.addVertexWithUV(1,0,0, 1,.0625*s);
			tess.addVertexWithUV(0,0,0, 0,.0625*s);

			texMan.bindTexture(new ResourceLocation("minecraft:textures/misc/enchanted_item_glint.png"));
			tess.startDrawingQuads();
			tess.setColorOpaque_I(0xffffff);
			tess.addVertexWithUV((double) (0 - b0), (double) (128 + b0), zz, .0625*t, 0);
			tess.addVertexWithUV((double) (128 + b0), (double) (128 + b0), zz,.0625*t,  1);
			tess.addVertexWithUV((double) (128 + b0), (double) (0 - b0), zz,.0625*s, 1);
			tess.addVertexWithUV((double) (0 - b0), (double) (0 - b0),zz,.0625*s, 0);
			tess.draw();
		//}
		
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
