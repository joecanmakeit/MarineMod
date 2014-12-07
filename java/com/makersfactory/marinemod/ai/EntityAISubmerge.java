package com.makersfactory.marinemod.ai;

import com.makersfactory.marinemod.entity.EntityWhale;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityAISubmerge extends EntityAIBase {
	
	EntityWhale whale;
	
	public EntityAISubmerge(EntityWhale entity) {
		whale = entity;
		
		setMutexBits(3); //If you want to make it compatible with swimming, but not begging and watching closest you should set mutexBits to 3.
		// DEBUG
		System.out.println("EntityAIDive constructor()");
	}
	
	@Override
	public boolean shouldExecute() {
			if (this.whale.posY > 61) { // debug. TODO remove
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

	
			for (int try_cnt=1; try_cnt <= 5 && !found_path; ++try_cnt) {
				targetX = whale.posX + ((Math.random() * 10.0) - 5.0); // TODO is [-5.0, 5.0) good?
				targetY = whale.posY + ((Math.random() * 10.0) - 5.0); // TODO is [-5.0, 5.0) good?
				targetZ = whale.posZ + ((Math.random() * 20.0) - 10.0); // TODO is [-20.0, 20.0) good?
				this.whale.getNavigator().tryMoveToXYZ(targetX, targetY, targetZ, whale.swimSpeed);
				found_path = ! (this.whale.getNavigator().noPath());
				// DEBUG
				System.out.println("EntityAISwim startExecute(): noPath (" + targetX + "," + targetY + "," + targetZ +")?? try" + try_cnt + ": " + !found_path);
			}
		}
	

}