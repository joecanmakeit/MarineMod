package com.makersfactory.marinemod.ai;

import com.makersfactory.marinemod.entity.EntityElephantSeal;
import com.makersfactory.marinemod.entity.EntityWhale;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAISwimming;
import java.util.Timer;
import java.util.TimerTask;


public class EntityAISurface extends EntityAIBase {
	
	private final EntityWhale theEntity;
    Timer timer = new Timer();
    
	public EntityAISurface(EntityWhale entity) {
		theEntity = entity;
		
		setMutexBits(1); // 1 because moving towards a target spot? 4 because of swimming.
		System.out.println("EntityAISwim constructor()");
	}

	public boolean shouldExecute() {
		//if (this.theEntity.isInWater() && Math.random() > 0.7D) { // original
		if (this.theEntity.isInWater() && Math.random() > 0.1D) { // debug. TODO remove
			return true;
		} else {
			return false;
		}
	}
}
