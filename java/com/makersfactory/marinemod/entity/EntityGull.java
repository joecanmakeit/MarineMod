package com.makersfactory.marinemod.entity;

import net.minecraft.entity.passive.EntityBat;
import net.minecraft.world.World;

public class EntityGull extends EntityBat {

	public EntityGull(World p_i1680_1_) {
		super(p_i1680_1_);
		// TODO Auto-generated constructor stub
	}
	
	protected void updateAITasks() {
		super.updateAITasks();
		this.setIsBatHanging(false);
	}

}
