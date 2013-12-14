package digitalseraphim.tcgc.core.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Card {
	private final static HashMap<String, Card> allCards = new HashMap<>();
	private final static HashMap<String, Card> manaCards = new HashMap<>();
	private final static HashMap<String, Card> spellCards = new HashMap<>();
	private final static HashMap<String, Card> selfModCards = new HashMap<>();
	private final static HashMap<String, Card> cardModCards = new HashMap<>();
	private final static HashMap<String, Card> summonCards = new HashMap<>();

	static {
		// E F A W O N
		new Card(Mana.EARTH.name(), Type.MANA, 1, 0, 0, 0, 0, 0);
		new Card(Mana.FIRE.name(), Type.MANA, 0, 1, 0, 0, 0, 0);
		new Card(Mana.AIR.name(), Type.MANA, 0, 0, 1, 0, 0, 0);
		new Card(Mana.WATER.name(), Type.MANA, 0, 0, 0, 1, 0, 0);
		new Card(Mana.ORDER.name(), Type.MANA, 0, 0, 0, 0, 1, 0);
		new Card(Mana.ENTROPY.name(), Type.MANA, 0, 0, 0, 0, 0, 1);

		new Card("Volcano", Type.MANA, 1, 1, 0, 0, 0, 0);
		new Card("Flying Island", Type.MANA, 1, 0, 1, 0, 0, 0);
		new Card("Island", Type.MANA, 1, 0, 0, 1, 0, 0);
		new Card("Fire Vortex", Type.MANA, 0, 1, 1, 0, 0, 0);
		new Card("Newborn Island", Type.MANA, 0, 1, 0, 1, 0, 0);
		new Card("Cloud", Type.MANA, 0, 0, 1, 1, 0, 0);

		new Card("FireBolt", Type.SPELL, 0, 1, 0, 0, 0, 0);
		new Card("FireBall", Type.SPELL, 0, 3, 0, 0, 0, 0);
		new Card("WaterStream", Type.SPELL, 0, 0, 0, 1, 0, 0);
		new Card("Tsunami", Type.SPELL, 0, 0, 0, 3, 0, 0);

		new Card("Heal", Type.SELF_MODIFIER, 0, 0, 1, 1, 1, 0);
		new Card("Haste", Type.SELF_MODIFIER, 0, 0, 3, 1, 0, 0);

		new Card("Haste", Type.CARD_MODIFIER, 0, 0, 3, 1, 0, 0);
		new Card("Haste", Type.CARD_MODIFIER, 0, 0, 3, 1, 0, 0);

		new Card("SnowGolem", Type.SUMMON, 0, 0, 2, 2, 2, 0);
		new Card("IronGolem", Type.SUMMON, 2, 2, 0, 0, 2, 0);
	}

	private final String name;
	private final Type type;

	// for mana cards this is how much mana is provided by the card
	private final int earthCost;
	private final int fireCost;
	private final int airCost;
	private final int waterCost;
	private final int orderCost;
	private final int entropyCost;

	public Card(String name, Type t, int earthCost, int fireCost, int airCost, int waterCost, int orderCost,
			int entropyCost) {
		this.name = name;
		this.type = t;
		this.earthCost = earthCost;
		this.fireCost = fireCost;
		this.airCost = airCost;
		this.waterCost = waterCost;
		this.orderCost = orderCost;
		this.entropyCost = entropyCost;

		switch (type) {
		case CARD_MODIFIER:
			cardModCards.put(name, this);
			break;
		case MANA:
			manaCards.put(name, this);
			break;
		case SELF_MODIFIER:
			selfModCards.put(name, this);
			break;
		case SPELL:
			spellCards.put(name, this);
			break;
		case SUMMON:
			summonCards.put(name, this);
			break;
		default:
			System.out.println("Error, unhandled card type: " + type);
			break;
		}

		allCards.put(getFullName(), this);
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return type.name() + "_" + name;
	}

	public static Map<String, Card> getAllCards() {
		return Collections.unmodifiableMap(allCards);
	}

	public static Card getRandomCard(Random r) {
		return allCards.values().toArray(new Card[0])[r.nextInt(allCards.size())];
	}

	public static Map<String, Card> getManaCards() {
		return Collections.unmodifiableMap(manaCards);
	}

	public static Card getRandomManaCard(Random r) {
		return manaCards.values().toArray(new Card[0])[r.nextInt(manaCards.size())];
	}

	public static Map<String, Card> getSpellCards() {
		return Collections.unmodifiableMap(spellCards);
	}

	public static Card getRandomSpellCard(Random r) {
		return spellCards.values().toArray(new Card[0])[r.nextInt(spellCards.size())];
	}

	public static Map<String, Card> getSelfModCards() {
		return Collections.unmodifiableMap(selfModCards);
	}

	public static Card getRandomSelfModCard(Random r) {
		return selfModCards.values().toArray(new Card[0])[r.nextInt(selfModCards.size())];
	}

	public static Map<String, Card> getCardModCards() {
		return Collections.unmodifiableMap(cardModCards);
	}

	public static Card getRandomCardModCard(Random r) {
		return cardModCards.values().toArray(new Card[0])[r.nextInt(cardModCards.size())];
	}

	public static Map<String, Card> getSummonCards() {
		return Collections.unmodifiableMap(summonCards);
	}

	public static Card getRandomSummonCard(Random r) {
		return summonCards.values().toArray(new Card[0])[r.nextInt(summonCards.size())];
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

	public Card fromName(String n) {
		return allCards.get(n);
	}

	public static enum Type {
		MANA, SPELL, SELF_MODIFIER, CARD_MODIFIER, SUMMON
	}

	// yes, planning for future Thaumcraft integration
	public static enum Mana {
		EARTH, FIRE, AIR, WATER, ORDER, ENTROPY
	}

}
