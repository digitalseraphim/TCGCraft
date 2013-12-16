package digitalseraphim.tcgc.core.hacks;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.FIRST_PERSON_MAP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import digitalseraphim.tcgc.items.ItemCard;

public class TCGCItemRenderer extends ItemRenderer {

    private ItemStack itemToRender;

    /**
     * How far the current item has been equipped (0 disequipped and 1 fully up)
     */
    private float equippedProgress;
    private float prevEquippedProgress;
    /** The index of the currently held item (0-8, or -1 if not yet updated) */
    private int equippedItemSlot = -1;

	
	public TCGCItemRenderer(Minecraft par1Minecraft) {
		super(par1Minecraft);
	}

    public void updateEquippedItem()
    {
        this.prevEquippedProgress = this.equippedProgress;
        EntityClientPlayerMP entityclientplayermp = Minecraft.getMinecraft().thePlayer;
        ItemStack itemstack = entityclientplayermp.inventory.getCurrentItem();
        boolean flag = this.equippedItemSlot == entityclientplayermp.inventory.currentItem && itemstack == this.itemToRender;

        if (this.itemToRender == null && itemstack == null)
        {
            flag = true;
        }

        if (itemstack != null && this.itemToRender != null && itemstack != this.itemToRender && itemstack.itemID == this.itemToRender.itemID && itemstack.getItemDamage() == this.itemToRender.getItemDamage())
        {
            this.itemToRender = itemstack;
            flag = true;
        }

        float f = 0.4F;
        float f1 = flag ? 1.0F : 0.0F;
        float f2 = f1 - this.equippedProgress;

        if (f2 < -f)
        {
            f2 = -f;
        }

        if (f2 > f)
        {
            f2 = f;
        }

        this.equippedProgress += f2;

        if (this.equippedProgress < 0.1F)
        {
            this.itemToRender = itemstack;
            this.equippedItemSlot = entityclientplayermp.inventory.currentItem;
        }
    }
	
	@Override
	public void renderItemInFirstPerson(float par1) {
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		ItemStack item = player.getCurrentEquippedItem();
		if(item.getItem() instanceof ItemCard){
	        float f1 = this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * par1;
	        EntityClientPlayerMP entityclientplayermp = Minecraft.getMinecraft().thePlayer;
	        float f2 = entityclientplayermp.prevRotationPitch + (entityclientplayermp.rotationPitch - entityclientplayermp.prevRotationPitch) * par1;
	        GL11.glPushMatrix();
	        GL11.glRotatef(f2, 1.0F, 0.0F, 0.0F);
	        GL11.glRotatef(entityclientplayermp.prevRotationYaw + (entityclientplayermp.rotationYaw - entityclientplayermp.prevRotationYaw) * par1, 0.0F, 1.0F, 0.0F);
	        RenderHelper.enableStandardItemLighting();
	        GL11.glPopMatrix();
	        EntityPlayerSP entityplayersp = (EntityPlayerSP)entityclientplayermp;
	        float f3 = entityplayersp.prevRenderArmPitch + (entityplayersp.renderArmPitch - entityplayersp.prevRenderArmPitch) * par1;
	        float f4 = entityplayersp.prevRenderArmYaw + (entityplayersp.renderArmYaw - entityplayersp.prevRenderArmYaw) * par1;
	        GL11.glRotatef((entityclientplayermp.rotationPitch - f3) * 0.1F, 1.0F, 0.0F, 0.0F);
	        GL11.glRotatef((entityclientplayermp.rotationYaw - f4) * 0.1F, 0.0F, 1.0F, 0.0F);
	        ItemStack itemstack = item;
	        float f5 = Minecraft.getMinecraft().theWorld.getLightBrightness(MathHelper.floor_double(entityclientplayermp.posX), MathHelper.floor_double(entityclientplayermp.posY), MathHelper.floor_double(entityclientplayermp.posZ));
	        f5 = 1.0F;
	        int i = Minecraft.getMinecraft().theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(entityclientplayermp.posX), MathHelper.floor_double(entityclientplayermp.posY), MathHelper.floor_double(entityclientplayermp.posZ), 0);
	        int j = i % 65536;
	        int k = i / 65536;
	        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        float f6;
	        float f7;
	        float f8;

	        if (itemstack != null)
	        {
	            i = Item.itemsList[itemstack.itemID].getColorFromItemStack(itemstack, 0);
	            f7 = (float)(i >> 16 & 255) / 255.0F;
	            f8 = (float)(i >> 8 & 255) / 255.0F;
	            f6 = (float)(i & 255) / 255.0F;
	            GL11.glColor4f(f5 * f7, f5 * f8, f5 * f6, 1.0F);
	        }
	        else
	        {
	            GL11.glColor4f(f5, f5, f5, 1.0F);
	        }

	        float f9;
	        float f10;
	        float f11;
	        float f12;
	        Render render;
	        RenderPlayer renderplayer;

	        if (itemstack != null && itemstack.getItem() instanceof ItemMap)
	        {
	            GL11.glPushMatrix();
	            f12 = 0.8F;
	            f7 = entityclientplayermp.getSwingProgress(par1);
	            f8 = MathHelper.sin(f7 * (float)Math.PI);
	            f6 = MathHelper.sin(MathHelper.sqrt_float(f7) * (float)Math.PI);
	            GL11.glTranslatef(-f6 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(f7) * (float)Math.PI * 2.0F) * 0.2F, -f8 * 0.2F);
	            f7 = 1.0F - f2 / 45.0F + 0.1F;

	            if (f7 < 0.0F)
	            {
	                f7 = 0.0F;
	            }

	            if (f7 > 1.0F)
	            {
	                f7 = 1.0F;
	            }

	            f7 = -MathHelper.cos(f7 * (float)Math.PI) * 0.5F + 0.5F;
	            GL11.glTranslatef(0.0F, 0.0F * f12 - (1.0F - f1) * 1.2F - f7 * 0.5F + 0.04F, -0.9F * f12);
	            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
	            GL11.glRotatef(f7 * -85.0F, 0.0F, 0.0F, 1.0F);
	            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	            Minecraft.getMinecraft().getTextureManager().bindTexture(entityclientplayermp.getLocationSkin());

	            for (k = 0; k < 2; ++k)
	            {
	                int l = k * 2 - 1;
	                GL11.glPushMatrix();
	                GL11.glTranslatef(-0.0F, -0.6F, 1.1F * (float)l);
	                GL11.glRotatef((float)(-45 * l), 1.0F, 0.0F, 0.0F);
	                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
	                GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
	                GL11.glRotatef((float)(-65 * l), 0.0F, 1.0F, 0.0F);
	                render = RenderManager.instance.getEntityRenderObject(Minecraft.getMinecraft().thePlayer);
	                renderplayer = (RenderPlayer)render;
	                f11 = 1.0F;
	                GL11.glScalef(f11, f11, f11);
	                renderplayer.renderFirstPersonArm(Minecraft.getMinecraft().thePlayer);
	                GL11.glPopMatrix();
	            }

	            f8 = entityclientplayermp.getSwingProgress(par1);
	            f6 = MathHelper.sin(f8 * f8 * (float)Math.PI);
	            f9 = MathHelper.sin(MathHelper.sqrt_float(f8) * (float)Math.PI);
	            GL11.glRotatef(-f6 * 20.0F, 0.0F, 1.0F, 0.0F);
	            GL11.glRotatef(-f9 * 20.0F, 0.0F, 0.0F, 1.0F);
	            GL11.glRotatef(-f9 * 80.0F, 1.0F, 0.0F, 0.0F);
	            f10 = 0.38F;
	            GL11.glScalef(f10, f10, f10);
	            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
	            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
	            GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
	            f11 = 0.015625F;
	            GL11.glScalef(f11, f11, f11);
	            
	            IItemRenderer custom = MinecraftForgeClient.getItemRenderer(itemstack, FIRST_PERSON_MAP);
	            MapData mapdata = ((ItemMap)itemstack.getItem()).getMapData(itemstack, Minecraft.getMinecraft().theWorld);

	            if (custom == null)
	            {
	                if (mapdata != null)
	                {
	                    this.mapItemRenderer.renderMap(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().getTextureManager(), mapdata);
	                }
	            }
	            else
	            {
	                custom.renderItem(FIRST_PERSON_MAP, itemstack, Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().getTextureManager(), mapdata);
	            }

	            GL11.glPopMatrix();
	        }
		}else{
			super.renderItemInFirstPerson(par1);
		}
	}
	
}
