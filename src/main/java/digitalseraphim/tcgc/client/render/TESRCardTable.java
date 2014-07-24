package digitalseraphim.tcgc.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import digitalseraphim.tcgc.tile.TECardTable;

public class TESRCardTable extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y,
			double z, float f) {
		if(!(te instanceof TECardTable)){
			return;
		}

		TECardTable cardTable = (TECardTable)te;

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);

		bindTexture(new ResourceLocation("tcgc:textures/blocks/anim_strip.png"));
		//bindTexture(new ResourceLocation("minecraft:textures/blocks/water_flow.png"));

		GL11.glTranslatef((float) x , (float) y + 0.5F, (float) z );
		GL11.glScalef(0.98F, 0.98F, 0.98F);
		GL11.glTranslatef(0.01F, -0.5F, 0.01F);

		Tessellator tess = Tessellator.instance;

		tess.setBrightness(((Block)Block.blockRegistry.getObject("grass")).getMixedBrightnessForBlock(te.getWorldObj(), (int) x,
				(int) y, (int) z));
		tess.setColorOpaque_I(0xffffff);

		tess.startDrawingQuads();

		int t = ((cardTable.getTicks()/3)+1)%16;
		int s = ((cardTable.getTicks()/3))%16;

		tess.addVertexWithUV(0,1,0, 0,.0625*t);
		tess.addVertexWithUV(1,1,0, 1,.0625*t);
		tess.addVertexWithUV(1,0,0, 1,.0625*s);
		tess.addVertexWithUV(0,0,0, 0,.0625*s);

		tess.addVertexWithUV(1,1,0, 0,.0625*t);
		tess.addVertexWithUV(1,1,1, 1,.0625*t);
		tess.addVertexWithUV(1,0,1, 1,.0625*s);
		tess.addVertexWithUV(1,0,0, 0,.0625*s);

		tess.addVertexWithUV(1,1,1, 0,.0625*t);
		tess.addVertexWithUV(0,1,1, 1,.0625*t);
		tess.addVertexWithUV(0,0,1, 1,.0625*s);
		tess.addVertexWithUV(1,0,1, 0,.0625*s);

		tess.addVertexWithUV(0,1,1, 0,.0625*t);
		tess.addVertexWithUV(0,1,0, 1,.0625*t);
		tess.addVertexWithUV(0,0,0, 1,.0625*s);
		tess.addVertexWithUV(0,0,1, 0,.0625*s);

		tess.addVertexWithUV(0,1,1, 0,.0625*t);
		tess.addVertexWithUV(1,1,1, 1,.0625*t);
		tess.addVertexWithUV(1,1,0, 1,.0625*s);
		tess.addVertexWithUV(0,1,0, 0,.0625*s);

		tess.addVertexWithUV(0,0,0, 0,.0625*t);
		tess.addVertexWithUV(1,0,0, 1,.0625*t);
		tess.addVertexWithUV(1,0,1, 1,.0625*s);
		tess.addVertexWithUV(0,0,1, 0,.0625*s);

		tess.draw();

		GL11.glPopAttrib();
		GL11.glPopMatrix();



	}

}
