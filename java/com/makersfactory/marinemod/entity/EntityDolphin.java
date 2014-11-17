package com.makersfactory.marinemod.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class EntityDolphin extends EntityWaterMob {

	public EntityDolphin(World p_i1695_1_) {
		super(p_i1695_1_);
		this.setSize(1.0F,1.0F);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
		this.tasks.addTask(2,  new EntityAITempt(this, 1.0D, Items.fish, false));
		this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
	}


    public void onUpdate()
    {
        super.onUpdate();
    }
    
    public boolean isAIEnabled()
    {
        return true;
    }
    
    public boolean canBePushed()
    {
    	return false;
    }
    
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0D);
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
