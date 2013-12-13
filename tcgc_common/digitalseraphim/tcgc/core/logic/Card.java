package digitalseraphim.tcgc.core.logic;

import java.util.HashMap;

public class Card {
	public final static HashMap<String,Card> manaCards = new HashMap<>();
	
	static{
		manaCards.put(Mana.EARTH.name(), new Card(Type.MANA, 1,0,0,0,0,0}));
	}
	
	
	Type type;
	
	//for mana cards this is how much mana is provided by the card
	final int cost[] = new int[6];
	
	
	public Card(Type t, int earthCost, int fireCost, int airCost, int waterCost, int orderCost, int entropyCost) {
		this.type = t;
		cost[0] = earthCost;
		cost[1] = fireCost;
		cost[2] = airCost;
		cost[3] = waterCost;
		cost[4] = orderCost;
		cost[5] = entropyCost;
	}
	
	
	
	public static enum Type {
		MANA, SPELL, SELF_MODIFIER, CARD_MODIFIER, SUMMON
	}
	
	//yes, planning for future Thaumcraft integration
	public static enum Mana {
		EARTH, FIRE, AIR, WATER, ORDER, ENTROPY
	}
}
