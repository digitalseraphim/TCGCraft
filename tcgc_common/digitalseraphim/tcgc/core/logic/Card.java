package digitalseraphim.tcgc.core.logic;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;

public class Card {
	public final static HashMap<String,Card> manaCards = new HashMap<>();
	
	static{
		//                                      E F A W O N
		new Card(Mana.EARTH.name(),  Type.MANA, 1,0,0,0,0,0);
		new Card(Mana.FIRE.name(),   Type.MANA, 0,1,0,0,0,0);
		new Card(Mana.AIR.name(),    Type.MANA, 0,0,1,0,0,0);
		new Card(Mana.WATER.name(),  Type.MANA, 0,0,0,1,0,0);
		new Card(Mana.ORDER.name(),  Type.MANA, 0,0,0,0,1,0);
		new Card(Mana.ENTROPY.name(),Type.MANA, 0,0,0,0,0,1);		
		
		new Card("Volcano",          Type.MANA, 1,1,0,0,0,0);
		new Card("Flying Island",    Type.MANA, 1,0,1,0,0,0);
		new Card("Island",           Type.MANA, 1,0,0,1,0,0);	
		new Card("Fire Vortex",      Type.MANA, 0,1,1,0,0,0);
		new Card("Newborn Island",   Type.MANA, 0,1,0,1,0,0);
		new Card("Cloud",            Type.MANA, 0,0,1,1,0,0);
	}
	
	String name;
	Type type;
	
	//for mana cards this is how much mana is provided by the card
	final int cost[] = new int[6];
	
	public Card(String name, Type t, int earthCost, int fireCost, int airCost, int waterCost, int orderCost, int entropyCost) {
		this.type = t;
		cost[0] = earthCost;
		cost[1] = fireCost;
		cost[2] = airCost;
		cost[3] = waterCost;
		cost[4] = orderCost;
		cost[5] = entropyCost;
		
		switch(type){
		case CARD_MODIFIER:
			break;
		case MANA:
			manaCards.put(name, this);
			break;
		case SELF_MODIFIER:
			break;
		case SPELL:
			break;
		case SUMMON:
			break;
		default:
			break;
		
		}
		
	}
	
	public static enum Type {
		MANA, SPELL, SELF_MODIFIER, CARD_MODIFIER, SUMMON
	}
	
	//yes, planning for future Thaumcraft integration
	public static enum Mana {
		EARTH, FIRE, AIR, WATER, ORDER, ENTROPY
	}
	
	public void toNBT(NBTTagCompound tagCompound){
		NBTTagCompound card = new NBTTagCompound("card");
		card.setString("cardName", name);
		
	}
	
}
