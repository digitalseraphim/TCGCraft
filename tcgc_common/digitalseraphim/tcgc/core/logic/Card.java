package digitalseraphim.tcgc.core.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.EnumRarity;

public class Card {
	private final static HashMap<String, Card> allCards = new HashMap<>();
	private final static HashMap<String, Card> manaCards = new HashMap<>();
	private final static HashMap<String, Card> spellCards = new HashMap<>();
	private final static HashMap<String, Card> selfModCards = new HashMap<>();
	private final static HashMap<String, Card> cardModCards = new HashMap<>();
	private final static HashMap<String, Card> summonCards = new HashMap<>();

	static {
		new Card(Mana.EARTH.name(), Type.MANA, 1, 0, 0, 0, 0, 0, EnumRarity.common);
		new Card(Mana.FIRE.name(), Type.MANA, 0, 1, 0, 0, 0, 0, EnumRarity.common);
		new Card(Mana.AIR.name(), Type.MANA, 0, 0, 1, 0, 0, 0, EnumRarity.common);
		new Card(Mana.WATER.name(), Type.MANA, 0, 0, 0, 1, 0, 0, EnumRarity.common);
		new Card(Mana.ORDER.name(), Type.MANA, 0, 0, 0, 0, 1, 0, EnumRarity.common);
		new Card(Mana.ENTROPY.name(), Type.MANA, 0, 0, 0, 0, 0, 1, EnumRarity.common);

		new Card("Volcano", Type.MANA, 1, 1, 0, 0, 0, 0, EnumRarity.rare);
		new Card("Flying Island", Type.MANA, 1, 0, 1, 0, 0, 0, EnumRarity.rare);
		new Card("Island", Type.MANA, 1, 0, 0, 1, 0, 0, EnumRarity.rare);
		new Card("Fire Vortex", Type.MANA, 0, 1, 1, 0, 0, 0, EnumRarity.rare);
		new Card("Newborn Island", Type.MANA, 0, 1, 0, 1, 0, 0, EnumRarity.rare);
		new Card("Cloud", Type.MANA, 0, 0, 1, 1, 0, 0, EnumRarity.rare);

		new Card("FireBolt", Type.SPELL, 0, 1, 0, 0, 0, 0);
		new Card("FireBall", Type.SPELL, 0, 3, 0, 0, 0, 0);
		new Card("WaterStream", Type.SPELL, 0, 0, 0, 1, 0, 0);
		new Card("Tsunami", Type.SPELL, 0, 0, 0, 3, 0, 0);

		new Card("Heal", Type.SELF_MODIFIER, 0, 0, 1, 1, 1, 0);
		new Card("Haste", Type.SELF_MODIFIER, 0, 0, 3, 1, 0, 0);

		new Card("Haste", Type.CARD_MODIFIER, 0, 0, 3, 1, 0, 0);

		new Card("SnowGolem", Type.SUMMON, 0, 0, 2, 2, 2, 0);
		new Card("IronGolem", Type.SUMMON, 2, 2, 0, 0, 2, 0);
	}

	private final String name;
	private final Type type;
	private final EnumRarity rarity;

	// for mana cards this is how much mana is provided by the card
	private final int[] cost;

	public Card(String name, Type t, int earthCost, int fireCost, int airCost, int waterCost, int orderCost,
			int entropyCost, EnumRarity rarity) {
		this.name = name;
		this.type = t;
		this.rarity = rarity;
		cost = new int[] { earthCost, fireCost, airCost, waterCost, orderCost, entropyCost };

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
		return cost[Mana.EARTH.ordinal()];
	}

	public int getFireCost() {
		return cost[Mana.FIRE.ordinal()];
	}

	public int getAirCost() {
		return cost[Mana.AIR.ordinal()];
	}

	public int getWaterCost() {
		return cost[Mana.WATER.ordinal()];
	}

	public int getOrderCost() {
		return cost[Mana.ORDER.ordinal()];
	}

	public int getEntropyCost() {
		return cost[Mana.ENTROPY.ordinal()];
	}

	public int getTotalCost() {
		int tot = 0;
		for (int i : cost) {
			tot += i;
		}
		return tot;
	}

	public Card fromName(String n) {
		return allCards.get(n);
	}

	public static enum Type {
		MANA("Mana"), SPELL("Spell"), SELF_MODIFIER("Self Mod"), CARD_MODIFIER("Card Mod"), SUMMON("Summon");

		private String name;

		private Type(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	// yes, planning for future Thaumcraft integration
	public static enum Mana {
		EARTH("Earth"), FIRE("Fire"), AIR("Air"), WATER("Water"), ORDER("Order"), ENTROPY("Entropy");

		private String name;

		private Mana(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public void addCost(int[] totalMana) {
		for (int i = 0; i < cost.length; i++) {
			totalMana[i] += cost[i];
		}
	}

	public int[] getCost() {
		return cost;
	}
}
