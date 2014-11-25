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
		double tagetX, tagetY, tagetZ; // where to head towards
		
		// DEBUG
		System.out.println("EntityAISwim startExecute()");
		
		if (!this.theEntity.getBiome().equals(BiomeGenBase.beach)) {
			System.out.println("EntityAISwim: entity is not in the beach biome");
			System.out.println("Current Biome: " + this.theEntity.getBiome());
			System.out.println("Beach Biome: " + BiomeGenBase.beach);
			// if you are out of the biome. move towards home (move 20% the distance from current position to spawn position).
			tagetX = theEntity.nearByBeachX + ((theEntity.posX - theEntity.nearByBeachX) * .2);
			tagetY = theEntity.nearByBeachY + ((theEntity.posY - theEntity.nearByBeachY) * .2);
			tagetZ = theEntity.nearByBeachZ + ((theEntity.posZ - theEntity.nearByBeachZ) * .2);
		} else {
			tagetX = theEntity.posX + ((Math.random() * 40.0) - 20.0); // TODO is [-20.0, 20.0) good?
			tagetY = theEntity.posY + ((Math.random() * 40.0) - 20.0); // TODO is [-20.0, 20.0) good?
			tagetZ = theEntity.posZ + ((Math.random() * 40.0) - 20.0); // TODO is [-20.0, 20.0) good?
		}
		this.theEntity.getNavigator().tryMoveToXYZ(tagetX, tagetY, tagetZ, theEntity.swimSpeed);
		
		//DEBUG
		System.out.println("EntityAISwim startExecute(): noPath??  " + this.theEntity.getNavigator().noPath());
	}

	@Override
	public boolean continueExecuting() {
		boolean continueExecuting = !this.theEntity.getNavigator().noPath();
		// DEBUG
		System.out.println("EntityAISwim continueExecuting ="
				+ continueExecuting);
		return (continueExecuting);
	}
}
