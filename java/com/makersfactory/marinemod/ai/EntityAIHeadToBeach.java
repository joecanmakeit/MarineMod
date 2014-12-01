package com.makersfactory.marinemod.ai;

import com.makersfactory.marinemod.entity.EntityElephantSeal;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary; //Alternatively: import net.minecraftforge.common.*;
import static net.minecraftforge.common.BiomeDictionary.Type;

/** based off of the ai tutorial Desmond found.
 * http://jabelarminecraft.blogspot.com/p/minecraft-forge-1721710-custom-entity-ai.html
 * http://www.minecraftforge.net/forum/index.php?topic=18777.0
 * @author Desmond
 *
 */
public class EntityAIHeadToBeach extends EntityAIBase {
	private final EntityElephantSeal theEntity;
	
	public EntityAIHeadToBeach(EntityElephantSeal entity) {
		theEntity = entity;
		
		setMutexBits(5); // 1 because moving towards a target. 4 because swimming.

		// DEBUG
		System.out.println("EntityAIHeadToBeach constructor()");
	}

	@Override
	public boolean shouldExecute() {
		if (theEntity.wantsToBeach()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void startExecuting() {
		// DEBUG
		System.out.println("EntityAIHeadToBeach startExecute()");
		// Check that you can still get to the found sand
		PathEntity tmp = this.theEntity.getNavigator().getPathToXYZ(theEntity.nearByBeachX, theEntity.nearByBeachY, theEntity.nearByBeachZ);
		if (tmp == null || tmp.isFinished()) {
			System.out.println("Path to beach spot is no longer valid");
			int[] tmp_coords = this.theEntity.findSandNear((int)theEntity.nearByBeachX, (int)theEntity.nearByBeachY, (int)theEntity.nearByBeachZ, 10, 10, 10);
			tmp = this.theEntity.getNavigator().getPathToXYZ(tmp_coords[0],tmp_coords[1],tmp_coords[2]);
			if (tmp == null || tmp.isFinished()) {
				System.out.println("Failed to find new path");
				this.theEntity.getNavigator().clearPathEntity(); // so it will stop executing
			} else {
				// Go to the marked beach point
				this.theEntity.getNavigator().tryMoveToXYZ(theEntity.nearByBeachX, theEntity.nearByBeachY, theEntity.nearByBeachZ, theEntity.swimSpeed);
			}
		} else {
			// Go to the marked beach point
			this.theEntity.getNavigator().tryMoveToXYZ(theEntity.nearByBeachX, theEntity.nearByBeachY, theEntity.nearByBeachZ, theEntity.swimSpeed);
		}		
	}

	@Override
	public boolean continueExecuting() {
		boolean continueExecuting = !this.theEntity.getNavigator().noPath();//theEntity.isInWater();
		// DEBUG
		System.out.println("EntityAIHeadToBeach continueExecuting ="
				+ continueExecuting);
		return (continueExecuting);
	}
}
