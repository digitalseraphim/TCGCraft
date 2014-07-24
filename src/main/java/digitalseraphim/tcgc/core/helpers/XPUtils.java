package digitalseraphim.tcgc.core.helpers;

import net.minecraft.entity.player.EntityPlayer;

public class XPUtils {
	public static void subtractXP(EntityPlayer player, int xp){
		player.experienceTotal -= xp;
		player.experienceLevel = xpToLevel(player.experienceTotal);
		player.experience = (float)(player.experienceTotal - levelToXP(player.experienceLevel))/(float)player.xpBarCap();
	}
	
	public static int xpToLevel(int xp){
		int level = 1;
		int toNext = levelToXP(level++);
		while(xp > toNext){
			toNext = levelToXP(level++);
		}

		return level-2;
	}
	
	public static int levelToXP(int level){
		int i = 0;
		if(level >= 30){
			i = (int)((3.5 * level * level) - (151.5*level) + 2220);
		}else if(level > 15){
			i =  (int)((1.5*level*level) - (29.5*level) + 360);
		}else{
			i = 17 * level; 
		}
		return i;
	}
}
