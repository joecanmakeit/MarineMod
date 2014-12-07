package com.makersfactory.marinemod.ai;

import com.makersfactory.marinemod.entity.EntityDolphin;

import net.minecraft.entity.ai.EntityAIBase;

public class EntityAISurface extends EntityAIBase {

	EntityDolphin dolphin;
	int timer;

	public EntityAISurface(EntityDolphin entity) {
		dolphin = entity;
		timer = 0;
		setMutexBits(3); //If you want to make it compatible with swimming, but not begging and watching closest you should set mutexBits to 3.
		// DEBUG
		System.out.println("EntityAISurfacee constructor()");
	}
	
	@Override
	public boolean shouldExecute() {
		
		if (this.dolphin.isInWater() && Math.random() > 0.1D) { // debug. TODO remove
			return true;
		} else {
			return false;
		}

	}
	
	@Override
	public void startExecuting() {
		double targetX, targetY, targetZ; // where to head towards
		boolean found_path = false; // could we find a path to swim to?
	
		for (int try_cnt=1; try_cnt <= 5 && !found_path; ++try_cnt) {
			targetX = dolphin.posX + ((Math.random() * 30.0) - 15.0); // TODO is [-2.0, 2.0) good?
			targetY = 60; // TODO is [0, 20.0) good?
			targetZ = dolphin.posZ + ((Math.random() * 30.0) - 15.0); // TODO is [-2.0, 2.0) good?
			this.dolphin.getNavigator().tryMoveToXYZ(targetX, targetY, targetZ, dolphin.swimSpeed);
			found_path = !(this.dolphin.getNavigator().noPath());
			// DEBUG
			System.out.println("EntityAISurface startExecute(): noPath (" + targetX + "," + targetY + "," + targetZ + ")" + "found_path:" + found_path);
		}

	}
	
	public boolean continueExecuting(){		
		//are we at sea level?
		double y = this.dolphin.posY;
		System.out.println("dolphin.posY == " + y);
		if(y==60)
		{
			System.out.println("stop executing Surface");
			return false;
		}
		else
		{
			System.out.println("continue executing Surface");
			return true;
		}
	}
}
