package digitalseraphim.tcgc.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
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
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		bindTexture(new ResourceLocation("tcgc:textures/blocks/anim_strip.png"));
//		RenderUtils.setGLColorFromInt(color);

		GL11.glTranslatef((float) x + 0.125F, (float) y + 0.5F, (float) z + 0.125F);
//		GL11.glScalef(0.75F, 0.999F, 0.75F);
		GL11.glTranslatef(0, -0.5F, 0);

		Render.renderAABB(AxisAlignedBB.getAABBPool().getAABB(0,0,0,1,1,1));

		GL11.glPopAttrib();
		GL11.glPopMatrix();

		
		
	}

}
