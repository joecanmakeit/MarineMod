package com.makersfactory.marinemod.ai;

import com.makersfactory.marinemod.entity.EntityDolphin;
import com.makersfactory.marinemod.entity.EntityElephantSeal;

import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIDive extends EntityAIBase {
	
	EntityDolphin dolphin;
	
	public EntityAIDive(EntityDolphin entity) {
		dolphin = entity;
		
		setMutexBits(3); //If you want to make it compatible with swimming, but not begging and watching closest you should set mutexBits to 3.
		// DEBUG
		System.out.println("EntityAIDive constructor()");
	}
	
	@Override
	public boolean shouldExecute() {
		// TODO Auto-generated method stub
		return false;
	}

}
