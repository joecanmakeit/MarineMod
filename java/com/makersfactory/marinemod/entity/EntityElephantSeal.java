package com.makersfactory.marinemod.entity;

import java.util.Date;
import java.util.Random;

import com.makersfactory.marinemod.ai.EntityAIHeadToBeach;
import com.makersfactory.marinemod.ai.EntityAISwim;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
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
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

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
	
	static int counttt = 0;	/// spawned count. used for debugging only
	
	public EntityElephantSeal(World p_i1695_1_)
	{
		super(p_i1695_1_);
		System.out.println("EntityElephantSeal: Ctor");
		//this.setSize(1.0F,1.0F);
		this.setSize(1.6F, 0.75F, 0.85F); // todo adjust these numbers by looking at F3 + B (shows bounding box) - Desmond can't do this easily :/
		//this.getBoundingBox().offset(p_72317_1_, p_72317_3_, p_72317_5_)
		this.scientificName = "Mirounga Angustrirostris";
		this.isImmuneToFire = true;
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
		boolean ss = this.onGround;
		//System.out.println("Can navi: ground[" + this.onGround + "] || water[" + isInWater() + "]");
		//boolean tmpwaterbool1=this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
		//boolean tmpwaterbool2=this.worldObj.getBlock((int)this.posX,(int)this.posY,(int)this.posZ) == Blocks.water;
		//boolean tmpwaterbool3=this.worldObj.getBlock((int)this.posX+1,(int)this.posY,(int)this.posZ) == Blocks.water;
		//boolean tmpwaterbool4=this.worldObj.getBlock((int)this.posX-1,(int)this.posY,(int)this.posZ) == Blocks.water;
		//System.out.println("water checks: " + tmpwaterbool1 +" " + tmpwaterbool2 + " " + tmpwaterbool3 + " " + tmpwaterbool4);
	}
	
	// TODO confirm this is correct: (entity is on sand and there is air directly above) or (entity is in air and their is sand directly below)?
	/** does not check if biome is beach... **/
	public boolean isPosTopBeachSand (int xCord, int yCord, int zCord) {
		return (this.worldObj.getBlock(xCord,yCord-1,zCord) == Blocks.sand &&
			   this.worldObj.getBlock(xCord,yCord,zCord) == Blocks.air);
	}
	
	/** returns null if no close by surface beach sand was found that is reachable from entities current position**/
	public int[] findSandNear(int byX, int byY, int byZ, int numXToCheck, int numYToCheck, int numZToCheck) {
		int[] coords = null;
		
		boolean not_found = true;
		/* for 300, 300by300 xz planes try to find a spawn point 
		 * (start with where you currently are) */
		for (int i_y = 0; i_y < numYToCheck && not_found; ++i_y) {
			for (int i_z = 0; i_z < numZToCheck && not_found; ++i_z) {
				for (int i_x = 0; i_x < numXToCheck && not_found; ++i_x) {
					
					// try adding and subtracting the z,y,x vals
					for (int tmp_y : new int[]{i_y, -i_y}) {
						for (int tmp_z : new int[]{i_z, -i_z}) {
							for (int tmp_x : new int[]{i_x, -i_x}) {
								// if a block is sand and above it is air then we can spawn there (on the air block)
								if (not_found && this.isPosTopBeachSand(byX+tmp_x,byY+tmp_y,byZ+tmp_z))
								{
									boolean prev_onGround = this.onGround;
									this.onGround = true;
									PathEntity possiblePath = this.getNavigator().getPathToXYZ(byX+tmp_x,byY+tmp_y+1,byZ+tmp_z);
									if (possiblePath != null) {
										if (!possiblePath.isFinished()) {
											coords = new int[3];
											coords[0] = byX+tmp_x;
											coords[1] = byY+tmp_y+1;
											coords[2] = byZ+tmp_z;
											not_found = false;
										}
									}
									this.onGround = prev_onGround;
								}
							}
						}
					}
					
				}
			}
		}
		
		return coords;
	}
	
	/** todo fix so the bbox is set around the entity **/
	protected void setSize(float new_x, float new_y, float new_z) {
        super.setSize(new_x, new_y);
        System.out.println(this.boundingBox.minX + " " + this.boundingBox.maxX + " | " + this.boundingBox.minY + " " + this.boundingBox.maxY + " | " + this.boundingBox.minZ + " " + this.boundingBox.maxZ);
        /*this.boundingBox.minX = this.boundingBox.minX - (double)new_x- (double)new_x;
        this.boundingBox.maxX = this.boundingBox.maxX - (double)new_x- (double)new_x;
        this.boundingBox.maxZ = this.boundingBox.minZ + (double)new_z;
        System.out.println(this.boundingBox.minX + " " + this.boundingBox.maxX + " | " + this.boundingBox.minY + " " + this.boundingBox.maxY + " | " + this.boundingBox.minZ + " " + this.boundingBox.maxZ);
        this.boundingBox.offset(-(double)new_x, 0, 0);
        System.out.println(this.boundingBox.minX + " " + this.boundingBox.maxX + " | " + this.boundingBox.minY + " " + this.boundingBox.maxY + " | " + this.boundingBox.minZ + " " + this.boundingBox.maxZ);
        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(this.boundingBox.minX - (double)new_x- (double)new_x, this.boundingBox.maxX - (double)new_x,
        		this.boundingBox.minY, this.boundingBox.maxY,
        		this.posZ - new_z, this.posZ + new_z);
        this.boundingBox.setBB(bb);
        System.out.println(this.boundingBox.minX + " " + this.boundingBox.maxX + " | " + this.boundingBox.minY + " " + this.boundingBox.maxY + " | " + this.boundingBox.minZ + " " + this.boundingBox.maxZ);*/
        //this.boundingBox.setBounds(this.boundingBox.minX - (double)new_x, this.boundingBox.minY, this.posZ - new_z, this.boundingBox.maxX - (double)new_x, this.boundingBox.maxY,  this.posZ + new_z);
       // this.boundingBox.setBounds(this.posX - 10, this.posY - 10, this.posZ - 10, this.posX + 10, this.posY + 10, this.posZ + 10);
        System.out.println(this.boundingBox.minX + " " + this.boundingBox.maxX + " | " + this.boundingBox.minY + " " + this.boundingBox.maxY + " | " + this.boundingBox.minZ + " " + this.boundingBox.maxZ);
        
        // TODO should I adjust "this.myEntitySize" ?
        // TODO should I create a length field?
	}

	/** for debugging. print "pre(floor(posX), floor(posY), floor(posZ))" to the screen **/
	public void printPos (String pre) {
		System.out.printf("%s(%d, %d, %d)%n",pre, (int)this.posX,(int)this.posY,(int)this.posZ);
	}
	
	protected void entityInit(){
		super.entityInit();
		//printPos("entityInit: ");
	}
	
	/** Change the entities path so that it changes its current target to be one block above where it currently is **/
	public void modifyPathForBeach(int up) {
		if (this.getNavigator().getPath() != null && !this.getNavigator().getPath().isFinished()) { // just to be safe
			int totalNumberOfPoints = this.getNavigator().getPath().getCurrentPathLength();
			int currentPoint = this.getNavigator().getPath().getCurrentPathIndex();
			int pointsLeft = totalNumberOfPoints - currentPoint;
					
			PathPoint[] newPathPoints = new PathPoint[pointsLeft + 1]; // + 1 because we are going to add a point above us
			newPathPoints[0] = new PathPoint((int) Math.floor(this.posX), (int) Math.floor(this.posY) + up, (int) Math.floor(this.posZ));
			for (int i=0; i < pointsLeft; ++i) {
				 PathPoint tmp_pp = this.getNavigator().getPath().getPathPointFromIndex(currentPoint + i);
				 newPathPoints[1+i] = new PathPoint(tmp_pp.xCoord, tmp_pp.yCoord, tmp_pp.zCoord);
			}
			this.getNavigator().clearPathEntity();
			this.getNavigator().setPath(new PathEntity(newPathPoints), this.swimSpeed);
		} else {
			System.err.println("elephant seal: modifyPathForBeach: should not get here: path is invalid"); // TODO remove debug
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
       
       //tasks.addTask(1, aiHeadToBeach);
       // tasks.addTask(1, aiWander);
       tasks.addTask(8, aiMySwim);
    }

    protected void clearAITasks()
    {
       tasks.taskEntries.clear();
       targetTasks.taskEntries.clear();
    }
	
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		//this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);  TODO
	}

	
	// protected String getLivingSound() { TODO
	// protected String getHurtSound() { TODO
	// protected String getDeathSound() { TODO
	// protected float getSoundVolume() { TODO

//	protected Item getDropItem() { // TODO remove?
//		return Item.getItemById(0);
//	}

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
		/*if (this.entityAge % 1000 == 0) {
			printPos("onLivingUpdate: ");
		}*/
	}

    protected void updateEntityActionState()
    {
        ++this.entityAge;
        this.despawnEntity();
    }

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity: must be in beach biome & super's method must be true. 
	 */
	public boolean getCanSpawnHere()
	{
		if (!super.getCanSpawnHere()) return false;
		if (!this.isInBeachBiome()) return false;
		return true;
	}
	
	/** @return true if found and set a nearby beach
	 ** @return false if failed to find and set a nearby beach 
	 */
	public boolean findAndSetNearbyBeach () {
		int[] tmp_coords = this.findSandNear((int)this.posX,(int)this.posY,(int)this.posZ, 20, 10, 20);
		
		if (tmp_coords == null) {
			System.out.println("EntityElephantSeal: Couldn't find a beach");
			if (aiHeadToBeach != null) {
				tasks.removeTask(aiHeadToBeach);
			}
			return false;
		} else {
			System.out.println("EntityElephantSeal: Found a beach (" + tmp_coords[0] + "," + tmp_coords[1] + "," + tmp_coords[2] + ")");
			this.nearByBeachX = tmp_coords[0];
			this.nearByBeachY = tmp_coords[1];
			this.nearByBeachZ = tmp_coords[2];
			return true;
		}
	}

	public boolean isInBeachBiome()
	{
		return (this.getBiome().equals(BiomeGenBase.beach));
	}
	
	/** true if on the sand and in a beach biome **/
	public boolean isOnBeach()
	{
		if (this.isInWater()) {
			return false; 
		}
		
		if (!this.isInBeachBiome()) {
			return false;
		}
		
		if (!this.isPosTopBeachSand((int)this.posX,(int)this.posY,(int)this.posZ)) {
			return false;
		}
		
		return true;
	}
/*	
	public void setOnBeach()
	{
		lastTimeEnteredBeach = (new Date()).getTime();
	}
*/
	/** Occasionally beach: "The northern elephant seals are nocturnal deep feeders famous for the long time intervals they remain under water." - http://en.wikipedia.org/wiki/Northern_elephant_seal
	 * 	  if it is currently not beached...
	 * 	    and it has been at least 6 minutes since the last time beaching: beach 10% likely
	 * 		and it has been at least 9 minutes since the last time beaching: beach 15% likely
	 *		and it has been at least 12 minutes since the last time beaching: beach 20% likely
	 * @return false if already on beach
	 * @return true if TODO
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
     * TODO figure out what the proper function is. Is the squid just hackin?
     */
    public boolean isInWater()
    {
    	return super.isInWater();
        //return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this); // how the squid entity does it	
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
	public BiomeGenBase getBiome() {
		return (this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ)));
	}
}