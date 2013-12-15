package digitalseraphim.tcgc.core.logic;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.EnumRarity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomItem;
import net.minecraft.world.World;

public abstract class Card extends WeightedRandomItem {
	private final static HashMap<String, Card> allCards = new HashMap<String, Card>();
	private final static HashMap<String, Card> manaCards = new HashMap<String, Card>();
	private final static HashMap<String, Card> spellCards = new HashMap<String, Card>();
	private final static HashMap<String, Card> modCards = new HashMap<String, Card>();
	private final static HashMap<String, Card> summonCards = new HashMap<String, Card>();
	private final static HashMap<EnumRarity, List<Card>> cardsByRarity = new HashMap<EnumRarity, List<Card>>();

	static {
		cardsByRarity.put(EnumRarity.common, new ArrayList<Card>());
		cardsByRarity.put(EnumRarity.uncommon, new ArrayList<Card>());
		cardsByRarity.put(EnumRarity.rare, new ArrayList<Card>());
		cardsByRarity.put(EnumRarity.epic, new ArrayList<Card>());

		new ManaCard(Mana.EARTH.getName(), 1, 0, 0, 0, 0, 0, EnumRarity.common);
		new ManaCard(Mana.FIRE.getName(), 0, 1, 0, 0, 0, 0, EnumRarity.common);
		new ManaCard(Mana.AIR.getName(), 0, 0, 1, 0, 0, 0, EnumRarity.common);
		new ManaCard(Mana.WATER.getName(), 0, 0, 0, 1, 0, 0, EnumRarity.common);
		new ManaCard(Mana.ORDER.getName(), 0, 0, 0, 0, 1, 0, EnumRarity.common);
		new ManaCard(Mana.ENTROPY.getName(), 0, 0, 0, 0, 0, 1, EnumRarity.common);

		new ManaCard("Volcano", 1, 1, 0, 0, 0, 0, EnumRarity.rare);
		new ManaCard("Flying Island", 1, 0, 1, 0, 0, 0, EnumRarity.rare);
		new ManaCard("Island", 1, 0, 0, 1, 0, 0, EnumRarity.rare);
		new ManaCard("Fire Vortex", 0, 1, 1, 0, 0, 0, EnumRarity.rare);
		new ManaCard("Newborn Island", 0, 1, 0, 1, 0, 0, EnumRarity.rare);
		new ManaCard("Cloud", 0, 0, 1, 1, 0, 0, EnumRarity.rare);

		new BoltAttackCard("FireBolt", 0, 1, 0, 0, 0, 0, EnumRarity.common, EntitySmallFireball.class, 5);
		new BoltAttackCard("FireBall", 0, 3, 0, 0, 0, 0, EnumRarity.rare, EntityLargeFireball.class, 5, 5);
		new BoltAttackCard("WitherBall", 0, 3, 0, 0, 0, 0, EnumRarity.rare, EntityWitherSkull.class, 5);
		//these need to be fixed
		new BeamAttackCard("WaterStream", 0, 0, 0, 1, 0, 0, EnumRarity.common );
		new TsunamiCard(0, 0, 0, 3, 0, 0, EnumRarity.rare);

		new ModifierCard("Heal", 0, 0, 1, 1, 1, 0, EnumRarity.uncommon, new PotionEffect(6,1));
		new ModifierCard("Haste", 1, 0, 3, 0, 0, 0, EnumRarity.rare, new PotionEffect(3,10*20));
		new ModifierCard("Speed", 0, 0, 3, 1, 0, 0, EnumRarity.rare, new PotionEffect(1,10*20));
		new ModifierCard("Jump", 1, 0, 3, 0, 0, 0, EnumRarity.rare, new PotionEffect(8,10*20));
		new ModifierCard("Strength", 0, 0, 3, 1, 0, 0, EnumRarity.rare, new PotionEffect(5,10*20));
		new ModifierCard("Heal", 0, 0, 1, 1, 1, 0, EnumRarity.rare, new PotionEffect(10,5*20));
		new ModifierCard("Resistence", 2, 0, 0, 0, 2, 0, EnumRarity.rare, new PotionEffect(11,10*20));
		new ModifierCard("Fire Resistence", 0, 3, 0, 3, 0, 0, EnumRarity.rare, new PotionEffect(12,10*20));
		new ModifierCard("Water Breathing", 0, 0, 3, 3, 0, 0, EnumRarity.rare, new PotionEffect(13,10*20));
		new ModifierCard("Invisibility", 0, 0, 3, 3, 0, 0, EnumRarity.rare, new PotionEffect(14,10*20));
		new ModifierCard("Night Vision", 0, 0, 3, 3, 0, 0, EnumRarity.rare, new PotionEffect(16,10*20));

		new SummonCard("SnowGolem", 0, 0, 2, 2, 2, 0, EnumRarity.rare, EntitySnowman.class);
		new SummonCard("IronGolem", 2, 2, 0, 0, 2, 0, EnumRarity.epic, EntityIronGolem.class);
		new SummonCard("Villager", 2, 2, 0, 0, 2, 0, EnumRarity.rare, EntityVillager.class);
	}

	private final String name;
	private final Type type;
	private final EnumRarity rarity;

	// for mana cards this is how much mana is provided by the card
	private final int[] cost;

	public Card(String name, Type t, int earthCost, int fireCost, int airCost, int waterCost, int orderCost,
			int entropyCost, EnumRarity rarity) {
		super((rarity.ordinal()+1) * 5);
		this.name = name;
		this.type = t;
		this.rarity = rarity;
		cost = new int[] { earthCost, fireCost, airCost, waterCost, orderCost, entropyCost };

		switch (type) {
		case MANA:
			manaCards.put(name, this);
			break;
		case MODIFIER:
			modCards.put(name, this);
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
		cardsByRarity.get(rarity).add(this);
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
		return (Card) WeightedRandom.getRandomItem(r, allCards.values());
	}

	public static Map<String, Card> getManaCards() {
		return Collections.unmodifiableMap(manaCards);
	}

	public static Card getRandomManaCard(Random r) {
		return (Card) WeightedRandom.getRandomItem(r, manaCards.values());
	}

	public static Map<String, Card> getSpellCards() {
		return Collections.unmodifiableMap(spellCards);
	}

	public static Card getRandomSpellCard(Random r) {
		return (Card) WeightedRandom.getRandomItem(r, spellCards.values());
	}

	public static Map<String, Card> getModCards() {
		return Collections.unmodifiableMap(modCards);
	}

	public static Card getRandomModCard(Random r) {
		return (Card) WeightedRandom.getRandomItem(r, modCards.values());
	}

	public static Map<String, Card> getSummonCards() {
		return Collections.unmodifiableMap(summonCards);
	}

	public static Card getRandomSummonCard(Random r) {
		return (Card) WeightedRandom.getRandomItem(r, summonCards.values());
	}

	public static Card getRandomCardByRarity(Random r, EnumRarity rarity) {
		return (Card) WeightedRandom.getRandomItem(r, cardsByRarity.get(rarity));
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

	public EnumRarity getRarity() {
		return rarity;
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
		MANA("Mana"), SPELL("Spell"), MODIFIER("Modifier"), SUMMON("Summon");

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

	public int getUseXPCost() {
		int useXP = 10 * (rarity.ordinal() + 1);
		return useXP;
	}

	public int getRestoreXPCost() {
		int squaredRarity = (rarity.ordinal() + 1) * (rarity.ordinal() + 1);
		int restoreXP = 15 * squaredRarity;
		return restoreXP;
	}

	public abstract void cast(EntityPlayer player, float x, float y, float z);

	public static class ManaCard extends Card {

		public ManaCard(String name, int earthCost, int fireCost, int airCost, int waterCost, int orderCost,
				int entropyCost, EnumRarity rarity) {
			super(name, Type.MANA, earthCost, fireCost, airCost, waterCost, orderCost, entropyCost, rarity);
		}

		@Override
		public void cast(EntityPlayer player, float x, float y, float z) {
			// do nothing
		}

	}

	public static class SummonCard extends Card {
		private Class<? extends EntityLiving> toSummon;

		private Vector<ModifierCard> modCards = new Vector<ModifierCard>();
		
		public SummonCard(String name, int earthCost, int fireCost, int airCost, int waterCost, int orderCost,
				int entropyCost, EnumRarity rarity, Class<? extends EntityLiving> toSummon) {
			super(name, Type.SUMMON, earthCost, fireCost, airCost, waterCost, orderCost, entropyCost, rarity);
			this.toSummon = toSummon;
		}

		@Override
		public void cast(EntityPlayer player, float x, float y, float z) {
			if(player.worldObj.isRemote){
				return;
			}
			try {
				EntityLiving ent = toSummon.getConstructor(World.class).newInstance(player.worldObj);
				ent.setPosition(x, y + 1, z);
				player.worldObj.spawnEntityInWorld(ent);
				
				for(ModifierCard modCard: modCards){
					modCard.cast(ent);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void addModCard(ModifierCard modCard){
			modCards.add(modCard);
		}
	}

	public static class BoltAttackCard extends Card{
		Class<? extends EntityFireball> projectileClass;
		private double velocity;
		private int explosionStrenth = -1;
		
		public BoltAttackCard(String name, int earthCost, int fireCost, int airCost, int waterCost, int orderCost,
				int entropyCost, EnumRarity rarity, Class<? extends EntityFireball> projectileClass, double velocity, int explosionStrength) {
			super(name, Type.SPELL, earthCost, fireCost, airCost, waterCost, orderCost, entropyCost, rarity);
			this.projectileClass = projectileClass;
			this.velocity = velocity;
			this.explosionStrenth = explosionStrength;
		}

		public BoltAttackCard(String name, int earthCost, int fireCost, int airCost, int waterCost, int orderCost,
				int entropyCost, EnumRarity rarity, Class<? extends EntityFireball> projectileClass, double velocity) {
			super(name, Type.SPELL, earthCost, fireCost, airCost, waterCost, orderCost, entropyCost, rarity);
			this.projectileClass = projectileClass;
			this.velocity = velocity;
		}

		@Override
		public void cast(EntityPlayer player, float x, float y, float z) {
			Vec3 v = player.getLookVec();
			Constructor<? extends EntityFireball> con;
			try {
				con = projectileClass.getConstructor(World.class, EntityLivingBase.class, double.class, double.class, double.class);
				EntityFireball proj = con.newInstance(player.worldObj, player, velocity*v.xCoord, velocity*v.yCoord, velocity*v.zCoord);
				if(proj instanceof EntityLargeFireball && this.explosionStrenth > 0){
					((EntityLargeFireball)proj).field_92057_e = this.explosionStrenth;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static class BeamAttackCard extends Card{

		public BeamAttackCard(String name, int earthCost, int fireCost, int airCost, int waterCost,
				int orderCost, int entropyCost, EnumRarity rarity) {
			super(name, Type.SPELL, earthCost, fireCost, airCost, waterCost, orderCost, entropyCost, rarity);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void cast(EntityPlayer player, float x, float y, float z) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static class TsunamiCard extends Card{

		public TsunamiCard(int earthCost, int fireCost, int airCost, int waterCost, int orderCost,
				int entropyCost, EnumRarity rarity) {
			super("Tsunami", Type.SPELL, earthCost, fireCost, airCost, waterCost, orderCost, entropyCost, rarity);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void cast(EntityPlayer player, float x, float y, float z) {
			double px = player.posX;
			double py = player.posY;
			double pz = player.posZ;
			
			for(int i = -4; i <= 4; i++){
				for(int j = -4; j <= 4; j++){
					if(i == 0 && j == 0){
						continue;
					}
					int md = Math.abs(i) + Math.abs(j);
					if(md > 4){
						continue;
					}
					int bid = player.worldObj.getBlockId((int)px + i, (int)py, (int)pz+i);
					System.out.println("bid = " + bid);
					Block b = Block.blocksList[bid];
					
					if(b==null || b.isBlockReplaceable(player.worldObj, (int)px + i, (int)py, (int)pz+i)){
						player.worldObj.setBlock((int)px + i, (int)py, (int)pz+i, Block.waterMoving.blockID, md, 7);
					}
				}
			}
		}
		
	}

	public static class ModifierCard extends Card{
		private PotionEffect eff;
		
		public ModifierCard(String name, int earthCost, int fireCost, int airCost, int waterCost,
				int orderCost, int entropyCost, EnumRarity rarity, PotionEffect eff) {
			super(name, Type.MODIFIER, earthCost, fireCost, airCost, waterCost, orderCost, entropyCost, rarity);
			this.eff = eff;
		}

		@Override
		public void cast(EntityPlayer player, float x, float y, float z) {
			System.out.println("casting effect on player");
			cast(player);
		}
		
		public void cast(EntityLivingBase el){
			el.addPotionEffect(new PotionEffect(eff));
		}
		
	}

}
