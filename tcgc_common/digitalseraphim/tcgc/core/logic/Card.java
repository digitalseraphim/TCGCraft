package digitalseraphim.tcgc.core.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Card {
	private final static HashMap<String,Card> manaCards = new HashMap<>();
	private final static HashMap<String,Card> allCards = new HashMap<>();
	
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
	
	private final String name;
	private final Type type;
	
	//for mana cards this is how much mana is provided by the card
	private final int earthCost;
	private final int fireCost;
	private final int airCost;
	private final int waterCost;
	private final int orderCost;
	private final int entropyCost;
	
	public Card(String name, Type t, int earthCost, int fireCost, int airCost, int waterCost, int orderCost, int entropyCost) {
		this.name = name;
		this.type = t;
		this.earthCost = earthCost;
		this.fireCost = fireCost;
		this.airCost = airCost;
		this.waterCost = waterCost;
		this.orderCost = orderCost;
		this.entropyCost = entropyCost;
		
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
		
		allCards.put(name, this);
	}
	
	public String getName() {
		return name;
	}
	
	public static Map<String, Card> getAllCards() {
		return Collections.unmodifiableMap(allCards);
	}

	public static Card getRandomCard(Random r){
		return allCards.values().toArray(new Card[0])[r.nextInt(allCards.size())]; 
	}

	public static Map<String, Card> getManaCards() {
		return Collections.unmodifiableMap(manaCards);
	}
	
	public static Card getRandomManaCard(Random r){
		return manaCards.values().toArray(new Card[0])[r.nextInt(manaCards.size())]; 
	}
	
	public Type getType() {
		return type;
	}

	public int getEarthCost() {
		return earthCost;
	}

	public int getFireCost() {
		return fireCost;
	}

	public int getAirCost() {
		return airCost;
	}

	public int getWaterCost() {
		return waterCost;
	}

	public int getOrderCost() {
		return orderCost;
	}

	public int getEntropyCost() {
		return entropyCost;
	}

	public Card fromName(String n){
		return allCards.get(n);
	}
	
	public static enum Type {
		MANA, SPELL, SELF_MODIFIER, CARD_MODIFIER, SUMMON
	}
	
	//yes, planning for future Thaumcraft integration
	public static enum Mana {
		EARTH, FIRE, AIR, WATER, ORDER, ENTROPY
	}
	
	
}
