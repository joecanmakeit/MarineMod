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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

public class EntitySeagull extends EntityAnimal {

	protected boolean isFlying = true;
	private ChunkCoordinates spawnPosition;
	
	public EntitySeagull(World par1World) {
		super(par1World);
		this.setSize(1.0F,1.0F);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.fish, false));
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
		return true;
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
	
	/*
	public void onUpdate()
    {
        super.onUpdate();

        if (isFlying)
        {
        	//Counteracting gravity
        	this.motionY *= 0.6000000238418579D;
        }
    }

    protected void updateAITasks()
    {
        super.updateAITasks();

        if (isFlying)
        {
        	//Set flying movement
            double d0 = (double)this.spawnPosition.posX + 0.5D - this.posX;
            double d1 = (double)this.spawnPosition.posY + 0.1D - this.posY;
            double d2 = (double)this.spawnPosition.posZ + 0.5D - this.posZ;
            this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
            this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
            this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
            float f = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
            float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
            this.moveForward = 0.5F;
            this.rotationYaw += f1;
        }
    }*/

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
	
    //Returns the sound this mob makes while it's alive.
    protected String getLivingSound()
    {
        return "mob.pig.say";
    }

     // Returns the sound this mob makes when it is hurt.
    protected String getHurtSound()
    {
        return "mob.pig.say";
    }

     //Returns the sound this mob makes on death.
    protected String getDeathSound()
    {
        return "mob.pig.death";
    }
}
