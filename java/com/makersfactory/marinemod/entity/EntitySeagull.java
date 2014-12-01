package com.makersfactory.marinemod.entity;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

public class EntitySeagull extends EntityAnimal {
	
	private ChunkCoordinates spawnPosition;
    public int courseChangeCooldown;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
	
	public EntitySeagull(World par1World) {
		super(par1World);
		this.setSize(1.0F,1.0F);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3,  new EntityAITempt(this, 1.0D, Items.fish, false));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
	} 
	
	protected void entityInit()
	{
	    super.entityInit();
	}

	public boolean isAIEnabled(){
		if(this.onGround){
			return true;
		}else{
			return false;
		}
	}
	
	protected Item getDropItem(){
		return this.isBurning() ? Items.cooked_fished : Items.fish;
	}
	
	protected void dropFewItems(boolean par1, int par2){
		int random = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2);
		
		for(int i = 0; i < random; ++i){
			if(i==2){
				this.dropItem(Items.feather, 2);
			} else if (i==1){
				this.dropItem(Items.fish, 1);
			}
		}
		if(this.isBurning()){
			this.dropItem(Items.cooked_fished, 1);
		}
	}
	
     //Moves the entity based on the specified heading.  Args: strafe, forward
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
    {
        if (this.isInWater())
        {
            this.moveFlying(p_70612_1_, p_70612_2_, 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929D;
            this.motionY *= 0.800000011920929D;
            this.motionZ *= 0.800000011920929D;
        }
        else if (this.handleLavaMovement())
        {
            this.moveFlying(p_70612_1_, p_70612_2_, 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
        }
        else
        {
            float f2 = 0.91F;

            if (this.onGround)
            {
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);
            this.moveFlying(p_70612_1_, p_70612_2_, this.onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;

            if (this.onGround)
            {
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)f2;
            this.motionY *= (double)f2;
            this.motionZ *= (double)f2;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d1 = this.posX - this.prevPosX;
        double d0 = this.posZ - this.prevPosZ;
        float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

        if (f4 > 1.0F)
        {
            f4 = 1.0F;
        }

        this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }
	
	//Times course changing and determines if course is traversable.
	 protected void updateEntityActionState()
	    {
	        double d0 = this.waypointX - this.posX;
	        double d1 = this.waypointY - this.posY;
	        double d2 = this.waypointZ - this.posZ;
	        double d3 = d0 * d0 + d1 * d1 + d2 * d2;

	        if (d3 < 1.0D || d3 > 3600.0D)
	        {
	            this.waypointX = this.posX + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
	            this.waypointY = this.posY + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
	            this.waypointZ = this.posZ + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
	        }

	        if (this.courseChangeCooldown-- <= 0)
	        {
	            this.courseChangeCooldown += this.rand.nextInt(5) + 2;
	            d3 = (double)MathHelper.sqrt_double(d3);

	            if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3))
	            {
	                this.motionX += d0 / d3 * 0.1D;
	                this.motionY += d1 / d3 * 0.1D;
	                this.motionZ += d2 / d3 * 0.1D;
	            }
	            else
	            {
	                this.waypointX = this.posX;
	                this.waypointY = this.posY;
	                this.waypointZ = this.posZ;
	            }
	        }

	        double d4 = 64.0D;
	    }
	 
	 //True if the seagull has an unobstructed line of travel to the waypoint.
	 
	 private boolean isCourseTraversable(double p_70790_1_, double p_70790_3_, double p_70790_5_, double p_70790_7_)
	    {
	        double d4 = (this.waypointX - this.posX) / p_70790_7_;
	        double d5 = (this.waypointY - this.posY) / p_70790_7_;
	        double d6 = (this.waypointZ - this.posZ) / p_70790_7_;
	        AxisAlignedBB axisalignedbb = this.boundingBox.copy();

	        for (int i = 1; (double)i < p_70790_7_; ++i)
	        {
	            axisalignedbb.offset(d4, d5, d6);

	            if (!this.worldObj.getCollidingBoundingBoxes(this, axisalignedbb).isEmpty())
	            {
	                return false;
	            }
	        }

	        return true;
	    }
	 
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) {
		return new EntitySeagull(worldObj);
	}
	
	//Going to change sounds below.
    protected String getLivingSound()
    {
        return "mob.chicken.say";
    }

    protected String getHurtSound()
    {
        return "mob.chicken.hurt";
    }

    protected String getDeathSound()
    {
        return "mob.chicken.hurt";
    }
   
    //Item used to breed Seagulls
    public boolean isBreedingItem(ItemStack p_70877_1_)
    {
        return p_70877_1_ != null && p_70877_1_.getItem() instanceof ItemFishFood;
    }
    
    public int getMaxSpawnedInChunk()
    {
        return 10;
    }
   
    //Called when the mob is falling. Calculates and applies fall damage.  
    protected void fall(float p_70069_1_) {}
}
