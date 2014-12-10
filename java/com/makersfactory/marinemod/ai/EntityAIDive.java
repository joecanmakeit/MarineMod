package com.makersfactory.marinemod.ai;

import com.makersfactory.marinemod.entity.EntityDolphin;
import com.makersfactory.marinemod.entity.EntityElephantSeal;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityAIDive extends EntityAIBase {
	
	EntityDolphin dolphin;
	int timer;
	
	public EntityAIDive(EntityDolphin entity) {
		dolphin = entity;
		timer = 0;
		setMutexBits(3); //If you want to make it compatible with swimming, but not begging and watching closest you should set mutexBits to 3.
		// DEBUG
		System.out.println("EntityAIDive constructor()");
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
		
		// DEBUG
		System.out.println("EntityAIDive startExecute()");

		//Taken from Entity AI Swim

		for (int try_cnt=1; try_cnt <= 5 && !found_path; ++try_cnt) {
				targetX = dolphin.posX + ((Math.random() * 30.0) - 15.0); // TODO is [-2.0, 2.0) good?
				targetY = dolphin.posY - (Math.random() * 20.0) + 30; // TODO is [30, 50) good?
				targetZ = dolphin.posZ + ((Math.random() * 30.0) - 15.0); // TODO is [-2.0, 2.0) good?
				this.dolphin.getNavigator().tryMoveToXYZ(targetX, targetY, targetZ, dolphin.swimSpeed);
				found_path = !(this.dolphin.getNavigator().noPath());
				// DEBUG
				System.out.println("EntityAIDive startExecute(): noPath (" + targetX + "," + targetY + "," + targetZ + ")" + "found_path:" + found_path);
			}
		}
	
	public boolean continueExecuting(){
		//after five seconds of diving, stop executing.
		//this is called every tick and there are 20 ticks/second. 100 ticks == 5 seconds
		++timer;
		if(timer >= 100)
		{
			timer=0;
			System.out.println("stop executing Dive");
			return false;	
		}
		else
		{
			System.out.println("continue executing Dive timer==" + timer);
			return true;
		}

	}
	
}
