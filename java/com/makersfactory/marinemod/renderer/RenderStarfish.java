package com.makersfactory.marinemod.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import com.makersfactory.marinemod.*;

public class RenderStarfish extends RenderLiving {
	
	private static final ResourceLocation starfishTextures = new ResourceLocation("myassets:textures/entity/Starfish.png");

	public RenderStarfish(ModelBase p_i1262_1_, float p_i1262_2_) {
		super(p_i1262_1_, p_i1262_2_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return starfishTextures;
	}


}
