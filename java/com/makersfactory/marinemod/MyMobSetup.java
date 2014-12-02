package com.makersfactory.marinemod;

import com.makersfactory.marinemod.entity.EntityDolphin;
import com.makersfactory.marinemod.entity.EntityElephantSeal;
import com.makersfactory.marinemod.entity.EntityGull;
import com.makersfactory.marinemod.entity.EntityJellyfish;
import com.makersfactory.marinemod.entity.EntitySeagull;
import com.makersfactory.marinemod.entity.EntityShark;
import com.makersfactory.marinemod.entity.EntityFish;
import com.makersfactory.marinemod.entity.EntityStarfish;
import com.makersfactory.marinemod.entity.EntityWhale;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.registry.EntityRegistry;

public class MyMobSetup {
	
	public static void mainRegistry(MarineMod mod) {
		// Jellyfish Entity
		registerNewEntity(mod, EntityJellyfish.class, "Jellyfish", 0xE7ABFF, 0x7700A6);
		registerNewSpawnBiome(EntityStarfish.class, 6, 1, 5, EnumCreatureType.creature, BiomeGenBase.ocean);
		
		// Gull Entity
		registerNewEntity(mod, EntityGull.class, "Gull", 0xE7ABFF, 0x7700A6);
		
		// Starfish Entity
		registerNewEntity(mod, EntityStarfish.class, "Starfish", 0xE7ABFF, 0x7700A6);
		registerNewSpawnBiome(EntityStarfish.class, 6, 1, 5, EnumCreatureType.creature, BiomeGenBase.beach, BiomeGenBase.ocean);
		
		// Whale Entity
		registerNewEntity(mod, EntityWhale.class, "Whale", 0xE7ABFF, 0x7700A6);
		registerNewSpawnBiome(EntityStarfish.class, 6, 1, 5, EnumCreatureType.creature, BiomeGenBase.ocean);
		
		// Elephant Seal Entity
		registerNewEntity(mod, EntityElephantSeal.class, "ElephantSeal", 0x000000, 0xFFFFFF);
		registerNewSpawnBiome(EntityStarfish.class, 6, 1, 5, EnumCreatureType.creature, BiomeGenBase.beach);

		// Seagull Entity
		registerNewEntity(mod, EntitySeagull.class, "Seagull", 0xE7E6DD, 0xF4B400);
		registerNewSpawnBiome(EntityStarfish.class, 6, 1, 5, EnumCreatureType.creature, BiomeGenBase.beach);
		
		// Dolphin Entity
		registerNewEntity(mod, EntityDolphin.class, "Dolphin", 0xE7E6DD, 0xF4B400);
		registerNewSpawnBiome(EntityStarfish.class, 6, 1, 5, EnumCreatureType.creature, BiomeGenBase.ocean);
		
		// Shark Entity
		registerNewEntity(mod, EntityShark.class, "Shark", 0xE7E6DD, 0xF4B400);
		registerNewSpawnBiome(EntityShark.class, 6, 1, 5, EnumCreatureType.creature, BiomeGenBase.ocean);
	}
	
	// Register entity, create new egg for it.
	private static void registerNewEntity(MarineMod mod, Class entityClass, String entityName, int solidColor, int spotColor){
		int randomID = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, randomID);
		EntityRegistry.registerModEntity(entityClass, entityName, randomID, mod, 80, 1, true);
		EntityList.entityEggs.put(Integer.valueOf(randomID), new EntityList.EntityEggInfo(randomID, solidColor, spotColor));
	}
	
	// Register new spawn biome for a given entity
	private static void registerNewSpawnBiome(Class <? extends EntityLiving > entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeGenBase... biomes) {
		EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biomes);
	}
}
