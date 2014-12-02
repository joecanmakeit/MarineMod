package com.makersfactory.marinemod.ai;

import com.makersfactory.marinemod.entity.EntityDolphin;
import com.makersfactory.marinemod.entity.EntityElephantSeal;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.biome.BiomeGenBase;

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

//		Dolphin should always be in the water so we don't need to check
//		if (!this.dolphin.getBiome().equals(BiomeGenBase.beach)) {
//			System.out.println("EntityAISwim: entity is not in the beach biome");
//			System.out.println("Current Biome: " + this.dolphin.getBiome());
//			System.out.println("Beach Biome: " + BiomeGenBase.beach);
//			// if you are out of the biome. move towards home (move 20% the distance from current position to spawn position).
//			for (int try_cnt=1; try_cnt <= 5 && !found_path; ++try_cnt) {
//				targetX = dolphin.nearByBeachX + ((dolphin.posX - dolphin.nearByBeachX) * .2) + ((Math.random() * 10.0) - 5.0);
//				targetY = dolphin.nearByBeachY + ((dolphin.posY - dolphin.nearByBeachY) * .2) + ((Math.random() * 10.0) - 5.0);
//				targetZ = dolphin.nearByBeachZ + ((dolphin.posZ - dolphin.nearByBeachZ) * .2)+ ((Math.random() * 10.0) - 5.0);
//				this.dolphin.getNavigator().tryMoveToXYZ(targetX, targetY, targetZ, dolphin.swimSpeed);
//				found_path = ! (this.dolphin.getNavigator().noPath());
//				// DEBUG
//				System.out.println("EntityAISwim startExecute(): noPath (" + targetX + "," + targetY + "," + targetZ +")?? try" + try_cnt + ": " + !found_path);
//			}
//		} else {
			for (int try_cnt=1; try_cnt <= 5 && !found_path; ++try_cnt) {
				targetX = dolphin.posX + ((Math.random() * 10.0) - 5.0); // TODO is [-5.0, 5.0) good?
				targetY = dolphin.posY + ((Math.random() * 10.0) - 5.0); // TODO is [-5.0, 5.0) good?
				targetZ = dolphin.posZ + ((Math.random() * 40.0) - 20.0); // TODO is [-20.0, 20.0) good?
				this.dolphin.getNavigator().tryMoveToXYZ(targetX, targetY, targetZ, dolphin.swimSpeed);
				found_path = ! (this.dolphin.getNavigator().noPath());
				// DEBUG
				System.out.println("EntityAISwim startExecute(): noPath (" + targetX + "," + targetY + "," + targetZ +")?? try" + try_cnt + ": " + !found_path);
			}
		}
	

}
