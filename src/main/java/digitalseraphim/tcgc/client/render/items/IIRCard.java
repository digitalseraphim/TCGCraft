package digitalseraphim.tcgc.client.render.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import digitalseraphim.tcgc.core.logic.Card.SummonCard;
import digitalseraphim.tcgc.core.logic.Card.Type;
import digitalseraphim.tcgc.core.logic.CardInstance;
import digitalseraphim.tcgc.items.ItemCard;

public class IIRCard implements IItemRenderer {
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return item.getItem() instanceof ItemCard;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch (helper) {
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
			GL11.glScalef(1.f / 128.f, -1.f / 128.f, -1.f);
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
			// this shouldn't happen
			break;
		case INVENTORY: {
			RenderBlocks render = (RenderBlocks) data[0];
			GL11.glPushMatrix();
			GL11.glScalef(1.f / 8.f, 1.f / 8.f, -1.f);
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
		if (collapsed) {
			GL11.glPushMatrix();
			renderCardFront(texMan, ItemCard.getSelectedCard(item));
			GL11.glPopMatrix();
		} else {
			for (int i = 0; i < cards.length; i++) {
				GL11.glPushMatrix();
				GL11.glTranslatef(-5f * (cards.length-1) + (i * 10f)+16, (i == sel && cards.length != 1) ? -10f : 0f, -1 + .05f * Math.abs(i - sel)
						+ ((i > sel) ? .025f : 0));
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
		tess.addVertexWithUV((double) (96 + b0), (double) (0 - b0), 0.0D, 1.0D, 0.0D);
		tess.addVertexWithUV((double) (96 + b0), (double) (128 + b0), 0.0D, 1.0D, 1.0D);
		tess.addVertexWithUV((double) (0 - b0), (double) (128 + b0), 0.0D, 0.0D, 1.0D);
		tess.draw();
	}

	public void renderCardFront(TextureManager texMan, CardInstance card) {
		texMan.bindTexture(new ResourceLocation("tcgc:textures/items/cards2.png"));
		Tessellator tess = Tessellator.instance;
		byte b0 = 7;

		tess.startDrawingQuads();
		tess.setColorOpaque_I(0xffffff);
		tess.addVertexWithUV((double) (00 - b0), (double) (128 + b0), 0.0D, 0.00, 1.0);
		tess.addVertexWithUV((double) (96 + b0), (double) (128 + b0), 0.0D, 0.75, 1.0);
		tess.addVertexWithUV((double) (96 + b0), (double) (000 - b0), 0.0D, 0.75, 0.0);
		tess.addVertexWithUV((double) (00 - b0), (double) (000 - b0), 0.0D, 0.00, 0.0);
		tess.draw();

		float xx = 96 - 8 * card.getBaseCard().getTotalCost();
		float yy = 0;
		float zz = -.01f;

		GL11.glPushMatrix();
		GL11.glTranslatef(0, 0, -.01f);

		if (card.getBaseCard().getEarthCost() > 0) {
			for (int jj = 0; jj < card.getBaseCard().getEarthCost(); jj++) {
				tess.startDrawingQuads();
				tess.setColorOpaque_I(0xffffff);
				tess.addVertexWithUV(xx + 0, yy + 8, zz, 0.750, 0.125);
				tess.addVertexWithUV(xx + 8, yy + 8, zz, 0.875, 0.125);
				tess.addVertexWithUV(xx + 8, yy + 0, zz, 0.875, 0.000);
				tess.addVertexWithUV(xx + 0, yy + 0, zz, 0.750, 0.000);
				tess.draw();
				xx += 8;
			}
		}

		if (card.getBaseCard().getFireCost() > 0) {
			for (int jj = 0; jj < card.getBaseCard().getFireCost(); jj++) {
				tess.startDrawingQuads();
				tess.setColorOpaque_I(0xffffff);
				tess.addVertexWithUV(xx + 0, yy + 8, zz, 0.875, 0.125);
				tess.addVertexWithUV(xx + 8, yy + 8, zz, 1.000, 0.125);
				tess.addVertexWithUV(xx + 8, yy + 0, zz, 1.000, 0.000);
				tess.addVertexWithUV(xx + 0, yy + 0, zz, 0.875, 0.000);
				tess.draw();
				xx += 8;
			}
		}

		if (card.getBaseCard().getAirCost() > 0) {
			for (int jj = 0; jj < card.getBaseCard().getAirCost(); jj++) {
				tess.startDrawingQuads();
				tess.setColorOpaque_I(0xffffff);
				tess.addVertexWithUV(xx + 0, yy + 8, zz, 0.750, 0.250);
				tess.addVertexWithUV(xx + 8, yy + 8, zz, 0.875, 0.250);
				tess.addVertexWithUV(xx + 8, yy + 0, zz, 0.875, 0.125);
				tess.addVertexWithUV(xx + 0, yy + 0, zz, 0.750, 0.125);
				tess.draw();
				xx += 8;
			}
		}

		if (card.getBaseCard().getWaterCost() > 0) {
			for (int jj = 0; jj < card.getBaseCard().getWaterCost(); jj++) {
				tess.startDrawingQuads();
				tess.setColorOpaque_I(0xffffff);
				tess.addVertexWithUV(xx + 0, yy + 8, zz, 0.875, 0.250);
				tess.addVertexWithUV(xx + 8, yy + 8, zz, 1.000, 0.250);
				tess.addVertexWithUV(xx + 8, yy + 0, zz, 1.000, 0.125);
				tess.addVertexWithUV(xx + 0, yy + 0, zz, 0.875, 0.125);
				tess.draw();
				xx += 8;
			}
		}

		if (card.getBaseCard().getOrderCost() > 0) {
			for (int jj = 0; jj < card.getBaseCard().getOrderCost(); jj++) {
				tess.startDrawingQuads();
				tess.setColorOpaque_I(0xffffff);
				tess.addVertexWithUV(xx + 0, yy + 8, zz, 0.750, 0.375);
				tess.addVertexWithUV(xx + 8, yy + 8, zz, 0.875, 0.375);
				tess.addVertexWithUV(xx + 8, yy + 0, zz, 0.875, 0.250);
				tess.addVertexWithUV(xx + 0, yy + 0, zz, 0.750, 0.250);
				tess.draw();
				xx += 8;
			}
		}

		if (card.getBaseCard().getEntropyCost() > 0) {
			for (int jj = 0; jj < card.getBaseCard().getEntropyCost(); jj++) {
				tess.startDrawingQuads();
				tess.setColorOpaque_I(0xffffff);
				tess.addVertexWithUV(xx + 0, yy + 8, zz, 0.875, 0.375);
				tess.addVertexWithUV(xx + 8, yy + 8, zz, 1.000, 0.375);
				tess.addVertexWithUV(xx + 8, yy + 0, zz, 1.000, 0.250);
				tess.addVertexWithUV(xx + 0, yy + 0, zz, 0.875, 0.250);
				tess.draw();
				xx += 8;
			}
		}

		if(card.getBaseCard().getType() == Type.SUMMON &&  RenderManager.instance != null && RenderManager.instance.renderEngine != null){
			SummonCard sCard = (SummonCard)card.getBaseCard();
			Class<? extends EntityLiving> clazz = sCard.getEntityClassToSummon();
			Render r = RenderManager.instance.getEntityClassRenderObject(clazz);

			GL11.glPushMatrix();
			GL11.glTranslatef(48, 64, 0);
			GL11.glScaled(18, -18, -.1);
			EntityLiving entity;
			try {
				entity = clazz.getConstructor(World.class).newInstance(Minecraft.getMinecraft().theWorld);
				entity.setPositionAndRotation(0, 0, 0, 90, 0);
				r.doRender(entity, 0, 0, 0, 0f,0f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			GL11.glPopMatrix();
			
		}
		
		GL11.glPushMatrix();
		GL11.glScaled(.9, .9, 1);
		GL11.glTranslatef(0,1,0);
		EnumRarity cardRarity = card.getBaseCard().getRarity();
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		fr.drawString(cardRarity.rarityColor + card.getBaseCard().getName(), 0, 0, 0);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		String type = card.getBaseCard().getType().getName();
		GL11.glScaled(.75, .75, 1);
		fr.drawString( type, (int)(4*1.3), (int)(70*1.3), 0);
		
		
		GL11.glPopMatrix();
		int useXP = card.getBaseCard().getUseXPCost();
		int restoreXP = card.getBaseCard().getRestoreXPCost();

		String xpString =useXP + "/" + restoreXP;
		int width = fr.getStringWidth(xpString);
		GL11.glScaled(.625, .625, 1);
		fr.drawString(xpString, (int)((107-width)*1.65), (int)(124*1.55), 0);
		
		zz = -0.02f;
		if (card.isUsed()) {
			tess.startDrawingQuads();
			tess.setColorRGBA_I(0x0, 128);
			tess.addVertex((double) (0 - b0), (double) (128 + b0), zz);
			tess.addVertex((double) (128 + b0), (double) (128 + b0), zz);
			tess.addVertex((double) (128 + b0), (double) (0 - b0), zz);
			tess.addVertex((double) (0 - b0), (double) (0 - b0), zz);
			tess.draw();
		}

		if (card.isActivated()) {
			long tick = (Minecraft.getSystemTime() / 50) % 15;
			int t = (int) ((tick / 3) + 1) % 16;
			int s = (int) ((tick / 3)) % 16;

			tess.addVertexWithUV(0, 1, 0, 0, .0625 * t);
			tess.addVertexWithUV(1, 1, 0, 1, .0625 * t);
			tess.addVertexWithUV(1, 0, 0, 1, .0625 * s);
			tess.addVertexWithUV(0, 0, 0, 0, .0625 * s);

			texMan.bindTexture(new ResourceLocation("tcgc:textures/misc/anim2.png"));
			tess.startDrawingQuads();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			tess.setColorRGBA_I(0xffffff, 150);
			tess.addVertexWithUV((double) (0 - b0), (double) (128 + b0), zz, 0, .0625 * t);
			tess.addVertexWithUV((double) (128 + b0), (double) (128 + b0), zz, 1, .0625 * t);
			tess.addVertexWithUV((double) (128 + b0), (double) (0 - b0), zz, 1, .0625 * s);
			tess.addVertexWithUV((double) (0 - b0), (double) (0 - b0), zz, 0, .0625 * s);
			tess.draw();
		}

//		GL11.glPushMatrix();
//		GL11.glScalef(50f,-50,.1f);
//		EntitySnowman snowman = new EntitySnowman(Minecraft.getMinecraft().theWorld);
//		snowman.setRotationYawHead(180);
//		RenderManager.instance.renderEntityWithPosYaw(snowman, 1.25,-2.5,.1,0,0);
//		
//		GL11.glPopMatrix();
//		
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
