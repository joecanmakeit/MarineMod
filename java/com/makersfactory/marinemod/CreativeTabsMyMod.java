package com.makersfactory.marinemod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class CreativeTabsMyMod extends CreativeTabs {

	public CreativeTabsMyMod(String string) {
		super(string);
	}

	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(Blocks.flowing_water);
	}

}
