package com.makersfactory.dimensions;

import com.makersfactory.marinemod.MarineMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderTest extends WorldProvider {

	public double ticks = 0.0D;
	
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerTest(worldObj.getSeed(), terrainType);
		this.dimensionId = MarineMod.testDimensionId;
	}
	
	@Override
	public String getSaveFolder() {
		return "DIM-Test";
	}
	
	@Override
	public String getWelcomeMessage() {
		return "Entering the Test Dimension...";
	}
	
	@Override
	public String getDepartMessage() {
		return "Leaving the Test Dimension...";
	}
	
	@Override
	public boolean canRespawnHere() {
		return true;
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderTest(worldObj, worldObj.getSeed(), true);
	}
	
	@Override
	public String getDimensionName() {
		return "Test";
	}
	
	@Override
	public ChunkCoordinates getEntrancePortalLocation() {
		return null;
	}
	
	@Override
	public boolean canDoLightning(Chunk chunk) {
		return true;
	}
	
	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return true;
	}
	/*
	@Override
    @SideOnly(Side.CLIENT)
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks)
    {
		ticks++;
		double f = Math.sin(ticks/90.0D);
		return Vec3.createVectorHelper(0.0, 0.3+(f/3.0), 0.6-(f/3.0));
    }
    */

}
