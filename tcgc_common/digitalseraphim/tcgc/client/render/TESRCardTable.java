package digitalseraphim.tcgc.client.render;

import digitalseraphim.tcgc.tile.TECardTable;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TESRCardTable extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double d0, double d1,
			double d2, float f) {
		if(!(te instanceof TECardTable)){
			return;
		}

		TECardTable cardTable = (TECardTable)te;

		
		
	}

}
