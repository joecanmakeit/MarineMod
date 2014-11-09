package com.makersfactory.marinemod.model;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;



public class ModelWhale extends ModelBase
{
  //fields
    ModelRenderer head;
    ModelRenderer body1;
    ModelRenderer leftpectoralfin;
    ModelRenderer rightpectoralfin;
    ModelRenderer jaw;
    ModelRenderer pectoralfinr;
    ModelRenderer body3;
    ModelRenderer body4;
    ModelRenderer lefttail;
    ModelRenderer righttail;
  
  public ModelWhale()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      head = new ModelRenderer(this, 0, 0);
      head.addBox(-4F, -4F, -8F, 8, 6, 8);
      head.setRotationPoint(0F, 14F, -6F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      body1 = new ModelRenderer(this, 28, 8);
      body1.addBox(-5F, -10F, -7F, 10, 14, 8);
      body1.setRotationPoint(0F, 11F, 3F);
      body1.setTextureSize(64, 32);
      body1.mirror = true;
      setRotation(body1, 1.570796F, -0.0174533F, 0F);
      leftpectoralfin = new ModelRenderer(this, 28, 8);
      leftpectoralfin.addBox(0F, 2F, -1F, 8, 3, 1);
      leftpectoralfin.setRotationPoint(5F, 16F, -6F);
      leftpectoralfin.setTextureSize(64, 32);
      leftpectoralfin.mirror = true;
      setRotation(leftpectoralfin, 1.797689F, 0F, 0F);
      rightpectoralfin = new ModelRenderer(this, 0, 0);
      rightpectoralfin.addBox(2F, 21F, 0F, 10, 7, 7);
      rightpectoralfin.setRotationPoint(-7F, -11F, 7F);
      rightpectoralfin.setTextureSize(64, 32);
      rightpectoralfin.mirror = true;
      setRotation(rightpectoralfin, 0F, 0F, 0F);
      jaw = new ModelRenderer(this, 0, 0);
      jaw.addBox(0F, 0F, 0F, 8, 1, 8);
      jaw.setRotationPoint(-4F, 17F, -14F);
      jaw.setTextureSize(64, 32);
      jaw.mirror = true;
      setRotation(jaw, 0.1396263F, 0F, 0F);
      pectoralfinr = new ModelRenderer(this, 28, 8);
      pectoralfinr.addBox(0F, 0F, 0F, 8, 3, 1);
      pectoralfinr.setRotationPoint(-4F, 15F, -5F);
      pectoralfinr.setTextureSize(64, 32);
      pectoralfinr.mirror = true;
      setRotation(pectoralfinr, -1.797689F, 3.141593F, 0F);
      body3 = new ModelRenderer(this, 0, 0);
      body3.addBox(-5F, 10F, 14F, 10, 5, 5);
      body3.setRotationPoint(0F, 0F, 0F);
      body3.setTextureSize(64, 32);
      body3.mirror = true;
      setRotation(body3, 0F, 0F, 0F);
      body4 = new ModelRenderer(this, 0, 0);
      body4.addBox(0F, 0F, 0F, 10, 4, 5);
      body4.setRotationPoint(-5F, 10F, 19F);
      body4.setTextureSize(64, 32);
      body4.mirror = true;
      setRotation(body4, 0F, 0F, 0F);
      lefttail = new ModelRenderer(this, 0, 0);
      lefttail.addBox(0F, 0F, 0F, 8, 2, 3);
      lefttail.setRotationPoint(1F, 10F, 23F);
      lefttail.setTextureSize(64, 32);
      lefttail.mirror = true;
      setRotation(lefttail, 0.2359809F, 0F, 0F);
      righttail = new ModelRenderer(this, 0, 0);
      righttail.addBox(0F, 0F, 0F, 8, 2, 3);
      righttail.setRotationPoint(-1F, 12F, 23F);
      righttail.setTextureSize(64, 32);
      righttail.mirror = true;
      setRotation(righttail, 2.844164F, 3.141593F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    head.render(f5);
    body1.render(f5);
    leftpectoralfin.render(f5);
    rightpectoralfin.render(f5);
    jaw.render(f5);
    pectoralfinr.render(f5);
    body3.render(f5);
    body4.render(f5);
    lefttail.render(f5);
    righttail.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
