package com.makersfactory.marinemod.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.makersfactory.marinemod.ai.EntityAIHeadToBeach;
import com.makersfactory.marinemod.ai.EntityAISwim;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
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
	private SealNavigator customSealNavigator;
	
	static int counttt = 0;	/// spawned count. used for debugging only
	
	protected class SealNavigator {
		private ArrayList<Vec3> path;
		private int curr_ptr; 
		private boolean path_is_set;
		private EntityElephantSeal entity;
		private double path_speed;
		private int total_ticks;
		private int last_coords_index;
		private int last_coords_hash;
		private Vec3 last_pos;
		private static final int STUCK_TICK_CHECK_INTERVAL = 20;
		
		public SealNavigator(EntityElephantSeal entityElephantSeal, double speed) {
			entity = entityElephantSeal;
			path = new ArrayList<Vec3>();
			path_is_set = false;
			curr_ptr = -1;
			path_speed = speed;
			last_pos = null;
		}
		
		/** start coords should be entities current position. It is not put into the path. The end coords are put into the path **/
		public void setPath(double start_x, double start_y, double start_z, double target_x, double target_y, double target_z) {
			Vec3 start_coords = Vec3.createVectorHelper(start_x, start_y, start_z);
			Vec3 end_coords = Vec3.createVectorHelper(target_x, target_y, target_z);
			
			this.setPath(start_coords, end_coords);
		}
		
		/** returns the sum of vectors v1 and v2. Does not modify v1 or v2 **/
		public Vec3 add(Vec3 v1, Vec3 v2) {
			return v1.addVector(v2.xCoord, v2.yCoord, v2.zCoord);
		}
		
		/** scale v by the scalar. returns a new Vec3 (doesn't modify v) **/
		public Vec3 scale(Vec3 v, double scalar) {
			return Vec3.createVectorHelper(v.xCoord * scalar, v.yCoord * scalar, v.zCoord * scalar);
		}
		
		/** start coords should be entities current position. It is not put into the path. The end coords are put into the path **/
		public void setPath(Vec3 start_coords, Vec3 end_coords) {
			System.out.println("in setPath");
			
			// reset the path
			this.path_is_set = false;
			this.path.clear();
			this.curr_ptr = -1;
			this.last_pos = null;
			
			// add in the path. try to set a point about every 1 block away but if the distance is far we will use 100 points maximum
			double distance = start_coords.distanceTo(end_coords);
			double step_size = (distance/100.0 > 2.0)? (distance/100.0) : 2.0; // if using 2.0 as step size would give less than 100 steps use 2.0 otherwise use whatever gives 100 steps
			Vec3 dir = start_coords.subtract(end_coords).normalize(); // Normalize(End - Start)
			Vec3 curr_coords = add(start_coords, scale(dir, step_size*1)); // curr_point = start_point + ( normalize(end_point-start_point) * (step_size * 1,2,3....))
			for (int scalar=1; curr_coords.distanceTo(end_coords) > step_size; ++scalar) {
				curr_coords = add(start_coords, scale(dir, step_size*scalar)); // curr_point = start_point + ( normalize(end_point-start_point) * (step_size * 1,2,3....))
				// offset the point randomly a little bit 
				curr_coords = curr_coords.addVector(Math.random()-0.5, Math.random()-0.5, Math.random()-0.5);
				// if block is free
				Block block_at_curr_point = entity.worldObj.getBlock((int)Math.round(curr_coords.xCoord), (int)Math.round(curr_coords.yCoord), (int)Math.round(curr_coords.zCoord));
				if (block_at_curr_point.equals(Blocks.air) || block_at_curr_point.equals(Blocks.water)) {
					System.out.println("Adding path point : " + block_at_curr_point + " at point [" + (int)Math.round(curr_coords.xCoord) + "  " + (int)Math.round(curr_coords.yCoord) + "  " + (int)Math.round(curr_coords.zCoord)); 
					path.add(curr_coords);
				} else {
					boolean found_clear_point = false;
					int bad_x = (int)Math.round(curr_coords.xCoord);
					int bad_y = (int)Math.round(curr_coords.yCoord);
					int bad_z = (int)Math.round(curr_coords.zCoord);
					
					// todo try searching from 0 out...
					for (int x = -4; x <= 4 && !found_clear_point; ++x) {
						for (int y = -4; y <= 4 && !found_clear_point; ++y) {
							for (int z = -4; z <= 4 && !found_clear_point; ++z) {
								Block tmp = entity.worldObj.getBlock(bad_x + x, bad_y + y, bad_z + z);
								if (tmp.equals(Blocks.air) || tmp.equals(Blocks.water)) {
									found_clear_point = true;
									System.out.println("Adding path point : " + block_at_curr_point + " at point [" + (double)(bad_x + x) + "  " + (double)(bad_y + y) + "  " + (double)(bad_z + z));
									path.add(Vec3.createVectorHelper((double)(bad_x + x), (double)(bad_y + y), (double)(bad_z + z)));
								}
							}
						}
					}
					
					if (!found_clear_point) {
						System.out.println("Can not reach path: " + block_at_curr_point + " at point [" + (int)Math.round(curr_coords.xCoord) + "  " + (int)Math.round(curr_coords.yCoord) + "  " + (int)Math.round(curr_coords.zCoord));
						path.add(curr_coords); // TODO remove and handle this appropriately (search backwards and try until stuck. then search with in a range between these two points.
					}
				}
			}
			
			// add in last point
			this.path.add(end_coords);
			Block block_at_curr_point = entity.worldObj.getBlock((int)Math.round(end_coords.xCoord), (int)Math.round(end_coords.yCoord), (int)Math.round(end_coords.zCoord));
			System.out.println("Adding path point : " + block_at_curr_point + " at point [" + end_coords);
			
			this.last_coords_index = -1; 
			this.curr_ptr = 0;
			this.path_is_set = true;
			
			System.out.println("out setPath");
		}
		
		public boolean pathIsSet() {
			return this.path_is_set;
		}
		
		public int getPathIndex() {
			if (pathIsSet()) return this.curr_ptr;
			return -1;
		}
		
		public int getPathLength() {
			if (pathIsSet()) return this.path.size();
			return 0;
		}
		
		
		public boolean moveEntity() {
			//System.out.println("In moveEntity");
			if (this.pathIsSet()) {
				int currIdx = this.getPathIndex();
				Vec3 currCoords = this.path.get(this.getPathIndex());
				if (last_coords_index != currIdx || last_coords_hash != currCoords.hashCode() || this.entity.getNavigator().noPath()) {
					last_coords_index = currIdx;
					last_coords_hash = currCoords.hashCode();
					
                    //this.entity.getMoveHelper().setMoveTo(currCoords.xCoord, currCoords.yCoord, currCoords.zCoord, this.path_speed);
					if (this.entity.getNavigator().tryMoveToXYZ(currCoords.xCoord, currCoords.yCoord, currCoords.zCoord, this.path_speed)) {
						//System.out.println("getting path succeeded");
					} else {
						System.out.println("getting path failed try to move to: " + currCoords);
					}
					//System.out.println("out moveEntity");
                    return true;
				}
            }
			//System.out.println("out moveEntity");
            return false;
		}
		
	    private void updatePathState() {
	    	//System.out.println("In update custom path state");
	    	if (this.pathIsSet()) {
		        Vec3 vec3 = this.entity.getPosition(1.0F);
		        int i = this.getPathIndex();
	
		        if (i == this.getPathLength()-1) {
		        	if (this.total_ticks % STUCK_TICK_CHECK_INTERVAL == 0 && last_pos != null && vec3.distanceTo(last_pos) < 0.1) {
		        		System.out.println("Entity: " + vec3);
		        		System.out.println("Last  : " + last_pos);
		        		System.out.println("Dest  : " + this.path.get(i));
		        		System.out.println("Ticks : " + total_ticks);
	        			System.out.println("Stuck but at the last point on the path: Finished Path");
	        			this.path_is_set = false;
						this.path.clear();
						this.curr_ptr = -1;
						this.last_pos = null;
	        		}
		        } else {
	        		double distance_from_entity_to_next_point = this.path.get(i).distanceTo(vec3);
	        		Vec3 nextNextPoint = this.path.get(i+1);
	        		//System.out.println("Checking If Path Pointer Should Update");
	        		
		        	if (distance_from_entity_to_next_point < 0.1 || ( (this.total_ticks % (STUCK_TICK_CHECK_INTERVAL*2) == 0) && (distance_from_entity_to_next_point < 0.4) && this.isWaterAndAir(this.path.get(i), this.path.get(i+1)))) {
		        		// TODO should say if very close to next point or (we haven't checked in a little while and if close to next point and there is a fairly clear path to the next next point). Note due to the way the navigator is written it won't ever get this close:/ I think it can only get to within the sqrt of the width of the entity but we check if stuck as another means of advancing in the path 
		        		this.curr_ptr++;
		        		System.out.println("Path Pointer Was Incrimented");
		        	} else if (this.total_ticks % STUCK_TICK_CHECK_INTERVAL == 0) {
		        		System.out.println("Entity: " + vec3);
		        		System.out.println("Last  : " + last_pos);
		        		System.out.println("Dest  : " + this.path.get(i));
		        		System.out.println("Dist  : " + distance_from_entity_to_next_point);
		        		System.out.println("Ticks : " + total_ticks);
		        		
		        		if (last_pos != null && vec3.distanceTo(last_pos) < 0.1) {
		        			System.out.println("Stuck so forcefully advancing path");
		        			this.curr_ptr++;
		        		}
		        	}
	        	}
		        last_pos = Vec3.createVectorHelper(vec3.xCoord, vec3.yCoord, vec3.zCoord);
	        	this.total_ticks++;
	        	//System.out.println("Done Checking If Path Pointer Should Update");
	    	}
	    }
	    
	    private boolean vec3_equals(Vec3 a, Vec3 b) {
	    	if (a == null || b == null) return false;
			return ( a.xCoord == b.xCoord && 
					 a.yCoord == b.yCoord &&
					 a.zCoord == b.zCoord);
		}

		private boolean isWaterAndAir(Vec3 start, Vec3 end) {
	    	System.out.println("in isWaterAndAir");
	    	
	    	Vec3 dir = start.subtract(end).normalize();
	    	Vec3 curr;
	    	int count = 0;
	    	final double dist = start.distanceTo(end);
	    	
	    	for (double scalar=0; scalar <= dist; scalar++) {
	    		curr = add(start, scale(dir, scalar));
	    		Block curr_block = entity.worldObj.getBlock((int)Math.ceil(curr.xCoord), (int)Math.ceil(curr.yCoord), (int)Math.ceil(curr.zCoord));
	    		if (curr_block != Blocks.water || curr_block != Blocks.air) {
	    			System.out.println("out isWaterAndAir");
	    			return false;
	    		}
	    		
	    		curr_block = entity.worldObj.getBlock((int)Math.floor(curr.xCoord), (int)Math.floor(curr.yCoord), (int)Math.floor(curr.zCoord));
	    		if (curr_block != Blocks.water || curr_block != Blocks.air) {
	    			System.out.println("out isWaterAndAir");
	    			return false;
	    		}
	    	}
	    	System.out.println("out isWaterAndAir");
	    	return true;
	    }
	}
	
	public boolean setPathToOcean() {
		Vec3 curr_pos = this.getPosition(1.0F);
		Vec3 out = Vec3.createVectorHelper(7.0, 0.0, 7.0);
		float angle_tic = (float)((2 * Math.PI) / 100.0);
		for (int angle = 1; angle < 100; ++angle) {
			out.rotateAroundY(angle_tic);
			Vec3 tmp = curr_pos.addVector(out.xCoord, out.yCoord, out.zCoord);
			if (this.worldObj.getBlock((int)tmp.xCoord, (int)tmp.yCoord, (int)tmp.zCoord) == Blocks.water) {
				this.customSealNavigator.setPath(curr_pos, tmp);
				return true;
			}
		}
		return false;
	}
	
	public SealNavigator getSealNavigator() {
		return this.customSealNavigator;
	}
	
	public EntityElephantSeal(World p_i1695_1_)
	{
		super(p_i1695_1_);
		System.out.println("EntityElephantSeal: Ctor");
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
		this.customSealNavigator = new SealNavigator(this, this.swimSpeed);
		// find the sand close to the spawn point of the seal.
		boolean not_found = true;
		int ttt1 = 0;
		int ttt2 = 0;		
		this.getNavigator().setCanSwim(true);
		this.getNavigator().setAvoidSun(false);
		this.getNavigator().setAvoidsWater(false);
		this.getNavigator().setBreakDoors(false);
		this.getNavigator().setEnterDoors(false);
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
	
	public void preSpawnCallbackFunction() {
		/* maybe dont use this (use the current thing or bring the current thing here - the sand thingy....
		 *  
		 * do a bit better hit box
		 * setPath to some water a little ways away
		 * 	see if it works
		 * 
		 */
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

        // Many failed attempts at manipulating the hit box (This is based on the F3 + B bounding box that is drawn...)
        
        System.out.println(this.boundingBox.minX + " " + this.boundingBox.maxX + " | " + this.boundingBox.minY + " " + this.boundingBox.maxY + " | " + this.boundingBox.minZ + " " + this.boundingBox.maxZ);
	}

	/** for debugging. print "pre(floor(posX), floor(posY), floor(posZ))" to the screen **/
	public void printPos (String pre) {
		System.out.printf("%s(%d, %d, %d)%n",pre, (int)this.posX,(int)this.posY,(int)this.posZ);
	}
	
	/* attempt to fix bounding box issue. It doesn't seem to matter. (This is based on the F3 + B bounding box that is drawn...)
	@Override
	public AxisAlignedBB getBoundingBox(){
		AxisAlignedBB tmp = this.boundingBox.copy();
		tmp.offset(1.0, 1.0, 4.0);
		return tmp;
	}*/
	
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
       //aiMySwim = new EntityAISwim(this);
      
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50.0D); // TODO adjust (lower?)
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
	public void onLivingUpdate() {
		super.onLivingUpdate();
        this.customSealNavigator.updatePathState();
	}

    protected void updateEntityActionState() {
        ++this.entityAge;
        this.despawnEntity();
    }
    
    public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
    {
    	if (this.customSealNavigator.pathIsSet()) {
    		this.customSealNavigator.moveEntity();
    		//super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
    	} else {
    		super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
    	}
    }

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity: must be in beach biome & super's method must be true. 
	 */
	public boolean getCanSpawnHere() {
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

	public BiomeGenBase getBiome() {
		return (this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ)));
	}
}