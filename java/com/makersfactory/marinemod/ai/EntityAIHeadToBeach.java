package com.makersfactory.marinemod.ai;

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
	
	private double entityLastX;
	private double entityLastY;
	private double entityLastZ;
	/** in continueExecute we check to see if the entity has moved when waitTicksBeforeChecking = 0 **/
	private int waitTicksBeforeChecking;
	
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
		PathEntity tmp_path = this.theEntity.getNavigator().getPathToXYZ(theEntity.nearByBeachX, theEntity.nearByBeachY, theEntity.nearByBeachZ);
		if (tmp_path == null || tmp_path.isFinished()) {
			System.out.println("Path to beach spot is no longer valid");
			int[] tmp_coords = this.theEntity.findSandNear((int)theEntity.nearByBeachX, (int)theEntity.nearByBeachY, (int)theEntity.nearByBeachZ, 10, 10, 10);
			tmp_path = this.theEntity.getNavigator().getPathToXYZ(tmp_coords[0],tmp_coords[1],tmp_coords[2]);
			if (tmp_path == null || tmp_path.isFinished()) {
				System.out.println("Failed to find new path");
				this.theEntity.getNavigator().clearPathEntity(); // so it will stop executing
			} else {
				// Go to the marked beach point
				this.theEntity.getNavigator().setPath(tmp_path, theEntity.swimSpeed);
			}
		} else {
			// Go to the marked beach point
			this.theEntity.getNavigator().setPath(tmp_path, theEntity.swimSpeed);
		}
		
		this.entityLastX = this.theEntity.posX;
		this.entityLastY = this.theEntity.posY;
		this.entityLastZ = this.theEntity.posZ;
		this.waitTicksBeforeChecking = 0;
	}

	@Override
	public boolean continueExecuting() {
		boolean continueExecuting = !this.theEntity.getNavigator().noPath();//theEntity.isInWater();
		double currEntityX = this.theEntity.posX;
		double currEntityY = this.theEntity.posY;
		double currEntityZ = this.theEntity.posZ;
		
		if (continueExecuting) {
			/* hack: the entity's bounding box or something else is hard to set correctly.
			 * The mechanism in which the pathfinding uses to determine when to target 
			 * the next waypoint doesn't seem to work. We manually handle that here. 
			 * TODO make the entity function with regular waypoint code.
			 */
			int currentPointIdx = this.theEntity.getNavigator().getPath().getCurrentPathIndex();
			PathPoint currentPoint = this.theEntity.getNavigator().getPath().getPathPointFromIndex(currentPointIdx);
			Vec3 entityPos = this.theEntity.getPosition(1.0F);                                                                       
			Vec3 currentPointPos = Vec3.createVectorHelper(currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord);
			double pointDistance = currentPointPos.distanceTo(entityPos);
			if (this.theEntity.boundingBox.isVecInside(currentPointPos)) {
			//if (pointDistance < 0.2) {
				// pretty much at the point
				this.theEntity.getNavigator().getPath().incrementPathIndex();
				waitTicksBeforeChecking = 40;
				System.out.println("hit the point"        );
			} else if (waitTicksBeforeChecking == 0) {
				double diffEntityX = Math.abs(this.theEntity.posX - entityLastX);
				double diffEntityY = Math.abs(this.theEntity.posY - entityLastY);
				double diffEntityZ = Math.abs(this.theEntity.posZ - entityLastZ);
				if ( (diffEntityX <= 0.1) && (diffEntityY <= 0.1) && (diffEntityZ <= 0.1) ) {
					
					///////////// vv TODO remove debug ////////////////
					System.out.println("No progress has been made. Try moving up..." + pointDistance + "   entity pos:" + entityPos.xCoord + " | " + entityPos.yCoord + " | " + entityPos.zCoord);
					///////////// ^^ TODO remove debug ////////////////
					
					// if we have barely moved we are stuck. have the entity move up one block then continue on its path.
					int totalNumberOfPoints = this.theEntity.getNavigator().getPath().getCurrentPathLength();
					//int currentPointIdx = this.theEntity.getNavigator().getPath().getCurrentPathIndex();
					int pointsLeft = totalNumberOfPoints - currentPointIdx;
					
					///////////// vv TODO remove debug ////////////////
					String old = "old path: ";
					for (int i=currentPointIdx; i < this.theEntity.getNavigator().getPath().getCurrentPathLength(); ++i) {
						PathPoint pp = this.theEntity.getNavigator().getPath().getPathPointFromIndex(i);
						old += "(" + pp.xCoord + "," + pp.yCoord + "," + pp.zCoord + ") ";
					}
					System.out.println(old);
					///////////// ^^ TODO remove debug ////////////////
					
					this.theEntity.modifyPathForBeach(3);
					
					///////////// vv TODO remove debug ////////////////
					String newnew = "new path: ";
					for (int i=0; i < this.theEntity.getNavigator().getPath().getCurrentPathLength(); ++i) {
						PathPoint pp = this.theEntity.getNavigator().getPath().getPathPointFromIndex(i);
						newnew += "(" + pp.xCoord + "," + pp.yCoord + "," + pp.zCoord + ") ";
					}
					System.out.println(newnew);
					///////////// ^^ TODO remove debug ////////////////
					
					waitTicksBeforeChecking = 50;
				} else {
					// only do on the else to give the new path more time to kick in so the entity will move
					entityLastX = currEntityX;
					entityLastY = currEntityY;
					entityLastZ = currEntityZ;
				}
			} else {
				//System.out.println("dist: " + pointDistance + "   entity pos:" + entityPos.xCoord + " | " + entityPos.yCoord + " | " + entityPos.zCoord);
				--waitTicksBeforeChecking;
			}
		}
		
		// DEBUG
		//System.out.println("EntityAIHeadToBeach continueExecuting ="
		//		+ continueExecuting);
		return (continueExecuting);
	}
}
