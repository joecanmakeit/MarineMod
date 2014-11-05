package com.makersfactory.marinemod.ai;

import java.util.Date;

import com.makersfactory.marinemod.entity.EntityElephantSeal;

import net.minecraft.entity.ai.EntityAIBase;

/** based off of the ai tutorial Desmond found.
 * http://jabelarminecraft.blogspot.com/p/minecraft-forge-1721710-custom-entity-ai.html
 * http://www.minecraftforge.net/forum/index.php?topic=18777.0
 * @author Desmond
 *
 */
public class EntityAISwim extends EntityAIBase {
	private final EntityElephantSeal theEntity;
	private final double startX;
	private final double startY;
	private final double startZ;
	private final long startTime = (new Date()).getTime();
	
	public EntityAISwim(EntityElephantSeal entity) {
		theEntity = entity;
		startX = theEntity.posX;
		startY = theEntity.posY;
		startZ = theEntity.posZ;
		
		setMutexBits(5); // 1 because moving towards a target spot? 4 because of swimming.

		// DEBUG
		System.out.println("EntityAISwim constructor()");
	}

	@Override
	public boolean shouldExecute() {
		// Occasionally do nothing. TODO correct?
		if (Math.random() > 0.1D) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void startExecuting() {
		// DEBUG
		System.out.println("EntityAISwim startExecute()");
	}

	/** stop when have swam for about 200 or when have swam for 2 minutes
	 * TODO is 200 a good threshold?  **/
	@Override
	public boolean continueExecuting() {
		boolean continueExecuting = true;
		
		// note distance just checking farthest point (another reason for using time)
		double x_diff = theEntity.posX - this.startX;
		double y_diff = theEntity.posY - this.startY;
		double z_diff = theEntity.posZ - this.startZ;
		double distanceTraveled = Math.sqrt(Math.pow(x_diff,2) + Math.pow(y_diff,2) + Math.pow(z_diff,2));
		if (distanceTraveled > 200) {
			continueExecuting = false;
		}
			
		if ((new Date()).getTime() - startTime > 120000) {
			continueExecuting = false;
		}
		
		if (!continueExecuting) {
			theEntity.setSwimming(false);
			// TODO set states approperiately
		}
		// DEBUG
		System.out.println("EntityAIHeadToBeach continueExecuting ="
				+ continueExecuting);
		return (continueExecuting);
	}
}
