package digitalseraphim.tcgc.core.helpers;

import net.minecraft.entity.player.EntityPlayer;

public class XPUtils {
	public static void subtractXP(EntityPlayer player, int xp){
		System.out.println("before: ");
		System.out.println(" total: " + player.experienceTotal);
		System.out.println(" level: " + player.experienceLevel);
		System.out.println(" xp   : " + player.experience);

		player.experienceTotal -= xp;
		player.experienceLevel = xpToLevel(player.experienceTotal);
		player.experience = (float)(player.experienceTotal - levelToXP(player.experienceLevel))/(float)player.xpBarCap();

		System.out.println("after losing " + xp + " xp: ");
		System.out.println(" total: " + player.experienceTotal);
		System.out.println(" level: " + player.experienceLevel);
		System.out.println(" xp   : " + player.experience);
	}
	
	public static int xpToLevel(int xp){
		int level = 1;
		int toNext = levelToXP(level++);
		System.out.println("xp in " + xp);
		while(xp > toNext){
			toNext = levelToXP(level++);
		}

		System.out.println(xp + " xp = level " + (level-2));
		return level-1;
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
		System.out.println("to get to level " + level + " need " + i + " xp");
		return i;
	}
}
