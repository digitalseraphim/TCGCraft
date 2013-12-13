package digitalseraphim.tcgc.tile;

import net.minecraft.tileentity.TileEntity;

public class TECardTable extends TileEntity {
	public static String NAME = "CardTable";
	int ticks = 0;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		ticks++;
	}
	
	
	public int getTicks(){
		return this.ticks;
	}
}
