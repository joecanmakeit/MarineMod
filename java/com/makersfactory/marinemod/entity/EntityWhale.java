package com.makersfactory.marinemod.entity;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

public class EntityWhale extends EntityAnimal {
	
	Random random = new Random();
	
	public EntityWhale(World par1World) {
		super(par1World);
        this.isImmuneToFire = true;

	}
    
    public void onUpdate()
    {
        super.onUpdate();
    }
    
    public boolean isAIEnabled()
    {
        return true;
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.01D);
    }

	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) {
		// TODO Auto-generated method stub
		return null;
	}    protected String getLivingSound()
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
