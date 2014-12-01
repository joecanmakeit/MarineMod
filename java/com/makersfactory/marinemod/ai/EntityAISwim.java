package com.makersfactory.marinemod.ai;

import java.util.Date;

import com.makersfactory.marinemod.entity.EntityElephantSeal;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;

/** based off of the ai tutorial Desmond found.
 * http://jabelarminecraft.blogspot.com/p/minecraft-forge-1721710-custom-entity-ai.html
 * http://www.minecraftforge.net/forum/index.php?topic=18777.0
 * @author Desmond
 *
 */
public class EntityAISwim extends EntityAIBase {
	private final EntityElephantSeal theEntity;
	/*private double startX;
	private double startY;
	private double startZ;
	private long startTime; */
	private double lastBeachBiomeX;
	private double lastBeachBiomeY;
	private double lastBeachBiomeZ;
	
	public EntityAISwim(EntityElephantSeal entity) {
		theEntity = entity;
		
		setMutexBits(5); // 1 because moving towards a target spot? 4 because of swimming.
		// DEBUG
		System.out.println("EntityAISwim constructor()");
	}

	@Override
	public boolean shouldExecute() {
		//if (this.theEntity.isInWater() && Math.random() > 0.7D) { // original
		if (this.theEntity.isInWater() && Math.random() > 0.1D) { // debug. TODO remove
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
		System.out.println("EntityAISwim startExecute()");
		
		if (!this.theEntity.getBiome().equals(BiomeGenBase.beach)) {
			System.out.println("EntityAISwim: entity is not in the beach biome");
			System.out.println("Current Biome: " + this.theEntity.getBiome());
			System.out.println("Beach Biome: " + BiomeGenBase.beach);
			// if you are out of the biome. move towards home (move 20% the distance from current position to spawn position).
			for (int try_cnt=1; try_cnt <= 5 && !found_path; ++try_cnt) {
				targetX = theEntity.nearByBeachX + ((theEntity.posX - theEntity.nearByBeachX) * .2) + ((Math.random() * 10.0) - 5.0);
				targetY = theEntity.nearByBeachY + ((theEntity.posY - theEntity.nearByBeachY) * .2) + ((Math.random() * 10.0) - 5.0);
				targetZ = theEntity.nearByBeachZ + ((theEntity.posZ - theEntity.nearByBeachZ) * .2)+ ((Math.random() * 10.0) - 5.0);
				this.theEntity.getNavigator().tryMoveToXYZ(targetX, targetY, targetZ, theEntity.swimSpeed);
				found_path = ! (this.theEntity.getNavigator().noPath());
				// DEBUG
				System.out.println("EntityAISwim startExecute(): noPath (" + targetX + "," + targetY + "," + targetZ +")?? try" + try_cnt + ": " + !found_path);
			}
		} else {
			for (int try_cnt=1; try_cnt <= 5 && !found_path; ++try_cnt) {
				targetX = theEntity.posX + ((Math.random() * 40.0) - 20.0); // TODO is [-20.0, 20.0) good?
				targetY = theEntity.posY + ((Math.random() * 40.0) - 20.0); // TODO is [-20.0, 20.0) good?
				targetZ = theEntity.posZ + ((Math.random() * 40.0) - 20.0); // TODO is [-20.0, 20.0) good?
				this.theEntity.getNavigator().tryMoveToXYZ(targetX, targetY, targetZ, theEntity.swimSpeed);
				found_path = ! (this.theEntity.getNavigator().noPath());
				// DEBUG
				System.out.println("EntityAISwim startExecute(): noPath (" + targetX + "," + targetY + "," + targetZ +")?? try" + try_cnt + ": " + !found_path);
			}
		}
	}

	@Override
	public boolean continueExecuting() {
		boolean continueExecuting = !this.theEntity.getNavigator().noPath();
		// TODO maybe add some code that will chek if you actually made progress?
		// DEBUG
		System.out.println("EntityAISwim continueExecuting ="
				+ continueExecuting);
		return (continueExecuting);
	}
}
