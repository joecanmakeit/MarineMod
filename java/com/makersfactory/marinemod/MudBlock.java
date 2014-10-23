package com.makersfactory.marinemod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class MudBlock extends Block {

	public MudBlock(Material p_i45394_1_) {
		super(p_i45394_1_);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockTextureName("myassets:textures/blocks/mudblock.png");
		this.setHardness(0.5F);
		this.setHarvestLevel("pickaxe", 2);
	}

}
