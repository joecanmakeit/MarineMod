package com.makersfactory.marinemod.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

import com.makersfactory.marinemod.entity.EntityWhale;

public class EntityAISurfaceForAir extends EntityAIBase
{
    private EntityLiving theEntity;
    private static final String __OBFID = "CL_00001584";

    public EntityAISurfaceForAir(EntityLiving p_i1624_1_)
    {
        this.theEntity = p_i1624_1_;
        this.setMutexBits(4);
        p_i1624_1_.getNavigator().setCanSwim(true);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if(theEntity.posY > 61) return false;
        if(theEntity.posY < 49) 
        	while(theEntity.posY < 61) {
        		return true;
        	}
        return true;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if (this.theEntity.getRNG().nextFloat() < 0.8F)
        {
            this.theEntity.getJumpHelper().setJumping();
        }
    }
}