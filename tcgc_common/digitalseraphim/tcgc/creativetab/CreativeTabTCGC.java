package digitalseraphim.tcgc.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import digitalseraphim.tcgc.core.helpers.Strings;

public class CreativeTabTCGC extends CreativeTabs {

	public CreativeTabTCGC() {
		super(CreativeTabs.getNextID(), Strings.MOD_ID);
	}
    
	@SideOnly(Side.CLIENT)
    /**
     * the itemID for the item to be displayed on the tab
     */
    public int getTabIconItemIndex() {
        return Item.appleGold.itemID;
    }
}
