package com.makersfactory.marinemod;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenIceSpike;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class MainGenerator implements IWorldGenerator {

	private void generateSurface(World world, Random random, int i, int j) {
		
	}

	private void generateNether(World world, Random random, int i, int j) {
		
	}
	
	private void generateEnd(World world, Random random, int i, int j) {
		
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId){
		case -1:
		    generateNether(world, random, chunkX * 16, chunkZ * 16);
		    break;
		case 0:
		    generateSurface(world, random, chunkX * 16, chunkZ * 16);
		    break;
		case 1:
		    generateEnd(world, random, chunkX * 16, chunkZ * 16);
		    break;
		}
	}

	private void generateVeinsNormal(World world, Random random, int i, int j, Block block, int size, int frequency, int minLevel, int maxLevel) {
		for(int k = 0; k < frequency; k++) {
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(maxLevel-minLevel)+minLevel;
			int firstBlockZCoord = j + random.nextInt(16);
			
			(new WorldGenMinable(block, size)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
	}
	
	private void generateVeinsNether(World world, Random random, int i, int j, Block block, int size, int frequency, int minLevel, int maxLevel) {
		for(int k = 0; k < frequency; k++) {
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(maxLevel-minLevel)+minLevel;
			int firstBlockZCoord = j + random.nextInt(16);
			
			(new WorldGenMinable(block, size, Blocks.netherrack)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
	}
	
	private void generateVeinsEnd(World world, Random random, int i, int j, Block block, int size, int frequency, int minLevel, int maxLevel) {
		for(int k = 0; k < frequency; k++) {
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(maxLevel-minLevel)+minLevel;
			int firstBlockZCoord = j + random.nextInt(16);
			
			(new WorldGenMinable(block, size, Blocks.end_stone)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
	}

}
