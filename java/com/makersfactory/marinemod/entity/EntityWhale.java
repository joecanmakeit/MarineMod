package com.makersfactory.marinemod.entity;

import java.util.Random;


import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

public class EntityWhale extends EntityWaterMob {
	
	Random random = new Random();
	
	public EntityWhale(World par1World) {
		super(par1World);
		this.isImmuneToFire = true;
		
		this.tasks.addTask(3, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIPanic(this, 1.4D));
		//this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(4, new EntityAITempt(this, 1.0D, Items.fish, false));
		//this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		this.tasks.addTask(0, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(1, new EntityAILookIdle(this));
        
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

	@Override
	/*public EntityAgeable createChild(EntityAgeable p_90011_1_) {
		// TODO Auto-generated method stub
		return null;
	}    protected String getLivingSound()
    {
        return "mob.pig.say";
    }*/

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
