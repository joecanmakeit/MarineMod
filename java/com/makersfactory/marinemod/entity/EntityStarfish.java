package com.makersfactory.marinemod.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;

public class EntityStarfish extends EntityAnimal
{
    
    public EntityStarfish(World p_i1689_1_)
    {
        super(p_i1689_1_);
        this.setSize(0.9F, 0.9F);
        this.getNavigator().setAvoidsWater(false);
        this.tasks.addTask(0, new EntityAILookIdle(this));
        this.tasks.addTask(3, new EntityAIWander(this, 0.1D));
        //this.tasks.addTask(6, new EntityAISwimming(this));
        //this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        //this.tasks.addTask(2, this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3F));
        //this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
        //this.tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot_on_a_stick, false));
        //this.tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot, false));
        this.tasks.addTask(2, new EntityAIFollowParent(this, 1.1D));
        //this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.1D);
    }
    
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    protected void updateAITasks()
    {
        super.updateAITasks();
    }

    /**
     * Returns the sound this mob makes.
     */
    protected String getDeathSound()
    {
        return null;
    }

    protected String getLivingSound() {
    	return null;
    }
    
    protected String getHurtSound() {
    	return null;
    }
    
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.pig.step", 0.15F, 1.0F);
    }
    
    protected Item getDropItem() {
    	return Item.getItemById(0);
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
        int j = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + p_70628_2_);

        for (int k = 0; k < j; ++k) {
            if (!this.isBurning()) {
            	this.entityDropItem(new ItemStack(Items.dye, 1, 0), 0.0F);
            }
        }
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void onStruckByLightning(EntityLightningBolt p_70077_1_) {
        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }

    /**
     * Create new entity when bred. 
     */
    public EntityStarfish createChild(EntityAgeable p_90011_1_) {
        return new EntityStarfish(this.worldObj);
    }
    
    /**
     * Checks if this entity is inside water (if inWater field is true as a result of handleWaterMovement() returning
     * true)
     */
    public boolean isInWater()
    {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
    }
    
    public void onLivingUpdate() {
    	super.onLivingUpdate();
    	
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     * Also checks if animal is in water.
     */
    public boolean isBreedingItem(ItemStack p_70877_1_)
    {
    	
        return this.isInWater() && p_70877_1_.getItem() == Items.cookie;
    }
    
    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
//    public boolean interact(EntityPlayer p_70085_1_)
//    {
//        if (super.interact(p_70085_1_))
//        {
//            return true;
//        }
//        else if (this.getSaddled() && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == p_70085_1_))
//        {
//            p_70085_1_.mountEntity(this);
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }
    
    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
//    protected void fall(float p_70069_1_) {
//        super.fall(p_70069_1_);
//
//        if (p_70069_1_ > 5.0F && this.riddenByEntity instanceof EntityPlayer)
//        {
//            ((EntityPlayer)this.riddenByEntity).triggerAchievement(AchievementList.flyPig);
//        }
//    }
}
