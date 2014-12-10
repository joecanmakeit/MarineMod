package com.makersfactory.marinemod.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderShark extends RenderLiving {

	private static final ResourceLocation sharkTextures = new ResourceLocation("myassets:textures/entity/shark.png");
	
	public RenderShark(ModelBase p_i1262_1_, float p_i1262_2_) {
		super(p_i1262_1_, p_i1262_2_);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return sharkTextures;
	}

}
