package com.makersfactory.marinemod.ai;

import java.util.Stack;

import com.makersfactory.marinemod.entity.EntityElephantSeal;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
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
	private Vec3 entityLastPos;
	/** in continueExecute we check to see if the entity has moved when waitTicksBeforeChecking = 0 **/
	private int ticks;
	private Stack<Vec3> targets;
	
	
	public EntityAIHeadToBeach(EntityElephantSeal entity) {
		theEntity = entity;
		this.ticks = 0;
		
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
		System.out.println("EntityAIHeadToBeach startExecute()"); // DEBUG
		
		boolean tryToMove = true;
		this.theEntity.getNavigator().clearPathEntity(); // clear old path
		// Check that you can still get to the found sand
		PathEntity tmp_path = this.theEntity.getNavigator().getPathToXYZ(theEntity.nearByBeachX, theEntity.nearByBeachY, theEntity.nearByBeachZ);
		if (tmp_path == null || tmp_path.isFinished()) {
			System.out.println("Path to beach spot is no longer valid");
			int[] tmp_coords = this.theEntity.findSandNear((int)theEntity.nearByBeachX, (int)theEntity.nearByBeachY, (int)theEntity.nearByBeachZ, 10, 10, 10);
			tmp_path = this.theEntity.getNavigator().getPathToXYZ(tmp_coords[0],tmp_coords[1],tmp_coords[2]);
			if (tmp_path == null || tmp_path.isFinished()) {
				System.out.println("Failed to find new path");
				tryToMove = false;
			} else {
				System.out.println("Updated to new sand");
				theEntity.nearByBeachX = tmp_coords[0];
				theEntity.nearByBeachY = tmp_coords[1];
				theEntity.nearByBeachZ = tmp_coords[2];
			}
		}
		
		if (tryToMove) {
			this.targets = new Stack<Vec3>();
			this.targets.push(Vec3.createVectorHelper(theEntity.nearByBeachX, theEntity.nearByBeachY, theEntity.nearByBeachZ));
			this.targets.push(Vec3.createVectorHelper(theEntity.nearByBeachX, theEntity.nearByBeachY, theEntity.nearByBeachZ));
			this.theEntity.getNavigator().tryMoveToXYZ(theEntity.nearByBeachX, theEntity.nearByBeachY, theEntity.nearByBeachZ, this.theEntity.swimSpeed);
		} else {
			this.targets = null;
		}

		this.entityLastPos = null;
	}

	@Override
	public boolean continueExecuting() {
		if (this.targets == null) return false;
		
		boolean continue_executing = true;
		this.ticks++;
		Vec3 entity_curr_position = this.theEntity.getPosition(1.0F);
		
		if (this.ticks % 16 == 0 && this.entityLastPos != null && entity_curr_position.distanceTo(this.entityLastPos) < 0.1) {
			System.out.println("Entity: " + entity_curr_position);
    		System.out.println("Last  : " + this.entityLastPos);
    		System.out.println("Dest  : " + this.targets.peek());
    		System.out.println("Ticks : " + this.ticks);
			if (this.targets.size() == 1) {
    			System.out.println("Stuck but at the last point on the path: Finished Path");
    			continue_executing = false;
			} else {
				System.out.println("Stuck: poping");
				this.targets.pop();
			}
		}
		
		if (continue_executing) {
			this.entityLastPos = entity_curr_position;
			Vec3 curr_target = this.targets.peek();
			if (! this.theEntity.getNavigator().tryMoveToXYZ(curr_target.xCoord, curr_target.yCoord, curr_target.zCoord, this.theEntity.swimSpeed)) {
				Vec3 diff = entity_curr_position.subtract(curr_target);
				Vec3 midPoint = Vec3.createVectorHelper(diff.xCoord * 0.5, diff.yCoord * 0.5, diff.zCoord * 0.5);
				Vec3 newTarget = midPoint.addVector(Math.random(), Math.random(), Math.random());
				System.out.println("Couldn't get a path to " + curr_target + " Trying a closer point " + newTarget);
				this.targets.push(newTarget);
			}
		}
		
		return (continue_executing);
	}
}

