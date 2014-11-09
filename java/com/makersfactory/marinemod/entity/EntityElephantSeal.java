package com.makersfactory.marinemod.entity;

import java.util.Date;
import java.util.Random;

import com.makersfactory.marinemod.ai.EntityAIHeadToBeach;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

/*
 * 
 * Based off of EntitySquid
 * Scientific Species: Mirounga Angustrirostris
 */
public class EntityElephantSeal extends EntityWaterMob
{
	public float sealPitch;
	public float prevSealPitch;
	public float sealYaw;
	public float prevSealYaw;
	/**
	 * appears to be rotation in radians; we already have pitch & yaw, so this
	 * completes the triumvirate.
	 */
	public float sealRotation;
	/** previous sealRotation in radians */
	public float prevSealRotation;
	
	private float randomMotionSpeed;
	/** change in sealRotation in radians. */
	private float rotationVelocity;
	private float field_70871_bB;
	private float randomMotionVecX;
	private float randomMotionVecY;
	private float randomMotionVecZ;

	protected String scientificName;
	// variables for state / AI
	protected EntityAIBase aiHeadToBeach;
	protected boolean isOnBeach;
	/** expressed in epoch time (milliseconds) as returned by java.util.Date.getTime() **/
	protected long lastTimeEnteredBeach;
	protected boolean isSwimming;
	
	public double spawnX;
	public double spawnY;
	public double spawnZ;
	public double swimSpeed;
	
	public EntityElephantSeal(World p_i1695_1_)
	{
		super(p_i1695_1_);
		// this.setSize(0.95F, 0.95F); // todo adjust these numbers
		this.scientificName = "Mirounga Angustrirostris";
		this.rotationVelocity = 0;
		this.setupAI();
		this.spawnX = this.posX;
		this.spawnY = this.posY;
		this.spawnZ = this.posZ;
		this.swimSpeed = 1.0D; // TODO set appropriately
	}

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled()
    {
        return true;
    }
    
    // set up AI tasks
    protected void setupAI()
    {
       getNavigator().setAvoidsWater(false);
       clearAITasks(); // clear any tasks assigned in super classes
       //good to have instances of AI so task list can be modified, including in subclasses
       aiHeadToBeach = new EntityAIHeadToBeach(this);
       /*protected EntityAIBase aiSwimming = new EntityAISwimming(this);
       protected EntityAIBase aiLeapAtTarget = new EntityAILeapAtTarget(this, 0.4F);
       protected EntityAIBase aiAttackOnCollide = new EntityAIAttackOnCollide(this, 
             1.0D, true);
       protected EntityAIBase aiFollowOwner = new EntityAIFollowOwner(this, 1.0D, 10.0F, 
             2.0F);
       protected EntityAIBase aiMate = new EntityAIMate(this, 1.0D);
       protected EntityAIBase aiWander = new EntityAIWander(this, 1.0D);
       protected EntityAIBase aiBeg = new EntityAIBegBigCat(this, 8.0F); 
       protected EntityAIBase aiWatchClosest = new EntityAIWatchClosest(this, 
             EntityPlayer.class, 8.0F);
       protected EntityAIBase aiLookIdle = new EntityAILookIdle(this);
       protected EntityAIBase aiOwnerHurtByTarget = new EntityAIOwnerHurtByTarget(this);
       protected EntityAIBase aiOwnerHurtTarget = new EntityAIOwnerHurtTarget(this);
       protected EntityAIBase aiHurtByTarget = new EntityAIHurtByTarget(this, true);
       protected EntityAIBase aiTargetNonTamedSheep = new EntityAITargetNonTamed(this, 
             EntitySheep.class, 200, false);
       protected EntityAIBase aiTargetNonTamedCow = new EntityAITargetNonTamed(this, 
             EntityCow.class, 200, false);
       protected EntityAIBase aiTargetNonTamedPig = new EntityAITargetNonTamed(this, 
             EntityPig.class, 200, false);
       protected EntityAIBase aiTargetNonTamedChicken = new EntityAITargetNonTamed(this, 
             EntityChicken.class, 200, false);
       protected EntityAIBase aiTargetNonTamedHerdAnimal = new EntityAITargetNonTamed(this, 
             EntityHerdAnimal.class, 200, false);  */
       
       /*tasks.addTask(0, new EntityAISwimming(this));
       tasks.addTask(1, new EntityAIPanicHerdAnimal(this));
       // the leap and the collide together form an actual attack
       tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
       tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, true));
       tasks.addTask(5, new EntityAIMate(this, 1.0D));
       tasks.addTask(6, new EntityAITempt(this, 1.25D, Items.wheat, false));
       tasks.addTask(7, new EntityAIFollowParent(this, 1.25D));
       tasks.addTask(8, new EntityAIWander(this, 1.0D));
       tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
       tasks.addTask(10, new EntityAILookIdle(this));
       targetTasks.addTask(0, new EntityAIHurtByTargetHerdAnimal(this, true)); */
       
       tasks.addTask(8, aiHeadToBeach);
    }

    protected void clearAITasks()
    {
       tasks.taskEntries.clear();
       targetTasks.taskEntries.clear();
    }
	
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		//this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);  // todo set in children
	}

	
	// protected String getLivingSound() { // todo set in children
	// protected String getHurtSound() { // todo set in children
	// protected String getDeathSound() { // todo set in children
	// protected float getSoundVolume() { // todo set in children

	protected Item getDropItem()
	{ // todo remove?
		return Item.getItemById(0);
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	protected boolean canTriggerWalking() {
		return false;
	}

	// protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) { // todo change in children
	// public boolean isInWater() { // todo change in children / how to adjust this properly for children

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		this.prevSealPitch = this.sealPitch;
		this.prevSealYaw = this.sealYaw;
		this.prevSealRotation = this.sealRotation;
		this.sealRotation += this.rotationVelocity;

		if (this.sealRotation > ((float) Math.PI * 2F)) { // if rotation angle > 2pi
			this.sealRotation -= ((float) Math.PI * 2F); // rotation angle -= 2pi 

			if (this.rand.nextInt(10) == 0) { // 10% chance // TODO change?
				this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F; // rotation velocity = [.105,.2]// TODO change?
			}
		}
/*
		if (this.isInWater()) {
			float f;

			if (this.squidRotation < (float) Math.PI) { // if not rotated upside down
				f = this.squidRotation / (float) Math.PI; // f = % rotated 

				if ((double) f > 0.75D) { // if > 75% rotated
					this.randomMotionSpeed = 1.0F;
					this.field_70871_bB = 1.0F;
				} else {
					this.field_70871_bB *= 0.8F;
				}
			} else {
				this.tentacleAngle = 0.0F;
				this.randomMotionSpeed *= 0.9F;
				this.field_70871_bB *= 0.99F;
			}

			if (!this.worldObj.isRemote) {
				this.motionX = (double) (this.randomMotionVecX * this.randomMotionSpeed);
				this.motionY = (double) (this.randomMotionVecY * this.randomMotionSpeed);
				this.motionZ = (double) (this.randomMotionVecZ * this.randomMotionSpeed);
			}

			f = MathHelper.sqrt_double(this.motionX * this.motionX
					+ this.motionZ * this.motionZ);
			this.renderYawOffset += (-((float) Math.atan2(this.motionX,
					this.motionZ)) * 180.0F / (float) Math.PI - this.renderYawOffset) * 0.1F;
			this.rotationYaw = this.renderYawOffset;
			this.squidYaw += (float) Math.PI * this.field_70871_bB * 1.5F;
			this.squidPitch += (-((float) Math.atan2((double) f, this.motionY))
					* 180.0F / (float) Math.PI - this.squidPitch) * 0.1F;
		} else {
			this.tentacleAngle = MathHelper.abs(MathHelper
					.sin(this.squidRotation)) * (float) Math.PI * 0.25F;

			if (!this.worldObj.isRemote) {
				this.motionX = 0.0D;
				this.motionY -= 0.08D;
				this.motionY *= 0.9800000190734863D;
				this.motionZ = 0.0D;
			}

			this.squidPitch = (float) ((double) this.squidPitch + (double) (-90.0F - this.squidPitch) * 0.02D);
		}
		*/
	}

	/**
	 * Moves the entity based on the specified heading. Args: strafe, forward
	 */
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
	}

	protected void updateEntityActionState()
	{
		++this.entityAge;
/*
		if (this.entityAge > 100) {
			this.randomMotionVecX = this.randomMotionVecY = this.randomMotionVecZ = 0.0F;
		} else if (this.rand.nextInt(50) == 0 || !this.inWater
				|| this.randomMotionVecX == 0.0F
				&& this.randomMotionVecY == 0.0F
				&& this.randomMotionVecZ == 0.0F) {
			float f = this.rand.nextFloat() * (float) Math.PI * 2.0F;
			this.randomMotionVecX = MathHelper.cos(f) * 0.2F;
			this.randomMotionVecY = -0.1F + this.rand.nextFloat() * 0.2F;
			this.randomMotionVecZ = MathHelper.sin(f) * 0.2F;
		}
*/
		this.despawnEntity();
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	public boolean getCanSpawnHere()
	{
		return this.posY > 45.0D && this.posY < 63.0D
				&& super.getCanSpawnHere(); // TODO change. Where do these number come from?
	}

	public boolean isOnBeach()
	{
		return this.isOnBeach;
	}

	/** Occasionally beach: "The northern elephant seals are nocturnal deep feeders famous for the long time intervals they remain underwater." - http://en.wikipedia.org/wiki/Northern_elephant_seal
	 * 	  if it is currently not beached...
	 * 	    and it has been at least 6 minutes since the last time beaching: beach 10% likely
	 * 		and it has been at least 9 minutes since the last time beaching: beach 15% likely
	 *		and it has been at least 12 minutes since the last time beaching: beach 20% likely
	 * @return
	 */
	public boolean wantsToBeach()
	{
		if (isOnBeach()) {
			return false;
		}
		Date curr_date = new Date();
		
		long time_since_last_beach = curr_date.getTime() - lastTimeEnteredBeach;
		double threshold_percentage = 1.0;
		if (time_since_last_beach > 720000) { // 12 minutes
			threshold_percentage = 0.2;
		} else if (time_since_last_beach > 540000) { // 9 minutes
			threshold_percentage = 0.15;
		} else if (time_since_last_beach > 360000) { // 6 minutes
			threshold_percentage = 0.1;
		}
		
		return (Math.random() <= threshold_percentage);
	}

	public void setSwimming(boolean swim_val)
	{
		isSwimming = swim_val;
	}
	
	// return the block under the entity
//	public Block findBlockUnder()
//	{
//	    int blockX = MathHelper.floor_double(this.posX);
//	    int blockY = MathHelper.floor_double(this.boundingBox.minY)-1;
//	    int blockZ = MathHelper.floor_double(this.posZ);
//	    return this.worldObj.getBlock(blockX, blockY, blockZ);
//	}
	
	public int[] posToBlockPos() {
		int[] blockPositions = new int[2]; // [x,z]
		
		int blockX = MathHelper.floor_double(this.posX);
	    int blockY = MathHelper.floor_double(this.boundingBox.minY)-1;
	    int blockZ = MathHelper.floor_double(this.posZ);
		
	    if (blockX >= -30000000 && blockZ >= -30000000 && blockX < 30000000 && blockZ < 30000000 && blockY >= 0 && blockY < 256) {
			blockPositions[0] = blockX;
			blockPositions[1] = blockZ;
			return blockPositions;
		} else {
			return null;
		}
	}
}