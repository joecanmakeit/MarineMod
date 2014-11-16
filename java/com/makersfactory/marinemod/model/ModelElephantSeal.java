package com.makersfactory.marinemod.model;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelElephantSeal extends ModelBase {
	ModelRenderer Body;
	ModelRenderer HigherBack;
	ModelRenderer LowerBack;
	ModelRenderer RightFlipper;
	ModelRenderer LeftFlipper;
	ModelRenderer BackNeck;
	ModelRenderer FrontNeck;
	ModelRenderer Head;
	ModelRenderer RightForwardFlipper;
	ModelRenderer LeftForwardFlipper;
	ModelRenderer Nose;

	public ModelElephantSeal() {
		textureWidth = 302;
		textureHeight = 186;

		Body = new ModelRenderer(this, 250, 40);
		Body.addBox(0F, 0F, 0F, 10, 5, 6);
		Body.setRotationPoint(-5F, 15F, 10F);
		Body.setTextureSize(302, 186);
		Body.mirror = true;
		setRotation(Body, 0F, 0F, 0F);
		HigherBack = new ModelRenderer(this, 140, 135);
		HigherBack.addBox(0F, 0F, 0F, 8, 5, 5);
		HigherBack.setRotationPoint(-4F, 15F, 16F);
		HigherBack.setTextureSize(302, 186);
		HigherBack.mirror = true;
		setRotation(HigherBack, 0F, 0F, 0F);
		LowerBack = new ModelRenderer(this, 262, 91);
		LowerBack.addBox(0F, 0F, 0F, 6, 4, 4);
		LowerBack.setRotationPoint(-3F, 16F, 21F);
		LowerBack.setTextureSize(302, 186);
		LowerBack.mirror = true;
		setRotation(LowerBack, 0F, 0F, 0F);
		RightFlipper = new ModelRenderer(this, 262, 140);
		RightFlipper.addBox(0F, 0F, 0F, 3, 2, 5);
		RightFlipper.setRotationPoint(-3F, 18F, 25F);
		RightFlipper.setTextureSize(302, 186);
		RightFlipper.mirror = true;
		setRotation(RightFlipper, 0F, 0F, 0F);
		LeftFlipper = new ModelRenderer(this, 100, 112);
		LeftFlipper.addBox(0F, 0F, 0F, 3, 2, 5);
		LeftFlipper.setRotationPoint(0F, 18F, 25F);
		LeftFlipper.setTextureSize(302, 186);
		LeftFlipper.mirror = true;
		setRotation(LeftFlipper, 0F, 0F, 0F);
		BackNeck = new ModelRenderer(this, 190, 40);
		BackNeck.addBox(0F, 0F, 0F, 8, 5, 2);
		BackNeck.setRotationPoint(-4F, 15F, 8F);
		BackNeck.setTextureSize(302, 186);
		BackNeck.mirror = true;
		setRotation(BackNeck, 0F, 0F, 0F);
		FrontNeck = new ModelRenderer(this, 101, 50);
		FrontNeck.addBox(0F, 0F, 0F, 6, 4, 2);
		FrontNeck.setRotationPoint(-3F, 16F, 6F);
		FrontNeck.setTextureSize(302, 186);
		FrontNeck.mirror = true;
		setRotation(FrontNeck, 0F, 0F, 0F);
		Head = new ModelRenderer(this, 32, 40);
		Head.addBox(0F, 0F, 0F, 4, 3, 3);
		Head.setRotationPoint(-2F, 17F, 3F);
		Head.setTextureSize(302, 186);
		Head.mirror = true;
		setRotation(Head, 0F, 0F, 0F);
		RightForwardFlipper = new ModelRenderer(this, 61, 126);
		RightForwardFlipper.addBox(-4F, 0F, 0F, 4, 1, 3); // move the rotation ball so it is against the body. 
		RightForwardFlipper.setRotationPoint(-5F, 19F, 11F);
		RightForwardFlipper.setTextureSize(302, 186);
		RightForwardFlipper.mirror = true;
		//setRotation(RightForwardFlipper, 0.7854F, -0.7854F, 0F);
		setRotation(RightForwardFlipper, 0F, 0F, 0F);
		LeftForwardFlipper = new ModelRenderer(this, 20, 150);
		LeftForwardFlipper.addBox(0F, 0F, 0F, 4, 1, 3);
		LeftForwardFlipper.setRotationPoint(5F, 19F, 11F);
		LeftForwardFlipper.setTextureSize(302, 186);
		LeftForwardFlipper.mirror = true;
		//setRotation(LeftForwardFlipper, 0.7854F, 0.7854F, 0F);
		setRotation(LeftForwardFlipper, 0F, 0F, 0F);
		Nose = new ModelRenderer(this, 32, 40);
		Nose.addBox(0F, 0F, 0F, 2, 3, 1);
		Nose.setRotationPoint(-1F, 18F, 2F);
		Nose.setTextureSize(302, 186);
		Nose.mirror = true;
		setRotation(Nose, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Body.render(f5);
		HigherBack.render(f5);
		LowerBack.render(f5);
		RightFlipper.render(f5);
		LeftFlipper.render(f5);
		BackNeck.render(f5);
		FrontNeck.render(f5);
		Head.render(f5);
		RightForwardFlipper.render(f5);
		LeftForwardFlipper.render(f5);
		Nose.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
