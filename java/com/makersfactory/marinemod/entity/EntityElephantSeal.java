package com.makersfactory.marinemod.entity;

import java.util.Date;
import java.util.Random;

import com.makersfactory.marinemod.ai.EntityAIHeadToBeach;
import com.makersfactory.marinemod.ai.EntityAISwim;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
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
	protected EntityAIBase aiMySwim;
	protected EntityAIBase aiWander;
	protected boolean isOnBeach;
	/** expressed in epoch time (milliseconds) as returned by java.util.Date.getTime() **/
	protected long lastTimeEnteredBeach;
	
	public double spawnX;
	public double spawnY;
	public double spawnZ;
	/** X coordinate of the sand part of a nearby beach the seal will occasionally go to **/
	public double nearByBeachX;
	/** Y coordinate of the sand part of a nearby beach the seal will occasionally go to **/
	public double nearByBeachY;
	/** Z coordinate of the sand part of a nearby beach the seal will occasionally go to **/
	public double nearByBeachZ;
	public double swimSpeed;
	
	public EntityElephantSeal(World p_i1695_1_)
	{
		super(p_i1695_1_);
		System.out.println("EntityElephantSeal: Ctor");
		this.setSize(0.5F, 1.0F); // todo adjust these numbers by looking at F3 + B (shows bounding box) - Desmond can't do this easily :/
		this.scientificName = "Mirounga Angustrirostris";
		this.rotationVelocity = 0;
		this.setupAI();
		this.swimSpeed = 1.0D; // TODO set appropriately
		this.spawnX = this.posX;
		this.spawnY = this.posY;
		this.spawnZ = this.posZ;
		// find the sand close to the spawn point of the seal.
		boolean not_found = true;
		int ttt1 = 0;
		int ttt2 = 0;		
		this.getNavigator().setCanSwim(true);
		this.getNavigator().setAvoidSun(false);
		this.getNavigator().setAvoidsWater(false);
		this.getNavigator().setSpeed(this.swimSpeed);
		System.out.println("Can navi: ground[" + this.onGround + "] || water[" + isInWater() + "]");
		for (int i_z = 0; i_z < 300 && not_found; ++i_z) {
			for (int i_y = 0; i_y < 300 && not_found; ++i_y) {
				for (int i_x = 0; i_x < 300 && not_found; ++i_x) {
					
					// try adding and subtracting the z,y,x vals
					for (int tmp_z : new int[]{i_z, -i_z}) {
						for (int tmp_y : new int[]{i_y, -i_y}) {
							for (int tmp_x : new int[]{i_x, -i_x}) {
								if (not_found && this.worldObj.getBlock((int)this.posX+tmp_x,(int)this.posY+tmp_y,(int)this.posZ+tmp_z) == Blocks.sand) {
									PathEntity possiblePath = this.getNavigator().getPathToXYZ((int)this.posX+tmp_x,(int)this.posY+tmp_y,(int)this.posZ+tmp_z);
									if (possiblePath != null) {
										if (!possiblePath.isFinished()) {
											this.nearByBeachX = (int)this.posX+tmp_x;
											this.nearByBeachY = (int)this.posY+tmp_y;
											this.nearByBeachZ = (int)this.posZ+tmp_z;
											not_found = false;
										} else {
											//System.out.println("found sand but you can't reach it: [2] path was at end");
											ttt2++;
										}
									} else {
										//System.out.println("found sand but you can't reach it: [1] path was null");
										ttt1++;
									}
								}
							}
						}
					}
					
				}
			}
		}
		
		System.out.println("found sand but you can't reach it: [1] path was null " + ttt1 + " times");
		System.out.println("found sand but you can't reach it: [2] path was null " + ttt2 + " times");
		
		if (not_found) {
			System.out.println("EntityElephantSeal: Couldn't find a beach");
			if (aiHeadToBeach != null) {
				tasks.removeTask(aiHeadToBeach);
			}
		} else {
			System.out.println("EntityElephantSeal: Found a beach");
		}
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
       //aiWander = new EntityAIWander(this, 1.0D);
       //aiHeadToBeach = new EntityAIHeadToBeach(this);
       aiMySwim = new EntityAISwim(this);
      
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
       
       tasks.addTask(1, aiMySwim);
       // tasks.addTask(1, aiWander);
      // tasks.addTask(8, aiHeadToBeach);
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

	protected Item getDropItem() { // TODO remove?
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
		
	}

    protected void updateEntityActionState()
    {
        ++this.entityAge;
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
		return ((!this.isInWater()) && getBiome().equals(BiomeGenBase.beach)); // TODO fix so it check explicitly that you are on sand
	}
	
	public void setOnBeach()
	{
		lastTimeEnteredBeach = (new Date()).getTime();
	}

	/** Occasionally beach: "The northern elephant seals are nocturnal deep feeders famous for the long time intervals they remain under water." - http://en.wikipedia.org/wiki/Northern_elephant_seal
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
	
	// return the block under the entity
//	public Block findBlockUnder()
//	{
//	    int blockX = MathHelper.floor_double(this.posX);
//	    int blockY = MathHelper.floor_double(this.boundingBox.minY)-1;
//	    int blockZ = MathHelper.floor_double(this.posZ);
//	    return this.worldObj.getBlock(blockX, blockY, blockZ);
//	}

    /**
     * Checks if this entity is inside water (if inWater field is true as a result of handleWaterMovement() returning
     * true)
     */
    public boolean isInWater()
    {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
    }
	/*
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
	*/
	public BiomeGenBase getBiome () {
		return (this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ)));
	}
}