package com.makersfactory.marinemod.ai;

import com.makersfactory.marinemod.entity.EntityDolphin;
import com.makersfactory.marinemod.entity.EntityElephantSeal;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityAIDive extends EntityAIBase {
	
	EntityDolphin dolphin;
	int timer;
	boolean navToSurface;
	boolean surfacing;
	
	public EntityAIDive(EntityDolphin entity) {
		dolphin = entity;
		timer = 0;
		navToSurface = false;
		surfacing = false;
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
				targetY = 50; // TODO is 50 good?
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
		if(dolphin.posY > 51 && !surfacing)
		{
			System.out.println("continue executing Dive");
			System.out.println("is there no path?" + this.dolphin.getNavigator().noPath());
			if(this.dolphin.getNavigator().noPath())
				return false;
			else
				return true;	
		}
		else
		{
			if(!navToSurface)
			{	
				double targetX, targetY, targetZ; // where to head towards
				boolean found_path = false; // could we find a path to swim to?
				
				for (int try_cnt=1; try_cnt <= 5 && !found_path; ++try_cnt) {
					targetX = dolphin.posX + ((Math.random() * 30.0) - 15.0); // TODO is [-2.0, 2.0) good?
					targetY = 58; // TODO is 58 good?
					targetZ = dolphin.posZ + ((Math.random() * 30.0) - 15.0); // TODO is [-2.0, 2.0) good?
					this.dolphin.getNavigator().tryMoveToXYZ(targetX, targetY, targetZ, dolphin.swimSpeed);
					found_path = !(this.dolphin.getNavigator().noPath());
					// DEBUG
					System.out.println("attempt to Surface: noPath (" + targetX + "," + targetY + "," + targetZ + ")" + "found_path:" + found_path);
				}
				navToSurface = true;
				surfacing = true;
			}
			
			System.out.println("surfacing");
			System.out.println("is there no path?" + this.dolphin.getNavigator().noPath());
			
			
			if(surfacing && dolphin.posY >= 58)
			{
				System.out.println("returning false");
				return false;
			}
			else
			{
				if(this.dolphin.getNavigator().noPath())
					return false;
				else
					return true;
			}
		}

	}
	
}
