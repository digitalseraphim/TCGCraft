package digitalseraphim.tcgc.client.render.items;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
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
			EntityLiving entity = (EntityLiving) data[1];
			renderCardBack(render);
		}
			break;
		case EQUIPPED_FIRST_PERSON: {
			RenderBlocks render = (RenderBlocks) data[0];
			EntityPlayer entity = (EntityPlayer) data[1];
			System.out.println("IIRCard.renderItem() - EQUIPPED_FIRST_PERSON");
			renderFirstPerson(entity, render);
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
	public void renderFirstPerson(EntityPlayer player, RenderBlocks render) {
		GL11.glPushMatrix();
		partialTicks+=.01f;
		float f12 = 0.8F;
		float f1 = 0;
		float f2 = player.prevRotationPitch;
		float f7 = player.getSwingProgress(partialTicks);
		float f8 = MathHelper.sin(f7 * (float) Math.PI);
		float f6 = MathHelper.sin(MathHelper.sqrt_float(f7) * (float) Math.PI);
		GL11.glTranslatef(-f6 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(f7) * (float) Math.PI * 2.0F) * 0.2F,
				-f8 * 0.2F);
		f7 = 1.0F - f2 / 45.0F + 0.1F;

		if (f7 < 0.0F) {
			f7 = 0.0F;
		}

		if (f7 > 1.0F) {
			f7 = 1.0F;
		}

		f7 = -MathHelper.cos(f7 * (float) Math.PI) * 0.5F + 0.5F;
		GL11.glTranslatef(0.0F, 0.0F * f12 - (1.0F - f1) * 1.2F - f7 * 0.5F + 0.04F, -0.9F * f12);
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(f7 * -85.0F, 0.0F, 0.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		render.minecraftRB.getTextureManager().bindTexture(((EntityPlayerSP) player).getLocationSkin());

		for (int k = 0; k < 2; ++k) {
			int l = k * 2 - 1;
			GL11.glPushMatrix();
			GL11.glTranslatef(-0.0F, -0.6F, 1.1F * (float) l);
			GL11.glRotatef((float) (-45 * l), 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef((float) (-65 * l), 0.0F, 1.0F, 0.0F);
			RenderPlayer renderplayer = (RenderPlayer) RenderManager.instance.getEntityRenderObject(player);
			float f11 = 1.0F;
			GL11.glScalef(f11, f11, f11);
			renderplayer.renderFirstPersonArm(player);
			GL11.glPopMatrix();
		}

		f8 = player.getSwingProgress(0);
		f6 = MathHelper.sin(f8 * f8 * (float) Math.PI);
		float f9 = MathHelper.sin(MathHelper.sqrt_float(f8) * (float) Math.PI);
		GL11.glRotatef(-f6 * 20.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-f9 * 20.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(-f9 * 80.0F, 1.0F, 0.0F, 0.0F);
		float f10 = 0.38F;
		GL11.glScalef(f10, f10, f10);
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
		float f11 = 0.015625F;
		GL11.glScalef(f11, f11, f11);
		render.minecraftRB.getTextureManager().bindTexture(
				new ResourceLocation("tcgc:textures/items/card_front_base.png"));
		Tessellator tessellator = Tessellator.instance;
		GL11.glNormal3f(0.0F, 0.0F, -1.0F);
		tessellator.startDrawingQuads();
		byte b0 = 7;
		tessellator.addVertexWithUV((double) (0 - b0), (double) (128 + b0), 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV((double) (128 + b0), (double) (128 + b0), 0.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV((double) (128 + b0), (double) (0 - b0), 0.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV((double) (0 - b0), (double) (0 - b0), 0.0D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glPopMatrix();

	}

	public void renderCardFront() {

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
