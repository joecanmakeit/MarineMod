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
		// DEBUG
		System.out.println("EntityAISwim startExecute()");
		
		/*startTime = (new Date()).getTime();
		startX = theEntity.posX;
		startY = theEntity.posY;
		startZ = theEntity.posZ;*/
		//int[] tmp = this.theEntity.posToBlockPos();
		double tagetX, tagetY, tagetZ; // where to head towards
		
		if (!this.theEntity.getBiome().equals(BiomeGenBase.beach)) {
			System.out.println("EntityAISwim: entity is not in the beach biome");
			System.out.println("Current Biome: " + this.theEntity.getBiome());
			System.out.println("Beach Biome: " + BiomeGenBase.beach);
			System.out.println(this.theEntity.getBiome() == BiomeGenBase.beach);
			// if you are out of the biome. move towards home (move 20% the distance from current position to spawn position).
			tagetX = theEntity.nearByBeachX + ((theEntity.posX - theEntity.nearByBeachX) * .2);
			tagetY = theEntity.nearByBeachY + ((theEntity.posY - theEntity.nearByBeachY) * .2);
			tagetZ = theEntity.nearByBeachZ + ((theEntity.posZ - theEntity.nearByBeachZ) * .2);
		} else {
			tagetX = Math.random() * 40.0; // TODO is 40.0 good?
			tagetY = Math.random() * 40.0; // TODO is 40.0 good?
			tagetZ = Math.random() * 40.0; // TODO is 40.0 good?
		}
		this.theEntity.getNavigator().tryMoveToXYZ(tagetX, tagetY, tagetZ, theEntity.swimSpeed);
	}

	@Override
	public boolean continueExecuting() {
		boolean continueExecuting = !this.theEntity.getNavigator().noPath();
		// DEBUG
		System.out.println("EntityAIHeadToBeach continueExecuting ="
				+ continueExecuting);
		return (continueExecuting);
	}
}
