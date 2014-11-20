package com.makersfactory.marinemod;

import com.makersfactory.dimensions.TeleporterTest;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class ItemTeleporter extends Item {

	int dimension = 0;
	
	public ItemTeleporter(String name, int dimension) {
		this.dimension = dimension;
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setTextureName("myassets:"+name);
		this.setUnlocalizedName(name);
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (!par2World.isRemote) {
			EntityPlayerMP mplayer = (EntityPlayerMP)par3EntityPlayer;
			if (mplayer.dimension != this.dimension) {
				FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)par3EntityPlayer, this.dimension, new TeleporterTest(MinecraftServer.getServer().worldServerForDimension(this.dimension)));
			}
		}
		return par1ItemStack;
	}
}
