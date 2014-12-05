package com.makersfactory.marinemod.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.world.World;

public class EntityShark extends EntityWaterMob {

	public EntityShark(World p_i1693_1_) {
		super(p_i1693_1_);
		this.getNavigator().setAvoidsWater(false);
        //this.tasks.addTask(0, new EntityAILookIdle(this));
        this.tasks.addTask(3, new EntityAIWander(this, 0.1D));
	}

	@Override
//	public EntityAgeable createChild(EntityAgeable p_90011_1_) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
    public boolean canBreatheUnderwater()
    {
        return true;
    }

}
