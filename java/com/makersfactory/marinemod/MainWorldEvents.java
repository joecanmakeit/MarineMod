package com.makersfactory.marinemod;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

public class MainWorldEvents {

	int tickCount = 0;
	Random random = new Random();
	int range = 2;
	
	/*
	@SubscribeEvent
	public void onWorldTick(WorldTickEvent e) {
		if (!e.world.isRemote) {
			ArrayList<MyMobEntity> wraithpigs = new ArrayList<MyMobEntity>();
			for (Object o : e.world.loadedEntityList) {
				if (o instanceof MyMobEntity) wraithpigs.add((MyMobEntity)o);
			}
			if (wraithpigs.size() > 0) {
				int a = 0;
			}
			for (MyMobEntity wp : wraithpigs) {
				if (random.nextFloat() > 0.75F) {
			        int bX = (int) (Math.floor(wp.posX) + random.nextInt(range*2)-range);
			        int bY = (int) (Math.floor(wp.posY) + random.nextInt(range*2)-range);
			        int bZ = (int) (Math.floor(wp.posZ) + random.nextInt(range*2)-range);
			        if (	wp.worldObj.getBlock(bX, bY, bZ) != Blocks.air && 
			        		wp.worldObj.getBlock(bX, bY, bZ) != Blocks.fire && 
			        		wp.worldObj.getBlock(bX, bY+1, bZ) == Blocks.air) {
			        	wp.worldObj.setBlock(bX, bY+1, bZ, Blocks.fire, 0, 3);
			        }
				}
			}
		}
	}
	*/
	
}
