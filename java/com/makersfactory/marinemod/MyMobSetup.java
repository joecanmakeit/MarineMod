package com.makersfactory.marinemod;

import com.makersfactory.marinemod.entity.EntityGull;
import com.makersfactory.marinemod.entity.EntityJellyfish;
import com.makersfactory.marinemod.entity.EntityStarfish;

import net.minecraft.entity.EntityList;
import cpw.mods.fml.common.registry.EntityRegistry;

public class MyMobSetup {
	
	public static void mainRegistry(MarineMod mod) {
		registerJellyfish(mod);
		registerGull(mod);
		registerStarfish(mod);
	}
	
	public static void registerJellyfish(MarineMod mod) {
		createEntity(mod, EntityJellyfish.class, "Jellyfish", 0xE7ABFF, 0x7700A6);
	}
	
	public static void registerGull(MarineMod mod) {
		createEntity(mod, EntityGull.class, "Gull", 0xE7ABFF, 0x7700A6);
	}
	
	public static void registerStarfish(MarineMod mod) {
		createEntity(mod, EntityStarfish.class, "Starfish", 0xE7ABFF, 0x7700A6);
	}
	
	public static void createEntity(MarineMod mod, Class entityClass, String entityName, int solidColor, int spotColor) {
		int randomID = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, randomID);
		EntityRegistry.registerModEntity(entityClass, entityName, randomID, mod, 80, 1, true);
		createEgg(randomID, solidColor, spotColor);
	}

	private static void createEgg(int randomID, int solidColor, int spotColor) {
		EntityList.entityEggs.put(Integer.valueOf(randomID), new EntityList.EntityEggInfo(randomID, solidColor, spotColor));
	}
}
