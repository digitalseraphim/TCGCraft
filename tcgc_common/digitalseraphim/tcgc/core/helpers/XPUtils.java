package digitalseraphim.tcgc.core.helpers;

import net.minecraft.entity.player.EntityPlayer;

public class XPUtils {
	public static void subtractXP(EntityPlayer player, int xp){
		player.experienceTotal -= xp;
		
		player.experienceLevel = xpToLevel(player.experienceTotal);
		
		player.experience = player.experienceTotal - levelToXP(player.experienceLevel);
	}
	
	public static int xpToLevel(int xp){
		int level = 0;
		int toNext = levelToXP(level+1);
		
		while(xp > toNext){
			xp -= toNext;
			toNext = levelToXP(level+1);
		}
		
		return level;
	}
	
	public static int levelToXP(int level){
		if(level >= 30){
			return (int)((3.5 * level * level) - (151.5*level) + 2220);
		}
		if(level > 15){
			return (int)((1.5*level*level) - (29.5*level) + 360);
		}
		return 17 * level;
	}
}
