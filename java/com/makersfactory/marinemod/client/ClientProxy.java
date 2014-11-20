package com.makersfactory.marinemod.client;

import com.makersfactory.marinemod.CommonProxy;
import com.makersfactory.marinemod.MarineMod;
import com.makersfactory.marinemod.entity.EntityDolphin;
import com.makersfactory.marinemod.entity.EntityElephantSeal;
import com.makersfactory.marinemod.entity.EntityGull;
import com.makersfactory.marinemod.entity.EntityJellyfish;
import com.makersfactory.marinemod.entity.EntitySeagull;
import com.makersfactory.marinemod.entity.EntityStarfish;
import com.makersfactory.marinemod.entity.EntityWhale;
import com.makersfactory.marinemod.model.ModelDolphin;
import com.makersfactory.marinemod.model.ModelElephantSeal;
import com.makersfactory.marinemod.model.ModelGull;
import com.makersfactory.marinemod.model.ModelJellyfish;
import com.makersfactory.marinemod.model.ModelStarfish;
import com.makersfactory.marinemod.model.ModelWhale;
import com.makersfactory.marinemod.model.ModelSeagull;
import com.makersfactory.marinemod.renderer.RenderDolphin;
import com.makersfactory.marinemod.renderer.RenderElephantSeal;
import com.makersfactory.marinemod.renderer.RenderGull;
import com.makersfactory.marinemod.renderer.RenderJellyfish;
import com.makersfactory.marinemod.renderer.RenderStarfish;
import com.makersfactory.marinemod.renderer.RenderWhale;
import com.makersfactory.marinemod.renderer.RenderSeagull;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityJellyfish.class, new RenderJellyfish(new ModelJellyfish(1), new ModelJellyfish(0), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityGull.class, new RenderGull(new ModelGull(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityStarfish.class, new RenderStarfish(new ModelStarfish(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityWhale.class, new RenderWhale(new ModelWhale(), new ModelWhale(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySeagull.class, new RenderSeagull(new ModelSeagull(1), new ModelSeagull(0), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityElephantSeal.class, new RenderElephantSeal(new ModelElephantSeal(), new ModelElephantSeal(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDolphin.class, new RenderDolphin(new ModelDolphin(), new ModelDolphin(), 1.0F));
	}
}
