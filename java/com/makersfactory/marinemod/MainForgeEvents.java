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
