package com.makersfactory.marinemod;

import java.util.ArrayList;

import com.makersfactory.dimensions.TeleporterTest;
import com.makersfactory.marinemod.entity.EntityElephantSeal;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MainForgeEvents {
	
	/** They are about to spawn the entity.
	 * Check to see if the position is valid first and use this as a place to do anything else you need to before entity is spawned...
	 * http://www.minecraftforge.net/wiki/Event_Reference#LivingSpawnEvent
	 * http://www.minecraftforge.net/forum/index.php?topic=20376.0
	 * @author Desmond **/
	@SubscribeEvent
	public void controlSpawn(EntityJoinWorldEvent event) {
		if (!event.entity.worldObj.isRemote) {
			if (event.entity instanceof EntityElephantSeal) {
				EntityElephantSeal tmp_entitiy = (EntityElephantSeal)event.entity;
				tmp_entitiy.printPos("***********controlSpawn callback: "); // TODO REMOVE DEBUG
				
				if (tmp_entitiy.getCanSpawnHere() ) { // check to see if location is good for spawning
					if (! tmp_entitiy.findAndSetNearbyBeach()) { // try to find a nearby spot in the sand
						System.out.println("Couldn't find nearby sand.");
						event.setCanceled(true); // cancel the spawn
					}
				} else {
					tmp_entitiy.printPos("Enetity Elephant Seal Spawn Regected: ");
					event.setCanceled(true); // cancel the spawn
				}				
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerClick(PlayerInteractEvent e) {
		if (!e.entityPlayer.worldObj.isRemote) {
			if (	e.action == e.action.RIGHT_CLICK_BLOCK && 
					e.entityPlayer.getCurrentEquippedItem() != null) {
				if (e.entityPlayer.getCurrentEquippedItem().getItem() == MarineMod.teleporterTest) {
				//	FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)e.entityPlayer, MarineMod.testDimensionId, new TeleporterTest(MinecraftServer.getServer().worldServerForDimension(MarineMod.testDimensionId)));
				}
				else if (e.entityPlayer.getCurrentEquippedItem().getItem() == MarineMod.teleporterHome) {
				//	FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)e.entityPlayer, 0, new TeleporterTest(MinecraftServer.getServer().worldServerForDimension(0)));
				}
			}
		}
	}	
	
}
