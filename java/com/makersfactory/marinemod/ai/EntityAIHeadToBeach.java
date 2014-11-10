package com.makersfactory.marinemod.ai;

import com.makersfactory.marinemod.entity.EntityElephantSeal;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
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
		// Find path to the spawn beach
		this.theEntity.getNavigator().tryMoveToXYZ(theEntity.spawnX, theEntity.spawnY, theEntity.spawnZ, theEntity.swimSpeed);
		//BiomeGenBase.getBiomeGenArray();
		//BiomeGenBase[] biomes = BiomeDictionary.getBiomesForType(Type.BEACH);	
		//this.entityObj.worldObj.villageCollectionObj.findNearestVillage(i, j, k, 14);
	}

	@Override
	public boolean continueExecuting() {
//		boolean continueExecuting = theEntity.isOnBeach();
//		if (!continueExecuting) {
//			theEntity.setSwimming(false);
//		}
		boolean continueExecuting = !this.theEntity.getNavigator().noPath();//theEntity.isInWater();
		// DEBUG
		System.out.println("EntityAIHeadToBeach continueExecuting ="
				+ continueExecuting);
		return (continueExecuting);
	}
}
