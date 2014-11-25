package com.makersfactory.marinemod.model;

import java.util.Random;

import com.makersfactory.marinemod.entity.EntitySeagull;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ModelSeagull extends ModelBase
{
  //fields
    ModelRenderer Body;
    ModelRenderer Head;
    ModelRenderer Beak;
    ModelRenderer LeftWing;
    ModelRenderer RightWing;
    ModelRenderer Tail;
    ModelRenderer LeftLeg;
    ModelRenderer LeftFoot1;
    ModelRenderer LeftFoot2;
    ModelRenderer RightLeg;
    ModelRenderer RightFoot1;
    ModelRenderer RightFoot2;
  
  public ModelSeagull(int i)
  {
	  	textureWidth = 64;
	    textureHeight = 32;
	    
	      Body = new ModelRenderer(this, 0, 1);
	      Body.addBox(-2F, 0F, 0F, 4, 4, 7);
	      Body.setRotationPoint(0F, 18F, 0F);
	      Body.setTextureSize(64, 32);
	      Body.mirror = true;
	      setRotation(Body, 0F, 0F, 0F);
	      Head = new ModelRenderer(this, 0, 0);
	      Head.addBox(-1.5F, -3F, -1F, 3, 3, 3);
	      Head.setRotationPoint(0F, 18F, 0F);
	      Head.setTextureSize(64, 32);
	      Head.mirror = true;
	      setRotation(Head, 0F, 0F, 0F);
	      Beak = new ModelRenderer(this, 16, 0);
	      Beak.addBox(-0.5F, -1F, -4F, 1, 1, 3);
	      Beak.setRotationPoint(0F, 18F, 0F);
	      Beak.setTextureSize(64, 32);
	      Beak.mirror = true;
	      setRotation(Beak, 0F, 0F, 0F);
	      LeftWing = new ModelRenderer(this, 0, 0);
	      LeftWing.addBox(0F, 0F, 0F, 1, 3, 7);
	      LeftWing.setRotationPoint(2F, 18F, 1F);
	      LeftWing.setTextureSize(64, 32);
	      LeftWing.mirror = true;
	      setRotation(LeftWing, 0F, 0F, 0F);
	      RightWing = new ModelRenderer(this, 0, 0);
	      RightWing.addBox(0F, 0F, 0F, 1, 3, 7);
	      RightWing.setRotationPoint(-3F, 18F, 1F);
	      RightWing.setTextureSize(64, 32);
	      RightWing.mirror = true;
	      setRotation(RightWing, 0F, 0F, 0F);
	      Tail = new ModelRenderer(this, 0, 0);
	      Tail.addBox(-1F, 0F, 0F, 2, 1, 3);
	      Tail.setRotationPoint(0F, 18F, 7F);
	      Tail.setTextureSize(64, 32);
	      Tail.mirror = true;
	      setRotation(Tail, 0.3490659F, 0F, 0F);
	      LeftLeg = new ModelRenderer(this, 24, 0);
	      LeftLeg.addBox(0F, 0F, 0F, 1, 2, 1);
	      LeftLeg.setRotationPoint(1F, 22F, 4F);
	      LeftLeg.setTextureSize(64, 32);
	      LeftLeg.mirror = true;
	      setRotation(LeftLeg, 0F, 0F, 0F);
	      LeftFoot1 = new ModelRenderer(this, 0, 12);
	      LeftFoot1.addBox(0F, 0F, 0F, 3, 1, 1);
	      LeftFoot1.setRotationPoint(0F, 23F, 3F);
	      LeftFoot1.setTextureSize(64, 32);
	      LeftFoot1.mirror = true;
	      setRotation(LeftFoot1, 0F, 0F, 0F);
	      LeftFoot2 = new ModelRenderer(this, 0, 14);
	      LeftFoot2.addBox(0F, 0F, 0F, 1, 1, 1);
	      LeftFoot2.setRotationPoint(1F, 23F, 2F);
	      LeftFoot2.setTextureSize(64, 32);
	      LeftFoot2.mirror = true;
	      setRotation(LeftFoot2, 0F, 0F, 0F);
	      RightLeg = new ModelRenderer(this, 24, 0);
	      RightLeg.addBox(0F, 0F, 0F, 1, 2, 1);
	      RightLeg.setRotationPoint(-2F, 22F, 4F);
	      RightLeg.setTextureSize(64, 32);
	      RightLeg.mirror = true;
	      setRotation(RightLeg, 0F, 0F, 0F);
	      RightFoot1 = new ModelRenderer(this, 0, 12);
	      RightFoot1.addBox(0F, 0F, 0F, 3, 1, 1);
	      RightFoot1.setRotationPoint(-3F, 23F, 3F);
	      RightFoot1.setTextureSize(64, 32);
	      RightFoot1.mirror = true;
	      setRotation(RightFoot1, 0F, 0F, 0F);
	      RightFoot2 = new ModelRenderer(this, 0, 14);
	      RightFoot2.addBox(0F, 0F, 0F, 1, 1, 1);
	      RightFoot2.setRotationPoint(-2F, 23F, 2F);
	      RightFoot2.setTextureSize(64, 32);
	      RightFoot2.mirror = true;
	      setRotation(RightFoot2, 0F, 0F, 0F);
}
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Body.render(f5);
    Head.render(f5);
    Beak.render(f5);
    LeftWing.render(f5);
    RightWing.render(f5);
    Tail.render(f5);
    LeftFoot1.render(f5);
    LeftFoot2.render(f5);
    RightLeg.render(f5);
    RightFoot1.render(f5);
    RightFoot2.render(f5);
  }
    
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  //Initializing an instance of seagull to check if the gull is on the ground
  EntitySeagull Gull = new EntitySeagull(null);
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    float f6 = (180F / (float)Math.PI);
    this.Head.rotateAngleX = f4 / (180F / (float)Math.PI);
    this.Head.rotateAngleY = f3 / (180F / (float)Math.PI);
    this.Beak.rotateAngleX = f4 / (180F / (float)Math.PI);
    this.Beak.rotateAngleY = f3 / (180F / (float)Math.PI);
    this.LeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F)* 1.4F * f1;
    this.LeftFoot1.rotateAngleX = MathHelper.cos(f * 0.6662F)* 1.4F * f1;
    this.LeftFoot2.rotateAngleX = MathHelper.cos(f * 0.6662F)* 1.4F * f1;
    this.RightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
    this.RightFoot1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
    this.RightFoot2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
    //Flapping wings
    if(!Gull.onGround){
    	this.LeftWing.rotateAngleY = MathHelper.cos(f * 0.6662F + (float)Math.PI) * -0.7F * f1;
    	this.LeftWing.rotateAngleZ = -45F;
    	this.RightWing.rotateAngleY = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 0.7F * f1;
    	this.RightWing.rotateAngleZ = 45F;
    }
  }

}
