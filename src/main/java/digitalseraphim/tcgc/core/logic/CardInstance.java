package digitalseraphim.tcgc.core.logic;

import digitalseraphim.tcgc.core.logic.Card.Type;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class CardInstance {
	private static String NBT_NAME = "name";
	private static String NBT_ACTIVATED = "activated";
	private static String NBT_USED = "used";
	private static String NBT_LOCKED = "locked";
	
	
	private Card baseCard;
	private boolean isActivated;
	private boolean isUsed;
	private boolean isLocked;
	
	public CardInstance(NBTTagCompound cardNBT){
		baseCard = Card.getAllCards().get(cardNBT.getString(NBT_NAME));
		isActivated = cardNBT.getBoolean(NBT_ACTIVATED);
		isUsed = cardNBT.getBoolean(NBT_USED);
		isLocked = cardNBT.getBoolean(NBT_LOCKED);
	}
	
	public CardInstance(Card baseCard){
		this.baseCard = baseCard;
		isActivated = false;
		isUsed = false;
		isLocked = false;
	}
	
	public static CardInstance fromNBT(NBTTagCompound cardNBT){
		CardInstance ci = new CardInstance(cardNBT);
		if(ci.baseCard == null){
			return null;
		}
		return ci;
	}

	public Card getBaseCard() {
		return baseCard;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public boolean needsTargetBlock(){
		return getBaseCard().getType() == Type.SUMMON;
	}
	
	public NBTTagCompound toTagCompound(){
		NBTTagCompound cardNBT = new NBTTagCompound();
		
		cardNBT.setString(NBT_NAME, baseCard.getFullName());
		cardNBT.setBoolean(NBT_ACTIVATED, isActivated);
		cardNBT.setBoolean(NBT_LOCKED, isLocked);
		cardNBT.setBoolean(NBT_USED, isUsed);
		
		return cardNBT;
	}
	
	public void cast(EntityPlayer player, float x, float y, float z){
		if(player.worldObj.isRemote){
			return;
		}
		getBaseCard().cast(player, x, y, z);
	}
}
