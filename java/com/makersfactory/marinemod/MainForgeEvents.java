package com.makersfactory.marinemod;

import java.util.ArrayList;

import com.makersfactory.marinemod.entity.EntityElephantSeal;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MainForgeEvents {

	@SubscribeEvent
	public void onPlayerClick(PlayerInteractEvent e) {
		
	}
	
//	/** Set the entity's position when it is spawned. Learned about from here...
//	 * http://www.minecraftforge.net/wiki/Event_Reference#LivingSpawnEvent
//	 * http://www.minecraftforge.net/forum/index.php?topic=20376.0
//	 * @author Desmond **/
//	@SubscribeEvent
//	public void onBirth(LivingSpawnEvent event) {
//		if (!event.entity.worldObj.isRemote) {
//			if (event.entity instanceof EntityElephantSeal) {
//				((EntityElephantSeal)event.entity).printPos("***********birth callback: ");
//			}
//		}
//	}
	
	/** They are about to spawn the entity. Check to see if the posistion is valid first..
	 * http://www.minecraftforge.net/wiki/Event_Reference#LivingSpawnEvent
	 * http://www.minecraftforge.net/forum/index.php?topic=20376.0
	 * @author Desmond **/
	@SubscribeEvent
	public void controlSpawn(EntityJoinWorldEvent event) {
		if (!event.entity.worldObj.isRemote) {
			if (event.entity instanceof EntityElephantSeal) {
				((EntityElephantSeal)event.entity).printPos("***********controlSpawn callback: ");
				if (! ((EntityElephantSeal)event.entity).getCanSpawnHere()) {
					System.out.println("Enetity Elephant Seal Spawn Regected: (" + ((EntityElephantSeal)event.entity).posX + "," + ((EntityElephantSeal)event.entity).posY + "," + ((EntityElephantSeal)event.entity).posZ + ")");
					event.setCanceled(true);
				}
			}
		}
	}
	
	
}
